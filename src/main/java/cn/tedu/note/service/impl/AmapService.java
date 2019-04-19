package cn.tedu.note.service.impl;

import cn.tedu.note.constant.AmapApiConstants;
import cn.tedu.note.constant.TransferType;
import cn.tedu.note.entity.*;
import cn.tedu.note.service.IAmapService;
import cn.tedu.note.util.MapApiTool;
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
        return strBuff.toString();
    }
    /**
     * 获取体送货单中每个运单明细的地理编码
     * @param goodsPlanLineEntity
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public GoodsPlanLineEntity captureGeocoding(GoodsPlanLineEntity goodsPlanLineEntity) throws InterruptedException, ExecutionException {

        Map<String,AmapApiGeocodeMultiEntity> multiEntityMap = new HashMap<String, AmapApiGeocodeMultiEntity>();

        for(GoodsOrdersEntity order:goodsPlanLineEntity.getOrderGeoCodeList()){
            multiEntityMap.put(order.getYdbh(),MapApiTool.getGeocode(getGeocodeUrl(order)));
            Thread.sleep(10);
        }

        for(GoodsOrdersEntity order:goodsPlanLineEntity.getOrderGeoCodeList()){
            AmapApiGeocodeMultiEntity geocodeMultiResult = multiEntityMap.get(order.getYdbh());
            if(geocodeMultiResult.getGeocode() == null){
                System.out.println("当前地理编码通过geocodeFuture返回");
                order.setGeocode(geocodeMultiResult.getFutureGeocode().get());
            } else {
                System.out.println("当前地理编码通过geocode返回");
                order.setGeocode(geocodeMultiResult.getGeocode());
            }
        }

        return goodsPlanLineEntity;
    }

    /**
     * 运单地址解析url返回
     * @param order
     * @return
     */
    private String getGeocodeUrl(GoodsOrdersEntity order) {
        return AmapApiConstants.GEOCODE_URL + order.getAddress();
    }

    /**
     * 获取路径规划路径距离
     * @param goodsPlanLineEntity
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public GoodsPlanLineEntity captureRoutePlanning(GoodsPlanLineEntity goodsPlanLineEntity)
            throws InterruptedException, ExecutionException {
        int deliverGoodsListSize = goodsPlanLineEntity.getOrderGeoCodeList().size();
        if(deliverGoodsListSize <= AmapApiConstants.WAYPOINTS_MAX_NUM){
            AmapApiRoutePlanningMultiEntity apme = MapApiTool.getDistance(getRoutePlanningUrl(goodsPlanLineEntity));
            if(apme.getAmapResultDistance() == null){
                goodsPlanLineEntity.setGoodsDistance(apme.getFutureDistance().get());
                System.out.println("当前请求路径规划接口通过future返回的距离为: " + apme.getFutureDistance().get());
            } else {
                goodsPlanLineEntity.setGoodsDistance(apme.getAmapResultDistance());
                System.out.println("当前请求路径规划接口通过缓存返回的距离为: " + apme.getAmapResultDistance());
            }
        } else {
            System.out.println("送货单列表大于16单的情况------");
            //将运单明细按照地图api路径规划途径地最大数量分成若干段
            int count = deliverGoodsListSize / AmapApiConstants.WAYPOINTS_MAX_NUM + 1;
            for(int i = 0; i < count; i++){ //第一个分段
                StringBuffer strBuff = new StringBuffer();
                if(i == 0){
                    strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
                    strBuff.append("&origin=").append(goodsPlanLineEntity.getStoreGeoCode());
                    strBuff.append("&destination=").append(goodsPlanLineEntity.getOrderGeoCodeList().get(AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&waypoints=");
                    for(int j = 0; j < AmapApiConstants.WAYPOINTS_MAX_NUM; j++){
                        strBuff.append(goodsPlanLineEntity.getOrderGeoCodeList().get(j).getGeocode()).append(";");
                    }
                    AmapApiRoutePlanningMultiEntity arp = MapApiTool.getDistance(strBuff.substring(0,strBuff.length()-1));
                    if(arp.getAmapResultDistance() == null){
                        goodsPlanLineEntity.setGoodsDistance(arp.getFutureDistance().get());
                    } else {
                        goodsPlanLineEntity.setGoodsDistance(arp.getAmapResultDistance());
                    }
                } else if (i == count - 1){ //最后一个分段
                    strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
                    strBuff.append("&origin=").append(goodsPlanLineEntity.getOrderGeoCodeList().get(i*AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&destination=").append(goodsPlanLineEntity.getStoreGeoCode());
                    strBuff.append("&waypoints=");
                    for(int j = 0; j < (deliverGoodsListSize - i * AmapApiConstants.WAYPOINTS_MAX_NUM); j++){
                        strBuff.append(goodsPlanLineEntity.getOrderGeoCodeList().get(i * AmapApiConstants.WAYPOINTS_MAX_NUM + j).getGeocode()).append(";");
                    }
                    AmapApiRoutePlanningMultiEntity arp = MapApiTool.getDistance(strBuff.substring(0,strBuff.length()-1));
                    if(arp.getAmapResultDistance() == null){
                        goodsPlanLineEntity.setGoodsDistance(arp.getFutureDistance().get() + goodsPlanLineEntity.getGoodsDistance());
                    } else {
                        goodsPlanLineEntity.setGoodsDistance(arp.getAmapResultDistance() + goodsPlanLineEntity.getGoodsDistance());
                    }
                } else { //中间分段
                    strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
                    strBuff.append("&origin=").append(goodsPlanLineEntity.getOrderGeoCodeList().get(i*AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&destination=").append(goodsPlanLineEntity.getOrderGeoCodeList().get((i+1)*AmapApiConstants.WAYPOINTS_MAX_NUM).getGeocode());
                    strBuff.append("&waypoints=");
                    for(int j = 1; j < AmapApiConstants.WAYPOINTS_MAX_NUM; j++){
                        strBuff.append(goodsPlanLineEntity.getOrderGeoCodeList().get(i*AmapApiConstants.WAYPOINTS_MAX_NUM + j).getGeocode()).append(";");
                    }
                    AmapApiRoutePlanningMultiEntity arp = MapApiTool.getDistance(strBuff.substring(0,strBuff.length()-1));
                    if(arp.getAmapResultDistance() == null){
                        goodsPlanLineEntity.setGoodsDistance(arp.getFutureDistance().get() + goodsPlanLineEntity.getGoodsDistance());
                    } else {
                        goodsPlanLineEntity.setGoodsDistance(arp.getAmapResultDistance() + goodsPlanLineEntity.getGoodsDistance());
                    }
                }
            }
        }

        return goodsPlanLineEntity;
    }

    /**
     * 根据提送货单明细实体生成路径规划请求url
     * @param goodsPlanLineEntity
     * @return
     */
    private String getRoutePlanningUrl(GoodsPlanLineEntity goodsPlanLineEntity){
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
        strBuff.append("&origin=").append(goodsPlanLineEntity.getStoreGeoCode());
        strBuff.append("&destination=").append(goodsPlanLineEntity.getStoreGeoCode());
        strBuff.append("&waypoints=");
        for(GoodsOrdersEntity orders:goodsPlanLineEntity.getOrderGeoCodeList()){
            strBuff.append(orders.getGeocode()).append(";");
        }
        strBuff.substring(0,strBuff.length()-1);
        System.out.println("路径规划请求url为:"+strBuff.toString());
        return strBuff.toString();
    }
}
