package com.chenyudaima.util;

import com.chenyudaima.config.HttpConfig;
import com.chenyudaima.model.HttpResult;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * http请求工具
 */
public class HttpUtil {

    /**
     * 通过连接池获取HttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(HttpConfig.HTTP_CLIENT_CONNECTION_MANAGER).build();
    }


    /**
     * 路径字符串转URL字符串
     */
    private static String encode(String url) throws UnsupportedEncodingException {
        String[] split = url.split("/");

        for (int i = 0; i < split.length; i++) {
            split[i] = URLEncoder.encode(split[i], HttpConfig.ENCODING).replaceAll("\\+", "%20");
        }
        StringBuilder stringBuilder = new StringBuilder();

        String host = url.substring(url.toLowerCase().indexOf("http://") + 7);

        stringBuilder.append("http://" + host.substring(0,host.indexOf("/")) + "/");

        for (String s : split) {
            stringBuilder.append(s + "/");
        }

        return stringBuilder.toString();
    }

    /**
     * 封装请求头
     */
    private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        if(headers != null && headers.size() > 0) {
            headers.forEach(httpMethod::setHeader);
        }
    }

    /**
     * 封装请求参数
     * HttpPost、HttpPut
     */
    private static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) throws UnsupportedEncodingException {
        if (null != params && params.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<>();

            params.forEach((k, v) -> {
                nvps.add(new BasicNameValuePair(k, v));
            });

            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, HttpConfig.ENCODING));
        }
    }

    /**
     * 统一返回结果
     */
    private static HttpResult getHttpResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);
        HttpResult httpResult = new HttpResult();
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            if (httpResponse.getEntity() != null) {
                httpResult.setData(EntityUtils.toString(httpResponse.getEntity(), HttpConfig.ENCODING));
                httpResult.setHeaders(httpResponse.getAllHeaders());
                httpResult  .setCode(httpResponse.getStatusLine().getStatusCode());
            }
        }
        return httpResult;
    }

    private static void close(Closeable... resources) {
        try {
            for (Closeable resource : resources) {
                if (resource != null) {
                    resource.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static HttpResult get(String url, Map<String, String> headers, Map<String, String> params, int connectionTimeout, int responseTimeout) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);

        //拼接参数
        if (params != null && params.size() > 0) {
            params.forEach(uriBuilder::setParameter);
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpResult(httpResponse, httpClient, httpGet);
        } finally {
            close(httpResponse);
        }
    }

    public static HttpResult post(String url) throws UnsupportedEncodingException {
        return post(url,null);
    }
    public static HttpResult post(String url, Map<String, String> params) throws UnsupportedEncodingException {
        return post(url, params,null);
    }
    public static HttpResult post(String url, Map<String, String> headers, Map<String, String> params) throws UnsupportedEncodingException {
        return post(url, headers, params, HttpConfig.CONNECTION_TIMEOUT, HttpConfig.RESPONSE_TIMEOUT);
    }

    public static HttpResult post(String url, Map<String, String> headers, Map<String, String> params, int connectionTimeout, int responseTimeout) throws UnsupportedEncodingException {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpPost.setConfig(requestConfig);

        //设置请求头
        packageHeader(headers, httpPost);

        //封装参数
        packageParam(params, httpPost);

        //创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpResult(httpResponse,httpClient,httpPost);
        }catch (Exception e) {
            return null;
        }finally {
            //释放资源
            close(httpResponse);
        }
    }


    public static HttpResult postJson(String url, Map<String, String> headers, String jsonParams, int connectionTimeout, int responseTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpPost.setConfig(requestConfig);

        //设置请求头
        headers.put("content-type", "application/json");
        packageHeader(headers, httpPost);

        //设置参数
        httpPost.setEntity(new StringEntity(jsonParams,HttpConfig.ENCODING));

        //创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpResult(httpResponse,httpClient,httpPost);
        } finally {
            close(httpResponse);
        }
    }

    public static HttpResult postXml(String url, Map<String, String> headers, String xmlParams, int connectionTimeout, int responseTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpPost.setConfig(requestConfig);

        //设置请求头
        httpPost.addHeader("Content-Type", "text/xml");
        packageHeader(headers, httpPost);

        //设置参数
        httpPost.setEntity(new StringEntity(xmlParams, HttpConfig.ENCODING));

        //创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpResult(httpResponse,httpClient,httpPost);
        } finally {
            close(httpResponse);
        }
    }


    public static HttpResult delete(String url, Map<String, String> headers, int connectionTimeout, int responseTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建http对象
        HttpDelete httpDelete = new HttpDelete(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpDelete.setConfig(requestConfig);

        //设置请求头
        packageHeader(headers, httpDelete);

        //创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpResult(httpResponse,httpClient,httpDelete);
        } finally {
            //释放资源
            close(httpResponse);
        }
    }


    public static HttpResult put(String url, Map<String, String> headers, Map<String, String> params, int connectionTimeout, int responseTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建http对象
        HttpPut httpPut = new HttpPut(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpPut.setConfig(requestConfig);

        //设置请求头
        packageHeader(headers, httpPut);

        //封装参数
        packageParam(params, httpPut);

        //创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpResult(httpResponse,httpClient,httpPut);
        } finally {
            //释放资源
            close(httpResponse);
        }
    }


    public static HttpResult patch(String url, Map<String, String> headers, Map<String, String> params, int connectionTimeout, int responseTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建http对象
        HttpPatch httpPatch = new HttpPatch(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpPatch.setConfig(requestConfig);

        //设置请求头
        packageHeader(headers, httpPatch);

        //封装参数
        packageParam(params, httpPatch);

        //创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        try {
            return getHttpResult(httpResponse,httpClient,httpPatch);
        } finally {
            //释放资源
            close(httpResponse);
        }
    }

    public static File download(String url, File storagePath) throws Exception {
        return download(url,storagePath,null);
    }

    public static File download(String url, File storagePath, Map<String, String> headers) throws Exception {
        return download(url,storagePath,headers,null);
    }

    public static File download(String url, File storagePath, Map<String, String> headers, Map<String, String> params) throws Exception {
        return download(url,storagePath,headers,params,HttpConfig.CONNECTION_TIMEOUT,HttpConfig.RESPONSE_TIMEOUT);
    }

    public static File download(String url, File storagePath, Map<String, String> headers, Map<String, String> params , int connectionTimeout, int responseTimeout) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = getHttpClient();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);

        //拼接参数
        if (params != null && params.size() > 0) {
            params.forEach(uriBuilder::setParameter);
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        OutputStream out = null;
        InputStream in = null;


        try {
            httpResponse = httpClient.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();

            String fileName = null;

            if (entity.getContentLength() <= 0) {
                fileName = url.substring(url.lastIndexOf("/") + 1);

            }else {
                Header firstHeader = httpResponse.getFirstHeader("content-disposition");

                for (HeaderElement element : firstHeader.getElements()) {
                    fileName = element.getParameterByName("filename").getValue();
                    if (null != fileName) {
                        break;
                    }
                }
            }
            storagePath.mkdirs();

            out = new FileOutputStream(storagePath.getCanonicalPath() + "/" + fileName);

            in = httpResponse.getEntity().getContent();

            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            out.flush();

            return new File(storagePath.getCanonicalPath() + "/" + fileName);
        }finally {
            close(httpResponse,out,in);
        }
    }

    public static HttpResult upload(String url, File file, Map<String, String> headers, Map<String, String> params, int connectionTimeout, int responseTimeout) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        packageHeader(headers,httpPost);
        //把文件转换成流对象FileBody
        FileBody fileBody = new FileBody(file);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(responseTimeout).build();

        httpPost.setConfig(requestConfig);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .addPart("file", fileBody);// 相当于<input type="file" name="file"/>

        params.forEach((k, v) -> {
            // 相当于<input type="text" name="k" value=v>
            multipartEntityBuilder.addPart(k,new StringBody(v, ContentType.create("text/plain", HttpConfig.ENCODING)));
        });
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        try {
            return getHttpResult(response,httpClient,httpPost);
        }finally {
            EntityUtils.consume(httpEntity);
            close(response);
        }
    }



}
