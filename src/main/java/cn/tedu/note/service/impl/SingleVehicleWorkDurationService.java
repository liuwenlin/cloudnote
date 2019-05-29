package cn.tedu.note.service.impl;

import cn.tedu.note.constant.BillType;
import cn.tedu.note.constant.SystemConsts;
import cn.tedu.note.constant.TransferType;
import cn.tedu.note.dao.SingleVehicleWorkDurationMapper;
import cn.tedu.note.entity.*;
import cn.tedu.note.service.IAmapService;
import cn.tedu.note.service.ISingleVehicleWorkDurationService;
import cn.tedu.note.util.MapApiTool;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 单车工作时长数据计算业务
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/12 13:28
 */
@Service
@Scope("prototype")
public class SingleVehicleWorkDurationService implements ISingleVehicleWorkDurationService {

    @Resource
    private IAmapService amapService;

    @Resource
    private SingleVehicleWorkDurationMapper singleVehicleWorkDurationMapper;

    /**
     * 地理编码解析服务
     */
    private ExecutorService doGeocodingService = Executors.newFixedThreadPool(SystemConsts.CPU_CORES);

    private  CompletionService doGeocodingCompleteService
            = new ExecutorCompletionService(doGeocodingService);

    private CompletionService doTransferGeocodingCompleteService
            = new ExecutorCompletionService(doGeocodingService);

    private CompletionService doPickupGeocodingCompleteService
            = new ExecutorCompletionService(doGeocodingService);

    /**
     * 路径规划计算服务
     */
    private ExecutorService doRoutePlanningService = Executors.newFixedThreadPool(SystemConsts.CPU_CORES);

    private CompletionService doRoutePlanningCompleteService
            = new ExecutorCompletionService(doRoutePlanningService);

    private CompletionService doTransferRoutePlanningCompleteService
            = new ExecutorCompletionService(doRoutePlanningService);

    /**
     * 计算上下转移线路地理编码的FutureTask
     */
    private class TransferPlanGeocodeTask implements Callable<TransferPlanLineEntity> {

        TransferPlanLineEntity transferPlanLineEntity;

        public TransferPlanGeocodeTask(TransferPlanLineEntity transferPlanLineEntity){
            this.transferPlanLineEntity = transferPlanLineEntity;
        }

        @Override
        public TransferPlanLineEntity call() throws Exception {
            return amapService.captureTransferGeocoding(transferPlanLineEntity);
        }
    }

    /**
     * 计算上下转移线路规划线路距离的FutureTask
     */
    private class TransferPlanRouteTask implements Callable<TransferPlanLineEntity> {

        TransferPlanLineEntity transferPlanLineEntity;

        public TransferPlanRouteTask(TransferPlanLineEntity transferPlanLineEntity){
            this.transferPlanLineEntity = transferPlanLineEntity;
        }

        @Override
        public TransferPlanLineEntity call() throws Exception {
            return amapService.captureTransferRoutePlanning(transferPlanLineEntity);
        }
    }

//    /**
//     * 计算运单地理编码的FutureTask
//     */
//    private class GeocodingTask implements Callable<GoodsPlanLineEntity> {
//
//        GoodsPlanLineEntity goodsPlanLineEntity;
//
//        public GeocodingTask(GoodsPlanLineEntity goodsPlanLineEntity){
//            this.goodsPlanLineEntity = goodsPlanLineEntity;
//        }
//
//        @Override
//        public GoodsPlanLineEntity call() throws Exception {
//            GoodsPlanLineEntity goodsBill = amapService.captureGeocoding(goodsPlanLineEntity);
//            return goodsBill;
//        }
//    }
//
//    /**
//     * 计算提送货路径规划的FutureTask
//     */
//    private class RoutePlanningTask implements Callable<GoodsPlanLineEntity> {
//
//        private GoodsPlanLineEntity goodsPlanLineEntity;
//
//        public RoutePlanningTask(GoodsPlanLineEntity goodsPlanLineEntity){
//            this.goodsPlanLineEntity = goodsPlanLineEntity;
//        }
//
//        @Override
//        public GoodsPlanLineEntity call() throws Exception {
//            GoodsPlanLineEntity deliverGoodsBill = amapService.captureRoutePlanning(goodsPlanLineEntity);
//            return deliverGoodsBill;
//        }
//    }

//    @Override
//    public void computeSingleVehicleWorkDuration() throws InterruptedException, ExecutionException {
//
//        //1.首先获取计算车辆的实体Map(预先加载车牌号的基础信息,待需完成字段计算完成后,然后持久化)
//        Map<String, SingleVehicleWorkDurationEntity> resultMap
//                = new HashMap<String, SingleVehicleWorkDurationEntity>();
//
//        List<SingleVehicleWorkDurationEntity> entityList
//                = singleVehicleWorkDurationMapper.getSingleVehicleWorkDurationList();
//        //将返回的list集合中的SingleVehicleWorkDurationEntity对象
//        for(SingleVehicleWorkDurationEntity entity : entityList){
//            resultMap.put(entity.getCph(),entity);
//        }
//
//        //2.再获取上下转移线路串线车辆信息
//        List<TransferPlanLineEntity> transferPlanLineList
//                = singleVehicleWorkDurationMapper.getTransferPlanLineList();
//
//        //3.最后获取取派件线路信息
//        List<GoodsPlanLineEntity> goodsBillsList
//                = singleVehicleWorkDurationMapper.getGoodsPlanLineList();
//
//        Map<String,List<GoodsPlanLineEntity>> deliverVehicleMap = new HashMap<String,List<GoodsPlanLineEntity>>();
//
//        Map<String,List<GoodsPlanLineEntity>> pickupVehicleMap = new HashMap<String,List<GoodsPlanLineEntity>>();
//
//        Long startTime = System.currentTimeMillis();
//
//        //4.提交数据计算任务
//        //4.1 上下转移线路发车到车公司地理编码检测
//        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
//            doTransferGeocodingCompleteService.submit(new TransferPlanGeocodeTask(transferPlan));
//            Thread.sleep(6);
//        }
//
//        //4.2 提送货运单地理编码计算
//        for(GoodsPlanLineEntity goodsBill:goodsBillsList){
//            doGeocodingCompleteService.submit(new GeocodingTask(goodsBill));
//            if(BillType.DELIVER_GOODS.getTypeMsg().equals(goodsBill.getBillType())){
//                setVehicleMap(deliverVehicleMap, goodsBill);
//            } else {
//                setVehicleMap(pickupVehicleMap, goodsBill);
//            }
//            Thread.sleep(6);
//        }
//
//        //4.3 上下转移线路发车到车公司路径规划计算
//        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
//            Future<TransferPlanLineEntity> future = doTransferGeocodingCompleteService.take();
//            doTransferRoutePlanningCompleteService.submit(new TransferPlanRouteTask(future.get()));
//            Thread.sleep(22);
//        }
//
//        //4.4 提送货线路路径规划距离计算
//        for(GoodsPlanLineEntity goodsBill:goodsBillsList){
//            Future<GoodsPlanLineEntity> future = doGeocodingCompleteService.take();
//            GoodsPlanLineEntity entity = future.get();
//            if(BillType.DELIVER_GOODS.getTypeMsg().equals(entity.getBillType())){
//                if(!pickupVehicleMap.containsKey(entity.getCph())){
//                    doRoutePlanningCompleteService.submit(new RoutePlanningTask(entity));
//                } else {
//
//                }
//            } else {
//                if(!deliverVehicleMap.containsKey(entity.getCph())){
//                    doRoutePlanningCompleteService.submit(new RoutePlanningTask(entity));
//                } else {
//
//                }
//            }
//
//            doRoutePlanningCompleteService.submit(new RoutePlanningTask(future.get()));
//            Thread.sleep(22);
//        }
//
//        //4.5 上下转移线路路径规划距离获取
//        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
//            Future<TransferPlanLineEntity> future = doTransferRoutePlanningCompleteService.take();
//            TransferPlanLineEntity tp = future.get();
////            System.out.println("当前转移线路: " + tp.getLine()
////                    + " 车牌号为: " + tp.getCph()
////                    + " 当前转移线路路径规划结果距离为: " + tp.getPlannedDistance());
//            if(TransferType.UP_TRANFER.getTypeMsg().equals(tp.getType())){
//                int lc = resultMap.get(tp.getCph()).getSzylc() == null?0:resultMap.get(tp.getCph()).getSzylc();
//                double dw = resultMap.get(tp.getCph()).getSzydw() == null?0:resultMap.get(tp.getCph()).getSzydw();
//                resultMap.get(tp.getCph()).setSzylc(lc + tp.getPlannedDistance());
//                resultMap.get(tp.getCph()).setSzydw(dw + tp.getZydw());
//            } else {
//                int lc = resultMap.get(tp.getCph()).getXzylc() == null?0:resultMap.get(tp.getCph()).getXzylc();
//                double dw = resultMap.get(tp.getCph()).getXzydw() == null?0:resultMap.get(tp.getCph()).getXzydw();
//                resultMap.get(tp.getCph()).setXzylc(lc + tp.getPlannedDistance());
//                resultMap.get(tp.getCph()).setXzydw(dw + tp.getZydw());
//            }
//        }
//
//        //4.6 提送货线路路径规划距离获取
//        for(GoodsPlanLineEntity goodsBill:goodsBillsList){
//            Future<GoodsPlanLineEntity> future = doRoutePlanningCompleteService.take();
//            GoodsPlanLineEntity gp = future.get();
////            System.out.println("当前送货单为: " + gp.getGoodsBill()
////                    + " 车牌号为: " + gp.getCph()
////                    + " 编号类型: " + gp.getBillType());
////            System.out.println("当前送货单路径规划结果距离为: " + gp);
//            if(BillType.DELIVER_GOODS.getTypeMsg().equals(gp.getBillType())){
//                int lc = resultMap.get(gp.getCph()).getPjghlc() == null?0:resultMap.get(gp.getCph()).getPjghlc();
//                resultMap.get(gp.getCph()).setPjghlc(lc + gp.getGoodsDistance());
//                int js = 0;
//                double dw = 0;
//                for(GoodsOrdersEntity order:gp.getOrderGeoCodeList()){
//                    js = js + order.getCountOfOrder();
//                    dw = dw + order.getTon();
//                }
//                resultMap.get(gp.getCph()).setPjjs(js);
//                resultMap.get(gp.getCph()).setPjdw(dw);
//            } else {
//                int lc = resultMap.get(gp.getCph()).getQjghlc() == null?0:resultMap.get(gp.getCph()).getQjghlc();
//                resultMap.get(gp.getCph()).setQjghlc(lc + gp.getGoodsDistance());
//                int js = 0;
//                double dw = 0;
//                for(GoodsOrdersEntity order:gp.getOrderGeoCodeList()){
//                    js = js + order.getCountOfOrder();
//                    dw = dw + order.getTon();
//                }
//                resultMap.get(gp.getCph()).setQjjs(js);
//                resultMap.get(gp.getCph()).setQjdw(dw);
//            }
//        }
//
////        for(SingleVehicleWorkDurationEntity entity : resultMap.values()){
////            if(entity.getSzydw() != null || entity.getXzydw() != null
////                    || entity.getPjdw() != null || entity.getQjdw() != null){
////                System.out.println("统计计算结果如下\n" + entity);
////            }
////        }
//
//        //5.将统计计算结果插入数据库中
//        singleVehicleWorkDurationMapper.batchInsertSingleVehicleWorkDurationInfo(
//                new LinkedList<SingleVehicleWorkDurationEntity>(resultMap.values()));
//
//        Long endTime = System.currentTimeMillis();
//
//        System.out.println("统计结果插入数据库完毕! 总耗时为: " + (endTime - startTime)/1000 +"秒");
//
//        //6.关闭请求任务线程池
//        MapApiTool.shutdown();
//
//        //7.关闭计算任务线程池
//        this.shutdown();
//
//    }

    /**
     * 计算运单地理编码的FutureTask
     */
    private class GeocodingTask implements Callable<VehiclePlanLineEntity> {

        private VehiclePlanLineEntity vehiclePlanLineEntity;

        public GeocodingTask(VehiclePlanLineEntity vehiclePlanLineEntity){
            this.vehiclePlanLineEntity = vehiclePlanLineEntity;
        }

        @Override
        public VehiclePlanLineEntity call() throws Exception {
            VehiclePlanLineEntity goodsBill = amapService.captureGeocoding(vehiclePlanLineEntity);
            return goodsBill;
        }
    }

    /**
     * 计算运单地理编码的FutureTask
     */
    private class PickupGeocodingTask implements Callable<VehiclePlanLineEntity> {

        private VehiclePlanLineEntity vehiclePlanLineEntity;

        public PickupGeocodingTask(VehiclePlanLineEntity vehiclePlanLineEntity){
            this.vehiclePlanLineEntity = vehiclePlanLineEntity;
        }

        @Override
        public VehiclePlanLineEntity call() throws Exception {
            VehiclePlanLineEntity goodsBill = amapService.captureGeocoding(vehiclePlanLineEntity);
            return goodsBill;
        }
    }

    /**
     * 计算提送货路径规划的FutureTask
     */
    private class RoutePlanningTask implements Callable<VehiclePlanLineEntity> {

        private VehiclePlanLineEntity vehiclePlanLineEntity;

        private String startGeocode;

        public RoutePlanningTask(String startGeocode,VehiclePlanLineEntity vehiclePlanLineEntity){
            this.vehiclePlanLineEntity = vehiclePlanLineEntity;
            this.startGeocode = startGeocode;
        }

        @Override
        public VehiclePlanLineEntity call() throws Exception {
            VehiclePlanLineEntity deliverGoodsBill = amapService.captureRoutePlanning(startGeocode,vehiclePlanLineEntity);
            return deliverGoodsBill;
        }
    }

//    @Override
//    public void computeSingleVehicleWorkDuration() throws InterruptedException, ExecutionException {
//
//        //1.首先获取计算车辆的实体Map(预先加载车牌号的基础信息,待需完成字段计算完成后,然后持久化)
//        Map<String, SingleVehicleWorkDurationEntity> resultMap
//                = singleVehicleWorkDurationMapper.getSingleVehicleWorkDurationMap();
//
////        List<SingleVehicleWorkDurationEntity> entityList
////                = singleVehicleWorkDurationMapper.getSingleVehicleWorkDurationList();
////
////        //将返回的list集合中的SingleVehicleWorkDurationEntity对象
////        for(SingleVehicleWorkDurationEntity entity : entityList){
////            resultMap.put(entity.getCph(),entity);
////        }
//
//        //2.再获取上下转移线路串线车辆信息
//        List<TransferPlanLineEntity> transferPlanLineList
//                = singleVehicleWorkDurationMapper.getTransferPlanLineList();
//
//        //3.最后获取取派件线路信息
//        Map<String,VehiclePlanLineEntity> deliverVehicleMap
//                = singleVehicleWorkDurationMapper.getDeliverGoodsPlanLineMap();
//
//        Map<String,VehiclePlanLineEntity> pickupVehicleMap
//                = singleVehicleWorkDurationMapper.getPickupGoodsPlanLineMap();
//
//        Long startTime = System.currentTimeMillis();
//
//        //4.提交数据计算任务
//        //4.1 上下转移线路发车到车公司地理编码检测
//        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
//            doTransferGeocodingCompleteService.submit(new TransferPlanGeocodeTask(transferPlan));
//            Thread.sleep(8);
//        }
//
//        //4.2 提送货运单地理编码计算
//        for(VehiclePlanLineEntity entity:deliverVehicleMap.values()){
//            doGeocodingCompleteService.submit(new GeocodingTask(entity));
//            Thread.sleep(8);
//        }
//
//        for(VehiclePlanLineEntity entity:pickupVehicleMap.values()){
//            doGeocodingCompleteService.submit(new GeocodingTask(entity));
//            Thread.sleep(8);
//        }
//
//        //4.3 上下转移线路发车到车公司路径规划计算
//        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
//            Future<TransferPlanLineEntity> future = doTransferGeocodingCompleteService.take();
//            doTransferRoutePlanningCompleteService.submit(new TransferPlanRouteTask(future.get()));
//            Thread.sleep(22);
//        }
//
//        //4.4.1 送货线路路径规划距离计算
//        for(VehiclePlanLineEntity vehiclePlan:deliverVehicleMap.values()){
//            Future<VehiclePlanLineEntity> future = doGeocodingCompleteService.take();
//            VehiclePlanLineEntity entity = future.get();
//
//            doRoutePlanningCompleteService.submit(new RoutePlanningTask(entity.getStoreGeoCode(),entity));
//            Thread.sleep(22);
//        }
//
//        //4.4.2提货线路路径规划距离计算
//        for(VehiclePlanLineEntity vehiclePlan:pickupVehicleMap.values()){
//            Future<VehiclePlanLineEntity> future = doGeocodingCompleteService.take();
//            VehiclePlanLineEntity entity = future.get();
//
//            /**
//             * 判断当前提货单的车牌号再送货单中是否有值:
//             * 1.如果有,则根据送货单的最后一票进行路径规划计算,
//             * 2.如果没有则直接根据当前提货单的门店编码进行计算.
//             */
//            if(deliverVehicleMap.get(entity.getCph()) == null){
//                doRoutePlanningCompleteService.submit(
//                        new RoutePlanningTask(entity.getStoreGeoCode(),entity));
//            } else {
//                int index = deliverVehicleMap.get(entity.getCph()).getOrderGeoCodeList().size() - 1;
//                doRoutePlanningCompleteService.submit(
//                        new RoutePlanningTask(
//                                deliverVehicleMap.get(entity.getCph()).getOrderGeoCodeList()
//                                        .get(index).getGeocode(),entity));
//            }
//
//            Thread.sleep(22);
//        }
//
//        //4.5 上下转移线路路径规划距离获取
//        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
//            Future<TransferPlanLineEntity> future = doTransferRoutePlanningCompleteService.take();
//            TransferPlanLineEntity tp = future.get();
//            /**
//             * 查看基础数据中是否有当前转移线路对应的车牌号:
//             * 1.如果有,继续处理;
//             * 2.如果没有,则忽略当前步骤
//             */
//            if(resultMap.get(tp.getCph()) != null){
//                if(TransferType.UP_TRANFER.getTypeMsg().equals(tp.getType())){
//                    int lc = resultMap.get(tp.getCph()).getSzylc() == null?0:resultMap.get(tp.getCph()).getSzylc();
//                    double dw = resultMap.get(tp.getCph()).getSzydw() == null?0:resultMap.get(tp.getCph()).getSzydw();
//                    double tj = resultMap.get(tp.getCph()).getSzytj() == null?0:resultMap.get(tp.getCph()).getSzytj();
//                    resultMap.get(tp.getCph()).setSzylc(lc + tp.getPlannedDistance());
//                    resultMap.get(tp.getCph()).setSzydw(dw + tp.getZydw());
//                    resultMap.get(tp.getCph()).setSzytj(tj + tp.getZytj());
//                } else {
//                    int lc = resultMap.get(tp.getCph()).getXzylc() == null?0:resultMap.get(tp.getCph()).getXzylc();
//                    double dw = resultMap.get(tp.getCph()).getXzydw() == null?0:resultMap.get(tp.getCph()).getXzydw();
//                    double tj = resultMap.get(tp.getCph()).getXzytj() == null?0:resultMap.get(tp.getCph()).getXzytj();
//                    resultMap.get(tp.getCph()).setXzylc(lc + tp.getPlannedDistance());
//                    resultMap.get(tp.getCph()).setXzydw(dw + tp.getZydw());
//                    resultMap.get(tp.getCph()).setXzytj(tj + tp.getZytj());
//                }
//            }
//        }
//
//        int length = deliverVehicleMap.size() + pickupVehicleMap.size();
//
//        //4.6 提送货线路路径规划距离获取
//        for(int i = 0; i < length; i++){
//            Future<VehiclePlanLineEntity> future = doRoutePlanningCompleteService.take();
//            VehiclePlanLineEntity gp = future.get();
//            /**
//             * 查看基础数据中是否有当前转移线路对应的车牌号:
//             * 1.如果有,继续处理;
//             * 2.如果没有,则忽略当前步骤
//             */
//            if(resultMap.get(gp.getCph()) != null){
//                if(BillType.DELIVER_GOODS.getTypeMsg().equals(gp.getBillType())){
//                    int lc = resultMap.get(gp.getCph()).getPjghlc() == null?0:resultMap.get(gp.getCph()).getPjghlc();
//                    resultMap.get(gp.getCph()).setPjghlc(lc + gp.getGoodsDistance());
//                } else {
//                    int lc = resultMap.get(gp.getCph()).getQjghlc() == null?0:resultMap.get(gp.getCph()).getQjghlc();
//                    resultMap.get(gp.getCph()).setQjghlc(lc + gp.getGoodsDistance());
//                }
//            }
//        }
//
//        //5.将统计计算结果插入数据库中
//        singleVehicleWorkDurationMapper.batchInsertSingleVehicleWorkDurationInfo(
//                new LinkedList<SingleVehicleWorkDurationEntity>(resultMap.values()));
//
//        Long endTime = System.currentTimeMillis();
//
//        System.out.println("统计结果插入数据库完毕! 总耗时为: " + (endTime - startTime)/1000 +"秒");
//
//        MapApiTool.clearCache();
//
//        //6.关闭请求任务线程池
//        MapApiTool.shutdown();
//
//        //7.关闭计算任务线程池
//        this.shutdown();
//
//    }

    @Override
    public void computeSingleVehicleWorkDuration() throws InterruptedException, ExecutionException {

        //1.首先获取计算车辆的实体Map(预先加载车牌号的基础信息,待需完成字段计算完成后,然后持久化)
        Map<String, SingleVehicleWorkDurationEntity> resultMap
                = singleVehicleWorkDurationMapper.getSingleVehicleWorkDurationMap();

        //2.再获取上下转移线路串线车辆信息
        List<TransferPlanLineEntity> transferPlanLineList
                = singleVehicleWorkDurationMapper.getTransferPlanLineList();

        //3.最后获取取派件线路信息
        Map<String,VehicleBillEntity> deliverVehicleMap
                = singleVehicleWorkDurationMapper.getDeliverVehicleGoodsPlanLineMap();

        Map<String,VehiclePlanLineEntity> pickupVehicleMap
                = singleVehicleWorkDurationMapper.getPickupGoodsPlanLineMap();

        Long startTime = System.currentTimeMillis();

        //4.提交数据计算任务
        int length = pickupVehicleMap.size();

        //4.1 上下转移线路发车到车公司地理编码检测
        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
            doTransferGeocodingCompleteService.submit(new TransferPlanGeocodeTask(transferPlan));
            Thread.sleep(25);
        }

        //4.2.1 送货运单地理编码计算(根据送货单计算)
        for(VehicleBillEntity vehicleBillEntity:deliverVehicleMap.values()){
            length += vehicleBillEntity.getVehiclePlanLineEntityList().size();
            for(VehiclePlanLineEntity entity:vehicleBillEntity.getVehiclePlanLineEntityList()){
                doGeocodingCompleteService.submit(new GeocodingTask(entity));
                Thread.sleep(25);
            }
        }

        //4.2.2 提货运单地理编码计算(根据车辆计算)
        for(VehiclePlanLineEntity entity:pickupVehicleMap.values()){
            doPickupGeocodingCompleteService.submit(new PickupGeocodingTask(entity));
            Thread.sleep(25);
        }

        //4.3 上下转移线路发车到车公司路径规划计算
        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
            Future<TransferPlanLineEntity> future = doTransferGeocodingCompleteService.take();
            doTransferRoutePlanningCompleteService.submit(new TransferPlanRouteTask(future.get()));
            Thread.sleep(50);
        }

        //4.4.1 送货线路路径规划距离计算
        for(VehicleBillEntity billEntity:deliverVehicleMap.values()){
            for(VehiclePlanLineEntity planLineEntity:billEntity.getVehiclePlanLineEntityList()){
                Future<VehiclePlanLineEntity> future = doGeocodingCompleteService.take();
                VehiclePlanLineEntity entity = future.get();

                doRoutePlanningCompleteService.submit(
                        new RoutePlanningTask(entity.getStoreGeoCode(),entity));
                Thread.sleep(50);
            }
        }

        //4.4.2提货线路路径规划距离计算
        for(VehiclePlanLineEntity planLineEntity:pickupVehicleMap.values()){
            Future<VehiclePlanLineEntity> future = doPickupGeocodingCompleteService.take();
            VehiclePlanLineEntity entity = future.get();

            /**
             * 判断当前提货单的车牌号再送货单中是否有值:
             * 1.如果有,则根据送货单的最后一票进行路径规划计算,
             * 2.如果没有则直接根据当前提货单的门店编码进行计算.
             */
            if(deliverVehicleMap.get(entity.getCph()) == null){
                doRoutePlanningCompleteService.submit(
                        new RoutePlanningTask(entity.getStoreGeoCode(),entity));
            } else {
                int index = deliverVehicleMap.get(entity.getCph()).getVehiclePlanLineEntityList().size() - 1;
                int orderIndex = deliverVehicleMap.get(entity.getCph()).getVehiclePlanLineEntityList()
                        .get(index).getOrderGeoCodeList().size() - 1;
                doRoutePlanningCompleteService.submit(
                        new RoutePlanningTask(
                                deliverVehicleMap.get(entity.getCph()).getVehiclePlanLineEntityList()
                                        .get(index).getOrderGeoCodeList().get(orderIndex).getGeocode(),entity));
            }
            Thread.sleep(50);
        }

        //4.5 上下转移线路路径规划距离获取
        for(TransferPlanLineEntity transferPlan:transferPlanLineList){
            Future<TransferPlanLineEntity> future = doTransferRoutePlanningCompleteService.take();
            TransferPlanLineEntity tp = future.get();
            /**
             * 查看基础数据中是否有当前转移线路对应的车牌号:
             * 1.如果有,继续处理;
             * 2.如果没有,则忽略当前步骤
             */
            if(resultMap.get(tp.getCph()) != null){
                if(TransferType.UP_TRANFER.getTypeMsg().equals(tp.getType())){
                    int lc = resultMap.get(tp.getCph()).getSzylc() == null?0:resultMap.get(tp.getCph()).getSzylc();
                    double dw = resultMap.get(tp.getCph()).getSzydw() == null?0:resultMap.get(tp.getCph()).getSzydw();
                    double tj = resultMap.get(tp.getCph()).getSzytj() == null?0:resultMap.get(tp.getCph()).getSzytj();
                    resultMap.get(tp.getCph()).setSzylc(lc + tp.getPlannedDistance());
                    resultMap.get(tp.getCph()).setSzydw(dw + tp.getZydw());
                    resultMap.get(tp.getCph()).setSzytj(tj + tp.getZytj());
                } else {
                    int lc = resultMap.get(tp.getCph()).getXzylc() == null?0:resultMap.get(tp.getCph()).getXzylc();
                    double dw = resultMap.get(tp.getCph()).getXzydw() == null?0:resultMap.get(tp.getCph()).getXzydw();
                    double tj = resultMap.get(tp.getCph()).getXzytj() == null?0:resultMap.get(tp.getCph()).getXzytj();
                    resultMap.get(tp.getCph()).setXzylc(lc + tp.getPlannedDistance());
                    resultMap.get(tp.getCph()).setXzydw(dw + tp.getZydw());
                    resultMap.get(tp.getCph()).setXzytj(tj + tp.getZytj());
                }
            }
        }

        //4.6 提送货线路路径规划距离获取
        for(int i = 0; i < length; i++){
            Future<VehiclePlanLineEntity> future = doRoutePlanningCompleteService.take();
            VehiclePlanLineEntity gp = future.get();
            /**
             * 查看基础数据中是否有当前转移线路对应的车牌号:
             * 1.如果有,继续处理;
             * 2.如果没有,则忽略当前步骤
             */
            if(resultMap.get(gp.getCph()) != null){
                if(BillType.DELIVER_GOODS.getTypeMsg().equals(gp.getBillType())){
                    int lc = resultMap.get(gp.getCph()).getPjghlc() == null?0:resultMap.get(gp.getCph()).getPjghlc();
                    resultMap.get(gp.getCph()).setPjghlc(lc + gp.getGoodsDistance());
                } else {
                    int lc = resultMap.get(gp.getCph()).getQjghlc() == null?0:resultMap.get(gp.getCph()).getQjghlc();
                    resultMap.get(gp.getCph()).setQjghlc(lc + gp.getGoodsDistance());
                }
            }
        }

        //5.将统计计算结果插入数据库中
        singleVehicleWorkDurationMapper.batchInsertSingleVehicleWorkDurationInfo(
                new LinkedList<SingleVehicleWorkDurationEntity>(resultMap.values()));

        Long endTime = System.currentTimeMillis();

        System.out.println("统计结果插入数据库完毕! 总耗时为: " + (endTime - startTime)/1000 +"秒");

        MapApiTool.clearCache();

        //6.关闭请求任务线程池
        MapApiTool.shutdown();

        //7.关闭计算任务线程池
        this.shutdown();

    }

//    private List<Future<GoodsPlanLineEntity>> computeOrderGeocode(List<GoodsPlanLineEntity> goodsBillsList)
// throws InterruptedException {
//
//        List<Future<GoodsPlanLineEntity>> goodsPlanLineList = new LinkedList<Future<GoodsPlanLineEntity>>();
//
//        for(GoodsPlanLineEntity goodsBill:goodsBillsList){
//            doRoutePlanningCompleteService.submit(new GeocodingTask(goodsBill));
//            Thread.sleep(25);
//        }
//
//        for(GoodsPlanLineEntity goodsBill:goodsBillsList){
//            Future<GoodsPlanLineEntity> future = doGeocodingCompleteService.take();
//            goodsPlanLineList.add(future);
//        }
//
//        return goodsPlanLineList;
//    }
//
//
//    private void computeRoutePlanning(List<Future<GoodsPlanLineEntity>> goodsBillsFutureList)
// throws InterruptedException, ExecutionException {
//        for(Future<GoodsPlanLineEntity> goodsBillFuture:goodsBillsFutureList){
//            doRoutePlanningCompleteService.submit(new RoutePlanningTask(goodsBillFuture.get()));
//            Thread.sleep(25);
//        }
//
//        for(Future<GoodsPlanLineEntity> goodsBillFuture:goodsBillsFutureList){
//            System.out.println("当前送货单为: " + goodsBillFuture.get().getGoodsBill()
//                             + " 车牌号为: " + goodsBillFuture.get().getCph()
//                             + " 编号类型: " + goodsBillFuture.get().getBillType());
//            Future<GoodsPlanLineEntity> future = doRoutePlanningCompleteService.take();
//            System.out.println(future);
//            GoodsPlanLineEntity goodsBillResult = future.get();
//            System.out.println("当前送货单路径规划结果距离为: " + goodsBillResult);
//        }
//    }

//    @Override
//    public void computeSingleVehicleWorkDuration() throws InterruptedException, ExecutionException {
//        List<DeliverGoodsPlanLineEntity> deliverGoodsBillsList = getDeliverGoodsPlanLineEntity();
//
//        for(DeliverGoodsPlanLineEntity deliverGoodsBill:deliverGoodsBillsList){
//            DeliverGoodsPlanLineEntity deliverGoodsBillResult = amapService.captureRoutePlanning(deliverGoodsBill);
//            System.out.println("路径规划接口返回距离为: " + deliverGoodsBillResult.getDeliverGoodsDistance());
//            Thread.sleep(25);
//        }
//
//        //关闭请求任务线程池
//        MapApiTool.shutdown();
//
////        this.shutdown();
//
//    }

    private void shutdown(){
        if(doRoutePlanningService != null){
            doRoutePlanningService.shutdown();
        }
        if(doGeocodingService != null){
            doGeocodingService.shutdown();
        }
    }

//    private List<TransferPlanLineEntity> getTransferPlanLineEntity(){
//        List<TransferPlanLineEntity> list = new LinkedList<TransferPlanLineEntity>();
//        TransferPlanLineEntity transferPlanLineEntity1 = new TransferPlanLineEntity();
//        transferPlanLineEntity1.setCph("沪A88888");
//        transferPlanLineEntity1.setLine("串N上海122N上海-20190415001");
//        transferPlanLineEntity1.setStartCity("上海");
//        transferPlanLineEntity1.setStartAddress("上海市宝山区杨行镇湄浦路2172号");
//        transferPlanLineEntity1.setStartGeoCode("121.43625,31.403609");
//        transferPlanLineEntity1.setEndCity("上海");
//        transferPlanLineEntity1.setEndAddress("闵行区华翔路2239号");
//        transferPlanLineEntity1.setEndGeoCode("");
//        transferPlanLineEntity1.setZydw(1.5);
//        transferPlanLineEntity1.setType(TransferType.UP_TRANFER.getTypeMsg());
//
//        TransferPlanLineEntity transferPlanLineEntity2 = new TransferPlanLineEntity();
//        transferPlanLineEntity2.setCph("沪A66666");
//        transferPlanLineEntity2.setLine("串N上海N上海2-20180323009");
//        transferPlanLineEntity2.setStartCity("上海");
//        transferPlanLineEntity2.setStartAddress("闵行区华翔路2239号");
//        transferPlanLineEntity2.setStartGeoCode("");
//        transferPlanLineEntity2.setEndCity("上海");
//        transferPlanLineEntity2.setEndAddress("上海市静安区彭浦镇平遥路95号");
//        transferPlanLineEntity2.setEndGeoCode("121.432113,31.29617");
//        transferPlanLineEntity2.setZydw(2.0);
//        transferPlanLineEntity2.setType(TransferType.DOWN_TRANSFER.getTypeMsg());
//
//        list.add(transferPlanLineEntity1);
//        list.add(transferPlanLineEntity2);
//        return list;
//    }
//
//    /**
//     * 测试数据
//     * 返回送货单列表
//     * @return
//     */
//    private List<GoodsPlanLineEntity> getGoodsPlanLineEntity(){
//        List<GoodsPlanLineEntity> list= new LinkedList<GoodsPlanLineEntity>();
//        GoodsPlanLineEntity goodsPlanLineEntity = new GoodsPlanLineEntity();
//        goodsPlanLineEntity.setStoreGeoCode("121.303183,31.20409");
//        goodsPlanLineEntity.setOrderGeoCodeList(getGoodsOrdersEntity());
//        goodsPlanLineEntity.setCph("沪A88888");
//        goodsPlanLineEntity.setGoodsBill("0666632432");
//        goodsPlanLineEntity.setBillType(BillType.DELIVER_GOODS.getTypeMsg());
//
//        list.add(goodsPlanLineEntity);
//
//        GoodsPlanLineEntity goodsPlanLineEntity2 = new GoodsPlanLineEntity();
//        goodsPlanLineEntity2.setStoreGeoCode("121.303183,31.20409");
//        goodsPlanLineEntity2.setOrderGeoCodeList(getGoodsOrdersEntity());
//        goodsPlanLineEntity2.setCph("沪A66666");
//        goodsPlanLineEntity2.setGoodsBill("0666632433");
//        goodsPlanLineEntity2.setBillType(BillType.DELIVER_GOODS.getTypeMsg());
//
//        list.add(goodsPlanLineEntity2);
//
//        System.out.println("送货单列表已生成,列表大小为: " + list.size());
//        return list;
//    }
//
//    /**
//     * 测试数据
//     * 返回送货单运单列表
//     * @return
//     */
//    private List<GoodsOrdersEntity> getGoodsOrdersEntity() {
//        List<GoodsOrdersEntity> geocodeList = new LinkedList<GoodsOrdersEntity>();
//        GoodsOrdersEntity goodsOrdersEntity1 = new GoodsOrdersEntity();
//        GoodsOrdersEntity goodsOrdersEntity2 = new GoodsOrdersEntity();
////        goodsOrdersEntity1.setGeocode("121.437694,31.195071");
//        goodsOrdersEntity1.setGoodsSequence(0);
//        goodsOrdersEntity1.setYdbh("F8888888");
//        goodsOrdersEntity1.setAddress("上海市长宁区古北路上海纺织西北1门");
////        goodsOrdersEntity2.setGeocode("121.435694,31.194071");
//        goodsOrdersEntity2.setGoodsSequence(1);
//        goodsOrdersEntity2.setYdbh("F6666666");
//        goodsOrdersEntity2.setAddress("上海市浦东区陆家嘴东方明珠塔");
//        geocodeList.add(goodsOrdersEntity1);
//        geocodeList.add(goodsOrdersEntity2);
//        return geocodeList;
//    }

//    public static void main(String[] args){
//        SingleVehicleWorkDurationService svwds = new SingleVehicleWorkDurationService(new AmapService());
//        try {
//            svwds.computeSingleVehicleWorkDuration();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
}
