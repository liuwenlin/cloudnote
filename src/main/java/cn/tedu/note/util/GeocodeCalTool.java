package cn.tedu.note.util;

import cn.tedu.note.constant.AmapApiConstants;
import cn.tedu.note.entity.AmapApiGeocodeMultiEntity;
import cn.tedu.note.entity.AmapApiRoutePlanningMultiEntity;
import cn.tedu.note.entity.HoauScreenGeocodeEntity;
import cn.tedu.note.entity.StandardPlanDistanceEntity;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2020/3/19 15:48
 */
public class GeocodeCalTool {
    public static HoauScreenGeocodeEntity getHoauScreenGeocode(HoauScreenGeocodeEntity entity)
            throws ExecutionException, InterruptedException {
        if("".equals(entity.getLatitude())
                || "".equals(entity.getLongitude())
                || entity.getLatitude() == null
                || entity.getLongitude() == null){
            AmapApiGeocodeMultiEntity amapApiGeocodeMultiEntity
                    = MapApiTool.getGeocode(AmapApiConstants.GEOCODE_URL
                    + entity.getAddress() + "&city="
                    + entity.getCity());
            System.out.println("当前url为: " + AmapApiConstants.GEOCODE_URL
                    + entity.getAddress() + "&city="
                    + entity.getCity());
            String[] strArr = null;
            if(amapApiGeocodeMultiEntity.getGeocode() == null){
                String str = amapApiGeocodeMultiEntity.getFutureGeocode().get();
                if (str != null && !"".equals(str)){
                    strArr = str.split(",");
                }
            }else{
                String str = amapApiGeocodeMultiEntity.getGeocode();
                if (str != null && !"".equals(str)){
                    strArr = str.split(",");
                }
            }
            if(strArr != null) {
                entity.setLongitude(BigDecimal.valueOf(Double.parseDouble(strArr[0])));
                entity.setLatitude(BigDecimal.valueOf(Double.parseDouble(strArr[1])));
            }
        }

        return entity;
    }

    /**
     * 返回标准计算实体的地理编码
     * @param entity
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static StandardPlanDistanceEntity getGeocode(StandardPlanDistanceEntity entity) throws ExecutionException, InterruptedException {

        if(entity.getStartAddress() == null || "".equals(entity.getStartGeocode())){
            AmapApiGeocodeMultiEntity geocodeMultiEntity
                    = getGeocodeMultiEntity(entity.getStartCity(),entity.getStartCity());
            if(geocodeMultiEntity.getGeocode() != null){
                entity.setStartGeocode(geocodeMultiEntity.getGeocode());
            } else {
                entity.setStartGeocode(geocodeMultiEntity.getFutureGeocode().get());
            }
        }

        if(entity.getEndGeocode() == null || "".equals(entity.getEndGeocode())){
            AmapApiGeocodeMultiEntity geocodeMultiEntity
                    = getGeocodeMultiEntity(entity.getEndCity(),entity.getEndCity());
            if(geocodeMultiEntity.getGeocode() != null){
                entity.setEndGeocode(geocodeMultiEntity.getGeocode());
            } else {
                entity.setEndGeocode(geocodeMultiEntity.getFutureGeocode().get());
            }
        }

        return entity;
    }

    public static StandardPlanDistanceEntity getPlanDistance(StandardPlanDistanceEntity entity) throws ExecutionException, InterruptedException {

        AmapApiRoutePlanningMultiEntity routePlanningMultiEntity
                = MapApiTool.getDistance(getRoutePlanUrl(entity.getStartGeocode(),entity.getEndGeocode()));

        if(routePlanningMultiEntity.getAmapResultDistance() != null){
            entity.setDistance(routePlanningMultiEntity.getAmapResultDistance().doubleValue());
        } else {
            entity.setDistance(routePlanningMultiEntity.getFutureDistance().get().doubleValue());
        }

        return entity;
    }

    private static AmapApiGeocodeMultiEntity getGeocodeMultiEntity(String city, String address){

        return MapApiTool.getGeocode(AmapApiConstants.GEOCODE_URL
                + address + "&city="
                + city);

    }

    private static String getRoutePlanUrl(String start, String end){
        StringBuffer strBuff = new StringBuffer();
        strBuff.append(AmapApiConstants.ROUTE_PLANNING_URL);
        strBuff.append("&origin=").append(start);
        strBuff.append("&destination=").append(end);
        return strBuff.toString();
    }

    public static void main(String[] args) {
        System.out.println("3eer" + null);
    }
}
