package cn.tedu.note.service.impl;

import cn.tedu.note.constant.AmapApiConstants;
import cn.tedu.note.constant.TransferType;
import cn.tedu.note.entity.*;
import cn.tedu.note.service.IAmapService;
import cn.tedu.note.util.MapApiTool;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/11 23:17
 */
@Service
public class AmapService implements IAmapService {

    private static Logger LOG = Logger.getLogger(AmapService.class);

    /**
     * 获取上下转移线路实体地理编码
     * @param transferPlanLineEntity
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public TransferPlanLineEntity captureTransferGeocoding(TransferPlanLineEntity transferPlanLineEntity)
            throws InterruptedException, ExecutionException {

//        if((!("".equals(transferPlanLineEntity.getEndGeoCode()))&&(transferPlanLineEntity.getEndGeoCode()!=null))
//                &&(!("".equals(transferPlanLineEntity.getStartGeoCode()))&&(transferPlanLineEntity.getStartGeoCode()!=null))){
//            System.out.println("当前转移线路的发车和到车公司地理编码都存在,不用启动地理编码计算任务");
//            return transferPlanLineEntity;
//        }

        if("".equals(transferPlanLineEntity.getStartGeoCode()) || transferPlanLineEntity.getStartGeoCode() == null){
            AmapApiGeocodeMultiEntity apme = MapApiTool.getGeocode(AmapApiConstants.GEOCODE_URL
                    + transferPlanLineEntity.getStartAddress() + "&city=" + transferPlanLineEntity.getStartCity());
            if(apme.getGeocode() == null){
                transferPlanLineEntity.setStartGeoCode(apme.getFutureGeocode().get());
            } else {
                transferPlanLineEntity.setStartGeoCode(apme.getGeocode());
            }
        }

        if("".equals(transferPlanLineEntity.getEndGeoCode()) || transferPlanLineEntity.getEndGeoCode() == null){
            AmapApiGeocodeMultiEntity apme = MapApiTool.getGeocode(AmapApiConstants.GEOCODE_URL
                    + transferPlanLineEntity.getEndAddress() + "&city=" + transferPlanLineEntity.getEndCity());
            if(apme.getGeocode() == null){
                transferPlanLineEntity.setEndGeoCode(apme.getFutureGeocode().get());
            } else {
                transferPlanLineEntity.setEndGeoCode(apme.getGeocode());
            }
        }
        LOG.info("当前转移线路发车编号: " + transferPlanLineEntity.getLine()
                + " 车牌号: " + transferPlanLineEntity.getCph()
                + " 起始地理编码为: " + transferPlanLineEntity.getStartGeoCode()
                + " 目的地理编码为: " + transferPlanLineEntity.getEndGeoCode());

        return transferPlanLineEntity;
    }

    /**
     * 获取上下转移线路实体规划路径距离
     * @param transferPlanLineEntity
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public TransferPlanLineEntity captureTransferRoutePlanning(TransferPlanLineEntity transferPlanLineEntity)
            throws InterruptedException, ExecutionException {
        AmapApiRoutePlanningMultiEntity apme = MapApiTool.getDistance(getRoutePlanUrl(transferPlanLineEntity));
        if(apme.getAmapResultDistance() == null){
            transferPlanLineEntity.setPlannedDistance(apme.getFutureDistance().get());
        } else {
            transferPlanLineEntity.setPlannedDistance(apme.getAmapResultDistance());
        }
        LOG.info("当前转移线路发车编号: " + transferPlanLineEntity.getLine()
                + " 转移类型: " + transferPlanLineEntity.getType()
                + " 车牌号: " + transferPlanLineEntity.getCph()
                + " 转移线路路径规划距离为: " + transferPlanLineEntity.getPlannedDistance());
        return transferPlanLineEntity;
    }

    /**
     * 上下转移发车路径规划解析url返回
     * @param transferPlanLineEntity
     * @return
     */
    private String getRoutePlanUrl(TransferPlanLineEntity transferPlanLineEntity){
        StringBuffer strBuff = new StringBuffer();
        if(transferPlanLineEntity.getType() == TransferType.UP_TRANFER.getTypeMsg()){
            strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL)
                    .append("&origin=").append(transferPlanLineEntity.getStartGeoCode())
                    .append("&destination=").append(transferPlanLineEntity.getEndGeoCode());
        } else {
            strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL)
                    .append("&origin=").append(transferPlanLineEntity.getEndGeoCode())
                    .append("&destination=").append(transferPlanLineEntity.getStartGeoCode());
        }
        String url = strBuff.toString();
        LOG.info("当前转移线路发车编号: " + transferPlanLineEntity.getLine()
                + " 转移类型: " + transferPlanLineEntity.getType()
                + " 车牌号: " + transferPlanLineEntity.getCph()
                + " 转移线路请求url为: " + url);
        return url;
    }
    /**
     * 获取体送货单中每个运单明细的地理编码
     * @param vehiclePlanLineEntity
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public VehiclePlanLineEntity captureGeocoding(VehiclePlanLineEntity vehiclePlanLineEntity) throws InterruptedException, ExecutionException {

        //检查提送货门店地理编码是否为空,为空则请求地理编码
        if("".equals(vehiclePlanLineEntity.getStoreGeoCode()) || (vehiclePlanLineEntity.getStoreGeoCode() == null)){
            AmapApiGeocodeMultiEntity apme = MapApiTool.getGeocode(AmapApiConstants.GEOCODE_URL
                    + vehiclePlanLineEntity.getStoreAddress()+ "&city=" + vehiclePlanLineEntity.getCity());
            if(apme.getGeocode() == null){
                vehiclePlanLineEntity.setStoreGeoCode(apme.getFutureGeocode().get());
            } else {
                vehiclePlanLineEntity.setStoreGeoCode(apme.getGeocode());
            }
        }

        Map<String,AmapApiGeocodeMultiEntity> multiEntityMap = new HashMap<String, AmapApiGeocodeMultiEntity>();

        for(VehicleOrderEntity order:vehiclePlanLineEntity.getOrderGeoCodeList()){
            multiEntityMap.put(order.getYdbh(),MapApiTool.getGeocode(getGeocodeUrl(order)));
            Thread.sleep(50);
        }

        for(VehicleOrderEntity order:vehiclePlanLineEntity.getOrderGeoCodeList()){
            AmapApiGeocodeMultiEntity geocodeMultiResult = multiEntityMap.get(order.getYdbh());
            if(geocodeMultiResult.getGeocode() == null){
                order.setGeocode(geocodeMultiResult.getFutureGeocode().get());
                LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                        + " 车牌号: " + vehiclePlanLineEntity.getCph()
                        + " 运单编号: " + order.getYdbh()
                        + " 地理编码: " + order.getGeocode());
            } else {
                order.setGeocode(geocodeMultiResult.getGeocode());
                LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                        + " 车牌号: " + vehiclePlanLineEntity.getCph()
                        + " 运单编号: " + order.getYdbh()
                        + " 地理编码: " + order.getGeocode());
            }
        }

        return vehiclePlanLineEntity;
    }

    /**
     * 运单地址解析url返回
     * @param order
     * @return
     */
    private String getGeocodeUrl(VehicleOrderEntity order) {
        return AmapApiConstants.GEOCODE_URL + order.getCity() + order.getAddress();
    }

    /**
     * 获取路径规划路径距离
     * @param vehiclePlanLineEntity
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public VehiclePlanLineEntity captureRoutePlanning(String startGeocode, VehiclePlanLineEntity vehiclePlanLineEntity)
            throws InterruptedException, ExecutionException {
        int deliverGoodsListSize = vehiclePlanLineEntity.getOrderGeoCodeList().size();
        if(deliverGoodsListSize <= AmapApiConstants.WAYPOINTS_MAX_NUM){
            AmapApiRoutePlanningMultiEntity apme = MapApiTool.getDistance(getRoutePlanningUrl(startGeocode,vehiclePlanLineEntity));
            if(apme.getAmapResultDistance() == null){
                vehiclePlanLineEntity.setGoodsDistance(apme.getFutureDistance().get());
            } else {
                vehiclePlanLineEntity.setGoodsDistance(apme.getAmapResultDistance());
            }
        } else {
            System.out.println("送货单列表大于16单的情况------");
            //将运单明细按照地图api路径规划途径地最大数量分成若干段
            int count;
            if(isZeroAfterRemaind(deliverGoodsListSize,AmapApiConstants.WAYPOINTS_MAX_NUM)){ //判断运单明细数量是否为途径地的整数倍
                count = deliverGoodsListSize / AmapApiConstants.WAYPOINTS_MAX_NUM;
            } else {
                count = deliverGoodsListSize / AmapApiConstants.WAYPOINTS_MAX_NUM + 1;
            }
            for(int i = 0; i < count; i++){
                if(i == 0){ //第一个分段
                    StringBuffer strBuff = new StringBuffer();
                    strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
                    strBuff.append("&origin=").append(startGeocode);
                    strBuff.append("&destination=").append(vehiclePlanLineEntity.getOrderGeoCodeList().get(AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&waypoints=");
                    for(int j = 0; j < AmapApiConstants.WAYPOINTS_MAX_NUM; j++){
                        strBuff.append(vehiclePlanLineEntity.getOrderGeoCodeList().get(j).getGeocode()).append(";");
                    }
                    String url = strBuff.substring(0,strBuff.length()-1);
                    AmapApiRoutePlanningMultiEntity arp = MapApiTool.getDistance(url);
                    LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                            + " 车牌号: " + vehiclePlanLineEntity.getCph()
                            + " 第一个分段请求url为: " + url);
                    if(arp.getAmapResultDistance() == null){
                        vehiclePlanLineEntity.setGoodsDistance(arp.getFutureDistance().get());

                    } else {
                        vehiclePlanLineEntity.setGoodsDistance(arp.getAmapResultDistance());
                    }
                } else if (i == count - 1){ //最后一个分段
                    StringBuffer strBuff = new StringBuffer();
                    strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
                    strBuff.append("&origin=").append(vehiclePlanLineEntity.getOrderGeoCodeList().get(i*AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&destination=").append(vehiclePlanLineEntity.getStoreGeoCode());
                    strBuff.append("&waypoints=");
                    for(int j = 0; j < (deliverGoodsListSize - i * AmapApiConstants.WAYPOINTS_MAX_NUM); j++){
                        strBuff.append(vehiclePlanLineEntity.getOrderGeoCodeList().get(i * AmapApiConstants.WAYPOINTS_MAX_NUM + j).getGeocode()).append(";");
                    }
                    String url = strBuff.substring(0,strBuff.length()-1);
                    AmapApiRoutePlanningMultiEntity arp = MapApiTool.getDistance(url);
                    LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                            + " 车牌号: " + vehiclePlanLineEntity.getCph()
                            + " 最后一个分段请求url为: " + url);
                    if(arp.getAmapResultDistance() == null){
                        vehiclePlanLineEntity.setGoodsDistance(arp.getFutureDistance().get() + vehiclePlanLineEntity.getGoodsDistance());
                    } else {
                        vehiclePlanLineEntity.setGoodsDistance(arp.getAmapResultDistance() + vehiclePlanLineEntity.getGoodsDistance());
                    }
                } else { //中间分段
                    StringBuffer strBuff = new StringBuffer();
                    strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
                    strBuff.append("&origin=").append(vehiclePlanLineEntity.getOrderGeoCodeList().get(i*AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&destination=").append(vehiclePlanLineEntity.getOrderGeoCodeList().get((i+1)*AmapApiConstants.WAYPOINTS_MAX_NUM - 1).getGeocode());
                    strBuff.append("&waypoints=");
                    for(int j = 1; j < AmapApiConstants.WAYPOINTS_MAX_NUM; j++){
                        strBuff.append(vehiclePlanLineEntity.getOrderGeoCodeList().get(i*AmapApiConstants.WAYPOINTS_MAX_NUM + j).getGeocode()).append(";");
                    }
                    String url = strBuff.substring(0,strBuff.length()-1);
                    AmapApiRoutePlanningMultiEntity arp = MapApiTool.getDistance(url);
                    LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                            + " 车牌号: " + vehiclePlanLineEntity.getCph()
                            + " 中间分段请求url为: " + url);
                    if(arp.getAmapResultDistance() == null){
                        vehiclePlanLineEntity.setGoodsDistance(arp.getFutureDistance().get() + vehiclePlanLineEntity.getGoodsDistance());
                    } else {
                        vehiclePlanLineEntity.setGoodsDistance(arp.getAmapResultDistance() + vehiclePlanLineEntity.getGoodsDistance());
                    }
                }
            }
        }
        LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                + " 车牌号: " + vehiclePlanLineEntity.getCph()
                + " 路径规划距离为: " + vehiclePlanLineEntity.getGoodsDistance());

        return vehiclePlanLineEntity;
    }

    /**
     * 根据提送货单明细实体生成路径规划请求url
     * @param vehiclePlanLineEntity
     * @return
     */
    private String getRoutePlanningUrl(String startGeocode, VehiclePlanLineEntity vehiclePlanLineEntity){
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
        strBuff.append("&origin=").append(startGeocode);
        strBuff.append("&destination=").append(vehiclePlanLineEntity.getStoreGeoCode());
        strBuff.append("&waypoints=");
        for(VehicleOrderEntity orders:vehiclePlanLineEntity.getOrderGeoCodeList()){
            strBuff.append(orders.getGeocode()).append(";");
        }
        String url = strBuff.substring(0,strBuff.length()-1);
        LOG.info("当前线路类型: " + vehiclePlanLineEntity.getBillType()
                + " 车牌号: " + vehiclePlanLineEntity.getCph()
                + " 请求url为: " + url);
        return url;
    }

    /**
     * 与运算取余数,参数b必须为2的幂次数
     * @param a
     * @param b
     * @return
     */
    private boolean isZeroAfterRemaind(int a, int b){
        int c = a&(b-1);
        if(c==0){
            return true;
        }
        return false;
    }
}
