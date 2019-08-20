package cn.tedu.note.service.impl;

import cn.tedu.note.constant.AmapApiConstants;
import cn.tedu.note.constant.SystemConsts;
import cn.tedu.note.dao.SingleVehicleWorkDurationMapper;
import cn.tedu.note.entity.AmapApiGeocodeMultiEntity;
import cn.tedu.note.entity.OwnedCompanyGeocodingEntity;
import cn.tedu.note.service.IGeocodingService;
import cn.tedu.note.util.MapApiTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/8/20 15:26
 */
@Service
public class GeocodingServiceImpl implements IGeocodingService {

    @Resource
    private SingleVehicleWorkDurationMapper singleVehicleWorkDurationMapper;

    /**
     * 地理编码解析服务
     */
    private ExecutorService doGeocodingService = Executors.newFixedThreadPool(SystemConsts.CPU_CORES);

    private CompletionService doGeocodingCompleteService
            = new ExecutorCompletionService(doGeocodingService);

    /**
     * 计算运单地理编码的FutureTask
     */
    private class GeocodingTask implements Callable<OwnedCompanyGeocodingEntity> {

        private OwnedCompanyGeocodingEntity geocodingCable;

        public GeocodingTask(OwnedCompanyGeocodingEntity geocodingCable){
            this.geocodingCable = geocodingCable;
        }

        @Override
        public OwnedCompanyGeocodingEntity call() throws Exception {
            OwnedCompanyGeocodingEntity entity = captureGeocoding(geocodingCable);
            return entity;
        }
    }

    @Override
    public void getGeocoding() throws InterruptedException, ExecutionException {
        List<OwnedCompanyGeocodingEntity> list = singleVehicleWorkDurationMapper.getOwnedCompanyGeocodingList();

        for(OwnedCompanyGeocodingEntity geocodingCable:list){
            doGeocodingCompleteService.submit(new GeocodingTask((OwnedCompanyGeocodingEntity)geocodingCable));
        }

        List<OwnedCompanyGeocodingEntity> resultList = new ArrayList<>();
        for(int i=0; i < list.size(); i++){
            Future<OwnedCompanyGeocodingEntity> future = doGeocodingCompleteService.take();
            resultList.add(future.get());
        }

        singleVehicleWorkDurationMapper.updateOwnedCompanyGeocodingInfoBatch(resultList);
    }

    private String getGeocodingUrl(OwnedCompanyGeocodingEntity geocodingEntity) {
        String url = AmapApiConstants.GEOCODE_URL
                + geocodingEntity.getAddress() + "&city=" + geocodingEntity.getCity();
        return url;
    }

    private OwnedCompanyGeocodingEntity captureGeocoding(OwnedCompanyGeocodingEntity geocodingCable) throws ExecutionException, InterruptedException {
        AmapApiGeocodeMultiEntity geocodeMultiEntity = MapApiTool.getGeocode(getGeocodingUrl(geocodingCable));

        if(geocodeMultiEntity.getGeocode() != null && geocodeMultiEntity.getGeocode().contains(",")){
            System.out.println("直接解析的地理编码为: " + geocodeMultiEntity.getGeocode());
            String[] arr = geocodeMultiEntity.getGeocode().split(",");
            if(arr.length > 0){
                geocodingCable.setLongitudeA(Double.parseDouble(arr[0]));
                geocodingCable.setLatitudeA(Double.parseDouble(arr[1]));
            }
        } else {
            String resultGeocode = geocodeMultiEntity.getFutureGeocode().get();
            System.out.println("future解析的地理编码为: " + resultGeocode);
            if(resultGeocode != null && resultGeocode.contains(",")){
                String[] arr = resultGeocode.split(",");
                if(arr.length > 0){
                    geocodingCable.setLongitudeA(Double.parseDouble("".equals(arr[0].toString())?"0.00":arr[0].toString()));

                    geocodingCable.setLatitudeA(Double.parseDouble("".equals(arr[1].toString())?"0.00":arr[1].toString()));
                }
            }
        }

        return geocodingCable;
    }

}
