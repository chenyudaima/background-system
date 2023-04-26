package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpParam;
import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.exception.SignException;
import com.chenyudaima.util.HttpDataUtil;
import com.chenyudaima.util.RedisUtil;
import com.chenyudaima.util.SignUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * 验参（是否被篡改），防重放 拦截器
 * @author 沉鱼代码
 * @date 2023/2/6
 */
@Component
@RequiredArgsConstructor
public class SignInterceptor extends Interceptor {
    private final RedisUtil redisUtil;

    @Override
    public String[] getAddPathPatterns() {
        return new String[]{
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[]{
                "/test/**"
        };
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取参数签名
        String signature = request.getHeader(HttpHeader.K_REQUEST_HEADER_SIGNATURE);

        if(signature == null) {
            throw new SignException("没有参数签名");
        }

        //获取用户请求的参数
        TreeMap<String, String> params = HttpDataUtil.getParams(request);

        //参与加密的accessKey
        params.put(HttpParam.ACCESS_KEY, request.getHeader(HttpHeader.K_REQUEST_HEADER_AUTHORIZATION));

        //验签
        if(!SignUtil.verify(signature, params)) {
            throw new SignException("非法篡改参数");
        }

        //判断时间戳是否大于当前时间1分钟
        if((System.currentTimeMillis() - 60000) > Long.parseLong(params.get(HttpParam.TIMESTAMP))) {
            throw new SignException("请求超时，请重新发起请求");
        }

        //防重放
        String nonce = params.get(HttpParam.NONCE);
        assert nonce != null;


        if(redisUtil.hasKey(RedisKey.NONCE + nonce)) {
            throw new SignException("重复请求");
        }

        redisUtil.set(RedisKey.NONCE + nonce,null, 15, TimeUnit.MINUTES);

        return true;
    }
}
