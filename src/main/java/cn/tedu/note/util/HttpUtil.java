package cn.tedu.note.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/4/8 21:38
 */
public class HttpUtil {

    private static CloseableHttpClient client = HttpClients.createDefault();

    /**
     * Using http client to do get url request
     * @param urlstr
     * @return
     */
    public static String doGetRequest(String urlstr) throws URISyntaxException, MalformedURLException {
        URL url = new URL(urlstr); //避免有Java内部无法识别的字符,先将其转化为url,然后再请求.
        HttpGet get
                = new HttpGet(new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null));
        RequestConfig config = RequestConfig.custom() //4.5版本httpclient连接超时参数设置方式.
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).build();
        get.setConfig(config);
        HttpResponse response;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                String result = EntityUtils.toString(entity, "UTF-8");
                return result;
            } else {
                return "";
            }
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Using http client to do post url request
     * @param url
     * @return
     */
    public static String doPostRequest(String url){
        return "";
    }

    public static void closeClient(){
        try {
            if(client != null){
                client.close();
                System.out.println("HttpClient连接已关闭");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
