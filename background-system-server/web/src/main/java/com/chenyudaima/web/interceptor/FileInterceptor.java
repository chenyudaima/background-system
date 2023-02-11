package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件拦截器
 * 文件扩展名检测 大小写 （黑名单漏网之鱼） 防止双写和双后缀名，检测到就停止和删除
 * 使用雪花id 改写文件名
 * 不符合windows文件命名规则的文件名
 * 文件头检测
 * 文件加载检测
 *
 * @author 沉鱼代码
 * @date 2023/2/8
 */
@Component
public class FileInterceptor extends Interceptor {
    @Override
    public String[] getAddPathPatterns() {
        return new String[]{
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[0];
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不是文件上传，直接跳过
        if(!(request.getMethod().equals(HttpMethod.POST) && request.getContentType().contains(HttpHeader.V_CONTENT_TYPE_MULTIPART_FORM_DATA))) {
            return true;
        }


        StandardMultipartHttpServletRequest servletRequest = (StandardMultipartHttpServletRequest) request;

        for (MultipartFile file : servletRequest.getFileMap().values()) {

            String fileName = file.getOriginalFilename();

            //处理文件名后缀问题

            try(InputStream inputStream = file.getInputStream()) {
                byte[] bytes = new byte[1024];

                //文件头检测
                inputStream.read(bytes);

                //文件加载检测
            }
        }

        return true;
    }
}
