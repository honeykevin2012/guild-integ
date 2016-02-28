package com.game.common.utility.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    private static CloseableHttpClient httpClient;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static RequestConfig requestConfig;
    private static int maxConnectionPerHost = 500;
    private static int maxTotalConnections = 500;
    private static int connectionTimeOut = 10 * 1000;
    private static int socketTimeOut = 10 * 1000;
    static {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(maxConnectionPerHost);
        connectionManager.setMaxTotal(maxTotalConnections);
        requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeOut).setSocketTimeout(socketTimeOut).build();
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        //httpClient = HttpClients.custom().setRetryHandler(new HttpRequestRetryHandler()).build();
    }

    public static String URLPost(String url, Map<String, String> params, String encode) {
    	CloseableHttpResponse response = null;
    	HttpPost httpPost = null;
    	HttpEntity entity = null;
    	try{
    		httpPost = new HttpPost(url);
    		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode);
	        String result = null;
	        Set<String> keySet = params.keySet();// 将表单的值放入postMethod中
	        List<NameValuePair> pairs = new ArrayList<NameValuePair>(keySet.size());
	        for (String key : keySet) {
	            String value = params.get(key);
	            pairs.add(new BasicNameValuePair(key, value));
	        }
	        httpPost.setEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
	        httpPost.setConfig(requestConfig);
	        response = httpClient.execute(httpPost);
	
	        int statusCode = response.getStatusLine().getStatusCode();// 执行postMethod
	        if (statusCode == HttpStatus.SC_OK) {
	            entity = response.getEntity();
	            if (entity != null) {
	                result = EntityUtils.toString(entity, "utf-8");
	            }
	            EntityUtils.consume(entity);
	            response.close();
	        }
	        return result;
    	}catch(Exception e){
    		e.printStackTrace();
    		return "Http post error!";
    	}finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
                if (response != null) {
                    response.close();
                }
                if (httpPost != null) {
                	httpPost.abort();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static String URLGet(String url, String enc) throws IOException {
        String result = null;
        HttpGet httpget = new HttpGet(url);

        httpget.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
        httpget.setConfig(requestConfig);
        CloseableHttpResponse response = httpClient.execute(httpget);
        int statusCode = response.getStatusLine().getStatusCode();// 执行getMethod
        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
        }

        return result;
    }

    public static String getLocationMethod(String reqUrl) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String location = null;
        int responseCode = 0;
        try {
            final HttpGet request = new HttpGet(reqUrl);
            RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
            // 默认不让重定向 ,这样就能拿到Location头了
            request.setConfig(requestConfig);
            HttpResponse response = httpclient.execute(request);
            responseCode = response.getStatusLine().getStatusCode();

            if (responseCode == 302) {
                Header locationHeader = response.getFirstHeader("Location");
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public static String post(String uri, Map<String, String> map) {
        try {
            return URLPost(uri, map, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String uri) throws IOException {
        return URLGet(uri, "UTF-8");
    }
    
    // begin 生活中心post请求第三方接口时调用的方法
    public static String zzfServiceURLPost(String url, String xmlInfo, String encode) {
    	try{
	        String result = null;
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode);
	        //String xmlInfo = getXmlInfo();
	        httpPost.setEntity(new StringEntity(xmlInfo,encode));
	        httpPost.setConfig(requestConfig);
	        CloseableHttpResponse response = httpClient.execute(httpPost);
	        int statusCode = response.getStatusLine().getStatusCode();// 执行postMethod
	        if (statusCode == HttpStatus.SC_OK) {
	            HttpEntity entity = response.getEntity();
	            if (entity != null) {
	                result = EntityUtils.toString(entity, "utf-8");
	            }
	            EntityUtils.consume(entity);
	            response.close();
	        }
	        return result;
    	}catch(Exception e){
    		e.printStackTrace();
    		return "Http post error!";
    	}
    }
    
    public static String testPost(String urlStr,String xmlInfo) {  
    	try {  
    		URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());  
            out.write(new String(xmlInfo.getBytes("UTF-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));  
            String line = "";  
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                System.out.println(line);  
            }  
            return line;
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
            return "Http post error!";
	    } catch (IOException e) {  
	        e.printStackTrace();  
	        return "Http post error!";
	    }  
    }   
    
    public static String zzfPost(String url, String xmlInfo) {
        try {
            return zzfServiceURLPost(url, xmlInfo, "UTF-8").replace("null", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //end  

    /**
     * 模拟FORM表单HTTP文件上传
     * @param uploadUrl
     * @param file
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String uploadFile(String uploadUrl, MultipartEntityBuilder builder) throws ClientProtocolException, IOException {
        String resp = null;
        HttpPost httppost = new HttpPost(uploadUrl);
        httppost.setEntity(builder.build());
        httppost.setConfig(requestConfig);
        CloseableHttpResponse  response = httpClient.execute(httppost);
        int resStatu = response.getStatusLine().getStatusCode();
        if (resStatu == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity(); // get result data
            resp = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            response.close();
          }
        return resp;
    }

    /*public static List<NameValuePair> getAllParas(Map<String, Object> params){
    	Set<String> keySet = params.keySet();
    	List<NameValuePair> pairs = new ArrayList<NameValuePair>(keySet.size());
        for (String key : keySet) {
            Object value = params.get(key);
            if(!(value instanceof Map)){
            	return pairs;
            } 
            getAllParas(value);
            pairs.add(new BasicNameValuePair(key, (String) value));
        }
    }*/
    	
    public static void main(String[] args) throws IOException {
    	//测试环境接口地址：
    
       /* File file = new File("d:\\1.png");
        String url = "http://upload.letvcdn.com:8000/single_upload_tool.php";


        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        FileBody fileBody = new FileBody(file);
        entity.addPart("single_upload_file", fileBody);
        entity.addTextBody("username","zhaolianjun");
        entity.addTextBody("md5str", "c4dc219649d456d42429c3ff2cb7cdb4");
        entity.addTextBody("watermark", "0");
        entity.addTextBody("compress", "85");
        entity.addTextBody("channel", "solu");
        entity.addTextBody("single_upload_submit","ok");
        String resp = uploadFile(url, entity);
        System.out.println(resp);
        JSONObject json = JSONObject.fromObject(resp);
        System.out.println(json.get("file"));*/
    }
    
}
