package com.chenyudaima.model;

import org.apache.http.Header;
import java.io.Serializable;

/**
 * http请求响应结果
 */
public class HttpResult implements Serializable {
    private static final long serialVersionUID = 2168152194164783950L;

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应头
     */
    private Header[] headers;

    /**
     * 响应数据
     */
    private String data;

    public HttpResult() {
    }

    public HttpResult(int code, Header[] headers, String data) {
        this.code = code;
        this.headers = headers;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
