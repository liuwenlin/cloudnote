package cn.tedu.note.service.impl;

import cn.tedu.note.constant.SystemConsts;
import cn.tedu.note.dao.HoauScreenGeocodeDao;
import cn.tedu.note.entity.HoauScreenGeocodeEntity;
import cn.tedu.note.service.IHoauScreenGeocodeService;
import cn.tedu.note.util.GeocodeCalTool;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/19 15:29
 */
@Service
@Scope("prototype")
public class HoauScreenGeocodeService implements IHoauScreenGeocodeService {

    @Resource
    private HoauScreenGeocodeDao hoauScreenGeocodeDao;

    /**
     * 地理编码解析服务
     */
    private ExecutorService doGeocodingService = Executors.newFixedThreadPool(SystemConsts.CPU_CORES);

    private CompletionService doGeocodingCompleteService
            = new ExecutorCompletionService(doGeocodingService);

    /**
     * 计算数据地理编码的FutureTask
     */
    private class GeocodingTask implements Callable<HoauScreenGeocodeEntity>{

        private HoauScreenGeocodeEntity entity;

        public GeocodingTask(HoauScreenGeocodeEntity entity){
            this.entity = entity;
        }

        @Override
        public HoauScreenGeocodeEntity call() throws Exception {
            return GeocodeCalTool.getHoauScreenGeocode(entity);
        }
    }

    @Override
    public void computeHoauScreenGeocode() throws InterruptedException, ExecutionException {

        //1.获取基础数据信息
        Map<String,HoauScreenGeocodeEntity> geocodeMap = hoauScreenGeocodeDao.getHoauScreenGeocodeMapInfo();

        //2.放入计算服务中计算
        int count = geocodeMap.size();

        for(HoauScreenGeocodeEntity entity : geocodeMap.values()){
            doGeocodingCompleteService.submit(new GeocodingTask(entity));
        }

        for(int i = 0; i < count; i++){
            Future<HoauScreenGeocodeEntity> future= doGeocodingCompleteService.take();
            HoauScreenGeocodeEntity hoauScreenGeocodeEntity = future.get();

            String id = hoauScreenGeocodeEntity.getId();

            if(geocodeMap.get(id) != null){
                geocodeMap.get(id).setLongitude(hoauScreenGeocodeEntity.getLongitude());
                geocodeMap.get(id).setLatitude(hoauScreenGeocodeEntity.getLatitude());
            }
        }

        List<HoauScreenGeocodeEntity> list = new ArrayList<>(geocodeMap.values());

        //3.更新经纬度
        hoauScreenGeocodeDao.updateHoauScreenGeocode(list);

    }
}
