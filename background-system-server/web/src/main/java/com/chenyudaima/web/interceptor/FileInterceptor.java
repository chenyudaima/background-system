package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件上传拦截器
 * 文件扩展名检测 ，使用白名单的方式， （黑名单会有漏网之鱼） 防止大小写，双写和双后缀名
 * 改文件名
 * 文件头检测
 * 文件加载检测
 * 检测到非法的就停止和删除
 * @author 沉鱼代码
 * @date 2023/2/8
 */
@Component
public class FileInterceptor extends Interceptor {
    @Value("${servlet.multipart.whitelist}")
    private List<String> whitelist;

    @Override
    public String[] getAddPathPatterns() {
        return new String[] {
                "/**"
        };
    }

    @Override
    public String[] getExcludePathPatterns() {
        return new String[] {
                "/test/**"
        };
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不是文件上传，直接跳过
        if(!(request.getMethod().equals(HttpMethod.POST) && request.getContentType().contains(HttpHeader.V_CONTENT_TYPE_MULTIPART_FORM_DATA))) {
            return true;
        }

        StandardMultipartHttpServletRequest servletRequest = (StandardMultipartHttpServletRequest) request;

        for (MultipartFile file : servletRequest.getFileMap().values()) {

            //获取文件全名
            String fileName = file.getOriginalFilename();
            assert fileName != null;

            //去除没有后缀名的文件
            if(!fileName.contains(".")) {
                throw new RuntimeException("非法文件");
            }

            //白名单过滤
            //截取点后面的后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

            //匹配后缀名是否在白名单之内
            if(!whitelist.contains(fileType)) {
                throw new RuntimeException("非法文件");
            }

            //文件头检测

            //try(InputStream inputStream = file.getInputStream()) {
            //    byte[] bytes = new byte[1024];
            //    inputStream.read(bytes);
            //}

            //文件加载检测

            //修改为本系统给的文件名


        }

        return true;
    }
}
