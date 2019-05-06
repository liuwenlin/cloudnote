package cn.tedu.note.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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

    private static CloseableHttpClient client;

    static {
        //配置Http连接池管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(150);

        //添加默认重试机制(默认3次).
        HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler();

        //4.3版本httpclient请求连接超时参数设置方式.
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).build();

        client = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(config)
                    .setRetryHandler(requestRetryHandler)
                    .build();
    }

    /**
     * Using http client to do get url request
     * @param urlstr
     * @return
     */
    public static String doGetRequest(String urlstr) throws URISyntaxException, MalformedURLException {
        URL url = new URL(urlstr); //避免有Java内部无法识别的字符,先将其转化为url,然后再请求.
        HttpGet get
                = new HttpGet(new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null));
        HttpResponse response;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                String result = EntityUtils.toString(entity, "UTF-8");
                return result;
            } else {
                get.abort(); //如果返回结果状态不成功,则直接丢弃本次get请求.
                if(response != null){
                    ((CloseableHttpResponse) response).close();//关闭相应输入流
                }
                return "";
            }
        } catch (ClientProtocolException e){
            get.abort();
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            get.abort();
            e.printStackTrace();
            return "";
        } finally {
            get.releaseConnection(); //关闭连接
        }
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
