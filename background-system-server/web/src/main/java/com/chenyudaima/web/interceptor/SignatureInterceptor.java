package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeaderConstant;
import com.chenyudaima.exception.SignatureException;
import com.chenyudaima.util.HttpDataUtil;
import com.chenyudaima.util.SignatureUtil;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 验签，防重放 拦截器
 * @author 沉鱼代码
 * @date 2023/2/6
 */
@Component
public class SignatureInterceptor extends Interceptor {
    @Override
    public String[] getAddPathPatterns() {
        return new String[]{
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[]{};
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取参数签名
        String signature = request.getHeader(HttpHeaderConstant.K_REQUEST_HEADER_SIGNATURE);

        if(signature.isEmpty()) {
            throw new SignatureException("没有参数签名");
        }

        //获取accessKey
        String accessKey = request.getHeader(HttpHeaderConstant.K_REQUEST_HEADER_AUTHORIZATION);

        Map<String, String> params = HttpDataUtil.getParams(request);

        //参与加密的accessKey
        params.put("accessKey", accessKey);

        //验证参数签名
        if(!SignatureUtil.jointVerify(signature, params)) {
            throw new SignatureException("非法篡改参数");
        }

        return true;
    }
}
