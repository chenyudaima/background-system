package com.chenyudaima.util;


import com.alibaba.fastjson.JSON;
import com.chenyudaima.constant.CharSetConstant;
import com.chenyudaima.constant.HttpHeaderConstant;
import com.chenyudaima.constant.HttpMethodConstant;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * 获取http请求数据工具类
 *
 * @author 沉鱼代码
 * @date 2023/2/6
 */
public class HttpDataUtil {

    /**
     * 获取http请求的参数
     */
    public static Map<String, String> getParams(HttpServletRequest request) throws Exception {

        if (
                (request.getMethod().equals(HttpMethodConstant.POST) ||
            request.getMethod().equals(HttpMethodConstant.PUT) ||
            request.getMethod().equals(HttpMethodConstant.PATCH) ||
            request.getMethod().equals(HttpMethodConstant.DELETE))
            && request.getContentType().contains(HttpHeaderConstant.V_CONTENT_TYPE_APPLICATION_JSON)
        ) {
            return HttpDataUtil.getPostJsonParams(request);

        } else if (request.getMethod().equals(HttpMethodConstant.GET)) {

            return HttpDataUtil.getUrlParams(request);

        } else if ((request.getMethod().equals(HttpMethodConstant.POST) ||
                request.getMethod().equals(HttpMethodConstant.PUT) ||
                request.getMethod().equals(HttpMethodConstant.PATCH) ||
                request.getMethod().equals(HttpMethodConstant.DELETE))
                && (request.getContentType().contains(HttpHeaderConstant.V_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
                ||
                request.getContentType().contains(HttpHeaderConstant.V_CONTENT_TYPE_MULTIPART_FORM_DATA))
        ) {
            return HttpDataUtil.getPostFormParams(request);
        }


        return null;
    }

    /**
     * 获取POST请求中Body的json参数 application/json
     */
    public static Map<String, String> getPostJsonParams(HttpServletRequest request) throws Exception {
        String json = new String(StreamUtils.copyToByteArray(request.getInputStream()));

        SortedMap<String, String> sortedMap = new TreeMap<>();

        return JSON.parseObject(json, SortedMap.class);
    }


    /**
     * 获取POST请求中Body的Key-Value参数 application/x-www-form-urlencoded | multipart/form-data
     */
    public static Map<String, String> getPostFormParams(HttpServletRequest request) throws Exception {
        //Enumeration<String> parameterNames = request.getParameterNames();
        //while(parameterNames.hasMoreElements()) {
        //    Sting value = request.getParameter(parameterNames.nextElement());
        //}

        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String, String> treeMap = new TreeMap<>();

        parameterMap.forEach((k,v) -> {
            treeMap.put(k, v[0]);
        });

        return treeMap;
    }

    /**
     * 获取http路径参数
     */
    public static SortedMap<String, String> getUrlParams(HttpServletRequest request) throws Exception {
        if (request.getQueryString().isEmpty()) {
            return null;
        }


        String[] params = URLDecoder.decode(request.getQueryString(), CharSetConstant.UTF8).split("&");

        SortedMap<String, String> result = new TreeMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            result.put(keyValue[0], keyValue[1]);
        }
        return result;
    }
}
