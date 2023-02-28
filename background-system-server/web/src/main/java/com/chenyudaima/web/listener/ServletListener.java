package com.chenyudaima.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet监听器
 * @author 沉鱼代码
 * @date 2023/2/7
 */
@WebListener
@Slf4j
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ServletContext初始化！！！");
        log.info(sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContext销毁");
    }
}
