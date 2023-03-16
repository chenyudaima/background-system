package com.chenyudaima.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.chenyudaima.constant.CharSet;
import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * 获取HttpServletRequest请求数据工具类
 *
 * @author 沉鱼代码
 * @date 2023/2/6
 */
public class HttpDataUtil {

    /**
     * 获取http请求的参数
     */
    public static TreeMap<String, String> getParams(HttpServletRequest request) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            return HttpDataUtil.getUrlParams(request);
        }


        if ((request.getMethod().equals(HttpMethod.POST) ||
                request.getMethod().equals(HttpMethod.PUT) ||
                request.getMethod().equals(HttpMethod.PATCH) ||
                request.getMethod().equals(HttpMethod.DELETE))
                &&
                request.getContentType().contains(HttpHeader.V_CONTENT_TYPE_APPLICATION_JSON)) {
            return HttpDataUtil.getPostJsonParams(request);
        }


        if ((request.getMethod().equals(HttpMethod.POST) ||
                request.getMethod().equals(HttpMethod.PUT) ||
                request.getMethod().equals(HttpMethod.PATCH)
                        &&
                        (request.getContentType().contains(HttpHeader.V_CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED) ||
                                request.getContentType().contains(HttpHeader.V_CONTENT_TYPE_MULTIPART_FORM_DATA)))
        ) {
            return HttpDataUtil.getPostFormParams(request);
        }


        return new TreeMap<>();
    }

    /**
     * 获取POST请求中Body的json参数 application/json
     */
    public static TreeMap<String, String> getPostJsonParams(HttpServletRequest request) throws Exception {
        String json = new String(StreamUtils.copyToByteArray(request.getInputStream()));

        return JSON.parseObject(json, new TypeReference<TreeMap<String, String>>() {
        });

    }


    /**
     * POST application/x-www-form-urlencoded 获取请求中Body的Key-Value参数
     * <p>
     * POST multipart/form-data SpringBoot会自动转换成StandardMultipartHttpServletRequest，可以继续使用request.getParameterMap()获取POST请求中Body的二进制参数
     */
    public static TreeMap<String, String> getPostFormParams(HttpServletRequest request) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();

        TreeMap<String, String> treeMap = new TreeMap<>();

        parameterMap.forEach((k, v) -> treeMap.put(k, v[0]));

        return treeMap;
    }

    /**
     * 获取http路径参数（value空的也会获取，就是map中的value是null）
     *
     */
    public static TreeMap<String, String> getUrlParams(HttpServletRequest request) throws Exception {
        if (request.getQueryString().isEmpty()) {
            return new TreeMap<>();
        }

        String[] params = URLDecoder.decode(request.getQueryString(), CharSet.UTF8).split("&");

        TreeMap<String, String> result = new TreeMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");

            if(keyValue.length > 1) {
                result.put(keyValue[0], keyValue[1]);
            }else {
                result.put(keyValue[0], "");
            }
        }
        return result;
    }
}
