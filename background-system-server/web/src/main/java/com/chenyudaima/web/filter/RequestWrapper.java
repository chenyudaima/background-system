package com.chenyudaima.web.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * HttpServletRequest加强
 * @author 沉鱼代码
 * @date 2023/2/6
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    /**
     * 用于将流保存下来
     */
    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public int read(@NotNull byte[] b) throws IOException {
                return inputStream.read(b);
            }

            @Override
            public int read(@NotNull byte[] b, int off, int len) throws IOException {
                return inputStream.read(b, off, len);
            }
        };

    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
