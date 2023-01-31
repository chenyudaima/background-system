package com.chenyudaima.util.file;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileFilter;

/**
 * 当前服务器目录监听工具
 * @author 沉鱼代码
 * @date 2023/1/31
 */
public class DirectoryMonitorUtil {


    /**
     * @param path 监听路径 例如 D:/dev
     * @param fileFilter 文件监听过滤器 file -> return file.getName().toLowerCase().endsWith(".pdf")
     * @param listener 文件变化监听器
     * @param time 监听轮询间隔  TimeUnit.SECONDS.toMillis(1)
     */
    public void monitor(String path, FileFilter fileFilter, FileAlterationListener listener, long time) {

        FileAlterationObserver observer = new FileAlterationObserver(new File(path),fileFilter);

        //添加监听
        observer.addListener(listener);

        //文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(time, observer);

        try {

            //开启监听
            monitor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
