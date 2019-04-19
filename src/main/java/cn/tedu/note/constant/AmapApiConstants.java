package cn.tedu.note.constant;

/**
 * 高德地图接口返回json中的参数名
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/11 16:13
 */
public class AmapApiConstants {

    public static final String GEOCODE_URL = "https://restapi.amap.com/v3/geocode/geo?key=2b1b8768a5d61cfa19b2454f847cbf1f&output=json&address=";

    /**
     * 路径规划基础参数url
     */
    public static final String ROUTE_PLANNING_URL = "https://restapi.amap.com/v3/direction/driving?key=2b1b8768a5d61cfa19b2454f847cbf1f&originid=&destinationid=&extensions=base&avoidpolygons=&avoidroad=&output=json&strategy=2";

    /**
     * 状态
     */
    public static final String STATUS = "status";

    /**
     * 地图api返回状态正确
     */
    public static final String STATUS_VAL = "1";

    /**
     * 规划路由
     */
    public static final String ROUTE = "route";

    /**
     * 可选路径
     */
    public static final String PATHS = "paths";

    /**
     * 规划距离
     */
    public static final String DISTANCE = "distance";

    /**
     * 地理编码
     */
    public static final String GEOCODES = "geocodes";

    /**
     * 经纬度
     */
    public static final String LOCATION = "location";

    /**
     * 路径规划可选途径地最大值
     */
    public static final int WAYPOINTS_MAX_NUM = 16;

}
