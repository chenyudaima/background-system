package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpParam;
import com.chenyudaima.constant.RedisKey;
import com.chenyudaima.exception.SignException;
import com.chenyudaima.util.HttpDataUtil;
import com.chenyudaima.util.RedisUtil;
import com.chenyudaima.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验参（是否被篡改），防重放 拦截器
 * @author 沉鱼代码
 * @date 2023/2/6
 */
@Component
public class SignInterceptor extends Interceptor {
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
        return 2;
    }

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取参数签名
        String signature = request.getHeader(HttpHeader.K_REQUEST_HEADER_SIGNATURE);

        if(signature.isEmpty()) {
            throw new SignException("没有参数签名");
        }

        Map<String, String> params = HttpDataUtil.getParams(request);

        //参与加密的accessKey
        params.put(HttpParam.ACCESS_KEY, request.getHeader(HttpHeader.K_REQUEST_HEADER_AUTHORIZATION));

        //验证参数签名
        if(!SignUtil.jointVerify(signature, params)) {
            throw new SignException("非法篡改参数");
        }

        //判断时间戳是否大于当前时间1分钟
        long timestamp = Long.parseLong(params.get(HttpParam.TIMESTAMP));
        long currentTimestamp = System.currentTimeMillis() - 60000;
        if(currentTimestamp > timestamp) {
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
