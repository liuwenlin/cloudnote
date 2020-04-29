package cn.tedu.note.service.impl;

import cn.tedu.note.constant.SystemConsts;
import cn.tedu.note.entity.StandardPlanDistanceEntity;
import cn.tedu.note.service.IDrivingCarDistanceCalService;
import cn.tedu.note.util.GeocodeCalTool;
import cn.tedu.note.util.MapApiTool;
import cn.tedu.note.util.poiUtil.ExcelUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static cn.tedu.note.util.GeocodeCalTool.getPlanDistance;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/4/21 23:40
 */
@Service
@Scope("prototype")
public class DrivingCarDistanceCalService implements IDrivingCarDistanceCalService {


    /**
     * 地理编码解析服务
     */
    private ExecutorService doGeocodeService = Executors.newFixedThreadPool(SystemConsts.CPU_CORES);

    private CompletionService doGeocodeCompleteService
            = new ExecutorCompletionService(doGeocodeService);

    private ExecutorService doRoutingPlanService = Executors.newFixedThreadPool(SystemConsts.CPU_CORES);

    private CompletionService doRoutingPlanCompleteService
            = new ExecutorCompletionService(doRoutingPlanService);

    /**
     * 计算数据地理编码的FutureTask
     */
    private class GeocodingTask implements Callable<StandardPlanDistanceEntity>{

        private StandardPlanDistanceEntity entity;

        public GeocodingTask(StandardPlanDistanceEntity entity){
            this.entity = entity;
        }

        @Override
        public StandardPlanDistanceEntity call() throws Exception {
            return GeocodeCalTool.getGeocode(entity);
        }
    }

    private class PlanDistanceTask implements Callable<StandardPlanDistanceEntity>{

        private StandardPlanDistanceEntity entity;

        public PlanDistanceTask(StandardPlanDistanceEntity entity){
            this.entity = entity;
        }

        @Override
        public StandardPlanDistanceEntity call() throws Exception {
            return getPlanDistance(entity);
        }
    }

    @Override
    public void calDistance() throws InterruptedException, ExecutionException {

    }

    @Override
    public void calDistanceByExcel(String filePath) throws InterruptedException, ExecutionException {
        Map<String, StandardPlanDistanceEntity> excelMap = new HashMap<String, StandardPlanDistanceEntity>();
//        String filePath = "\\D:\\report.xls";
        try {
            ArrayList<ArrayList<String>> lists = ExcelUtil.excelReader(filePath);
            System.out.println("第一行:" + lists.get(1));
            for(int i = 0; i < lists.size(); i++){
                ArrayList<String> row = lists.get(i);

                StandardPlanDistanceEntity entity = new StandardPlanDistanceEntity();

                entity.setId(row.get(0));
                entity.setStartCity(row.get(1));
                entity.setEndCity(row.get(3));
                entity.setRemarkText(row.get(5));

                //获取表格第一行的标题
                if(i == 0){
                    entity.setStartGeocode(row.get(2));
                    entity.setEndGeocode(row.get(4));
                    entity.setDistance(Double.parseDouble(row.get(6)));
                }

                //先计算返回标准实体类的地理编码
                if(i > 0){
                    doGeocodeCompleteService.submit(new GeocodingTask(entity));
                }

                excelMap.put(row.get(0),entity);

            }

            int length = excelMap.size();

            for(int i = 0; i < length - 1; i ++){
                Future<StandardPlanDistanceEntity> future = doGeocodeCompleteService.take();
                StandardPlanDistanceEntity entity = future.get();
                excelMap.get(entity.getId()).setStartGeocode(entity.getStartGeocode());
                excelMap.get(entity.getId()).setEndGeocode(entity.getEndGeocode());
                doRoutingPlanCompleteService.submit(new PlanDistanceTask(entity));
            }

            for(int i = 0; i < length - 1; i ++){
                Future<StandardPlanDistanceEntity> future = doRoutingPlanCompleteService.take();
                StandardPlanDistanceEntity entity = future.get();
                excelMap.get(entity.getId()).setDistance(entity.getDistance());
            }

            System.out.println("first row: " + "\n" + excelMap.get("1"));

            int rows = lists.size();
            int columns = lists.get(0).size();

            Object[][] data = new Object[rows][columns];

            int sentinel = 0;

            //将map转换为数组
            for(Map.Entry<String,StandardPlanDistanceEntity> entry : excelMap.entrySet()){

                StandardPlanDistanceEntity distanceEntity = entry.getValue();

                data[sentinel][0] = distanceEntity.getId();
                data[sentinel][1] = distanceEntity.getStartCity();
                data[sentinel][2] = distanceEntity.getStartGeocode();
                data[sentinel][3] = distanceEntity.getEndCity();
                data[sentinel][4] = distanceEntity.getEndGeocode();
                data[sentinel][5] = distanceEntity.getRemarkText();
                data[sentinel][6] = distanceEntity.getDistance();
                sentinel++;
            }

            ExcelUtil.exportExcel("\\D:\\export.xls","data",data);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            MapApiTool.clearCache();
            MapApiTool.shutdown();
        }

    }
}
