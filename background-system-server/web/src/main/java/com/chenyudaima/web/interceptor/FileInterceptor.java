package com.chenyudaima.web.interceptor;

import com.chenyudaima.constant.HttpHeader;
import com.chenyudaima.constant.HttpMethod;
import com.chenyudaima.exception.request.RequestHeaderException;
import com.chenyudaima.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 文件名类型白名单
     */
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
        //if(!(request.getMethod().equals(HttpMethod.POST) && request.getContentType().contains(HttpHeader.V_CONTENT_TYPE_MULTIPART_FORM_DATA))) {
        //    return true;
        //}

        if(!(request instanceof StandardMultipartHttpServletRequest)) {
            return true;
        }

        StandardMultipartHttpServletRequest servletRequest = (StandardMultipartHttpServletRequest) request;

        //检测所有文件
        for (MultipartFile file : servletRequest.getFileMap().values()) {

            //获取文件全名
            String fileName = file.getOriginalFilename();
            assert fileName != null;

            //去除没有后缀名的文件
            if(!fileName.contains(".")) {
                throw new IllegalArgumentException("非法文件");
            }

            //白名单过滤
            //截取点后面的后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

            if(fileType.isEmpty()) {
                throw new IllegalArgumentException("非法文件");
            }

            //匹配后缀名是否在白名单之内
            if(!whitelist.contains(fileType)) {
                throw new IllegalArgumentException("文件类型不在白名单之内");
            }

            //文件头检测 判断文件头检测的类型是否和后缀名相同
            try(InputStream inputStream = file.getInputStream()) {
                String type = FileUtil.getFileTypeByMagicNumber(inputStream);
                if(!fileType.equals(type)) {
                    throw new IllegalArgumentException("文件内容和后缀不匹配");
                }
            }

            //文件加载检测



            //修改为本系统给的文件名，在控制器做处理比较好，因为有时候需要文件名

        }

        return true;
    }

}
