package cn.tedu.note.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/8 22:25
 */
public class TestMapApi {

    public static void main(String[] args){
        Thread t = new MapApiTask("https://restapi.amap.com/v3/direction/driving?key=2b1b8768a5d61cfa19b2454f847cbf1f&origin=121.303183,31.20409&destination=121.303183,31.20409&originid=&destinationid=&extensions=base&waypoints=121.437694,31.195071&avoidpolygons=&avoidroad=&output=json&strategy=2");
        t.start();
    }

}

class MapApiTask extends Thread {

    private String url;

    MapApiTask(String url){
        this.url = url;
    }

    public void run(){
        try {
            HttpUtil.doGetRequest(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}