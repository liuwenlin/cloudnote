package cn.tedu.note.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/8 21:38
 */
public class HttpUtil {

    /**
     * do get url request
     * @param url
     */
    public static String doGetRequest(String url){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            System.out.println("Status code: "+response.getStatusLine().getStatusCode());
            String result = EntityUtils.toString(entity, "UTF-8");
            JsonNode jsonNode = new ObjectMapper().readTree(result);
            if("1".equals(jsonNode.findValue("status").textValue())&&jsonNode.findValue("route").size()>0){
                System.out.println("find route !");
                JsonNode objNode = jsonNode.findPath("paths").findPath("distance");
                System.out.println(Integer.parseInt(objNode.textValue()));
            }
            client.close();
            System.out.println(StringUtils.formatJson(result));
            return result;
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void doPostRequest(String url){

    }

}
