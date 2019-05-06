package cn.tedu.note.util;

import cn.tedu.note.constant.AmapApiConstants;
import cn.tedu.note.constant.SystemConsts;
import cn.tedu.note.entity.AmapApiGeocodeMultiEntity;
import cn.tedu.note.entity.AmapApiRoutePlanningMultiEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/10 20:23
 */
public class MapApiTool {

    private static ExecutorService es = Executors.newFixedThreadPool(SystemConsts.CPU_CORES*2);

    /**
     * 路径规划请求任务并发容器
     */
    private static ConcurrentHashMap<String,Integer> routePlanningCache
                        = new ConcurrentHashMap<String, Integer>();

    private static ConcurrentHashMap<String,FutureTask<Integer>> processingRoutePlanningCache
                        = new ConcurrentHashMap<String, FutureTask<Integer>>();

    /**
     * 地理编码请求任务并发容器
     */
    private static ConcurrentHashMap<String,String> geocodeCache
            = new ConcurrentHashMap<String, String>();

    private static ConcurrentHashMap<String,FutureTask<String>> processingGeocodeCache
            = new ConcurrentHashMap<String, FutureTask<String>>();


    /**
     * 通过json解析,返回地图接口中的distance值
     * @param jsonStr
     * @return
     */
    private static Integer getRoutePlanningJsonResultToInteger(String jsonStr){
        JsonNode jsonNode;
        JsonNode objNode;
        try {
            jsonNode = new ObjectMapper().readTree(jsonStr);
            if(AmapApiConstants.STATUS_VAL.equals(jsonNode.findValue(AmapApiConstants.STATUS).textValue())&&jsonNode.findValue(AmapApiConstants.ROUTE).size()>0){
                System.out.println("找到规划路径!");
                objNode = jsonNode.findPath(AmapApiConstants.PATHS).findPath(AmapApiConstants.DISTANCE);
                if(objNode == null){
                    return 0;
                }
                int distance = Integer.parseInt(objNode.textValue());
                System.out.println("规划路径返回值: "+distance+ "米");
                return distance;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 用于并发执行调用路径规划API任务的Callable实现类
     */
    private static class MapApiRoutePlanningTask implements Callable<Integer> {

        private String url;

        MapApiRoutePlanningTask(String url){
            this.url = url;
        }

        @Override
        public Integer call() throws Exception {
            try{
                Integer distance = getRoutePlanningJsonResultToInteger(HttpUtil.doGetRequest(url));
                routePlanningCache.put(url,distance);
                return distance;
            } finally {
                // 无论正常或是异常都应将正在处理请求任务从并发容器中删除
                processingRoutePlanningCache.remove(url);
            }
        }
    }

    private static FutureTask<Integer> getDistanceFuture(String url){
        FutureTask<Integer> distanceFuture = processingRoutePlanningCache.get(url);
        if(distanceFuture==null){
            FutureTask<Integer> ft = new FutureTask<Integer>(new MapApiRoutePlanningTask(url));
            distanceFuture = processingRoutePlanningCache.putIfAbsent(url,ft);
            if(distanceFuture==null){ //表示当前没有正在执行的任务
                distanceFuture = ft;
                es.execute(ft);
                System.out.println("地图路径规划请求行车距离任务已启动,请等待完成>>>");
            } else {
                System.out.println("已有地图路径规划请求行车距离任务已启动,不必重新启动");
            }
        } else {
            System.out.println("当前已有地图路径规划请求行车距离任务已启动,不必重新启动");
        }
        return distanceFuture;
    }

    /**
     * 用于获取路径规划结果缓存对象
     * @param url
     * @return
     */
    public static AmapApiRoutePlanningMultiEntity getDistance(String url){
        Integer distance = routePlanningCache.get(url);
        if(distance == null){
            System.out.println("没有当前请求规划路径的缓存数据,请开始请求任务");
            return new AmapApiRoutePlanningMultiEntity(getDistanceFuture(url));
        } else {
            System.out.println("当前请求已有缓存数据,直接返回");
            return new AmapApiRoutePlanningMultiEntity(distance);
        }
    }

    /**
     * 通过json解析,返回地图接口中的distance值
     * @param jsonStr
     * @return
     */
    private static String getGeocodeJsonResultToInteger(String jsonStr){
        JsonNode jsonNode;
        JsonNode objNode;
        try {
            jsonNode = new ObjectMapper().readTree(jsonStr);
            if(AmapApiConstants.STATUS_VAL.equals(jsonNode.findValue(AmapApiConstants.STATUS).textValue())&&jsonNode.findValue(AmapApiConstants.GEOCODES).size()>0){
                System.out.println("找到地理编码!");
                objNode = jsonNode.findPath(AmapApiConstants.GEOCODES).findPath(AmapApiConstants.LOCATION);
                if(objNode == null){
                    return "";
                }
                String geocode = objNode.textValue();
                System.out.println("接口返回的地理编码为: "+geocode+ "");
                return geocode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用于并发执行调用地理编码API任务的Callable实现类
     */
    private static class MapApiGeocodeTask implements Callable<String> {

        private String url;

        MapApiGeocodeTask(String url){
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            try{
                String geocode = getGeocodeJsonResultToInteger(HttpUtil.doGetRequest(url));
                geocodeCache.put(url,geocode);
                return geocode;
            } finally {
                // 无论正常或是异常都应将正在处理请求任务从并发容器中删除
                processingGeocodeCache.remove(url);
            }
        }
    }

    /**
     * 用于获取地理编码结果缓存对象
     * @param url
     * @return
     */
    private static FutureTask<String> getGeocodeFuture(String url){
        FutureTask<String> geocodeFuture = processingGeocodeCache.get(url);
        if(geocodeFuture==null){
            FutureTask<String> ft = new FutureTask<String>(new MapApiGeocodeTask(url));
            geocodeFuture = processingGeocodeCache.putIfAbsent(url,ft);
            if(geocodeFuture==null){ //表示当前没有正在执行的任务
                geocodeFuture = ft;
                es.execute(ft);
                System.out.println("地图地理编码请求任务已启动,请等待完成>>>");
            } else {
                System.out.println("已有地图地理编码请求任务已启动,不必重新启动");
            }
        } else {
            System.out.println("当前已有地图地理编码请求任务已启动,不必重新启动");
        }
        return geocodeFuture;
    }

    public static AmapApiGeocodeMultiEntity getGeocode(String url){
        String geocode = geocodeCache.get(url);
        if(geocode == null){
            System.out.println("没有当前请求地理编码的缓存数据,请开始请求任务");
            return new AmapApiGeocodeMultiEntity(getGeocodeFuture(url));
        } else {
            System.out.println("当前地理编码请求已有缓存数据,直接返回");
            return new AmapApiGeocodeMultiEntity(geocode);
        }
    }

    /**
     * 每次使用静态并发缓存容器后,必须手动将其数据清空.
     */
    public static void clearCache(){
        geocodeCache.clear();
        routePlanningCache.clear();
    }

    /**
     * 在部署到正式库job任务中后,为防止jvm内存溢出,应显示的使用上文中的clearCache()方法清空缓存中的数据,
     * 由于数据需要每天在线程池中运行,因此此方法在正式库不能引用,仅用于单元测试关闭静态变量.
     */
    public static void shutdown(){
        if(es!=null){
            es.shutdown();
        }
        //关闭请求HttpClient客户端
        HttpUtil.closeClient();
    }

}
