package com.chenyudaima.util.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.Header;
import java.io.Serializable;

/**
 * http请求响应结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
