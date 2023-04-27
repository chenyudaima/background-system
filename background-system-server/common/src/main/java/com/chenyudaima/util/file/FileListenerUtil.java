package com.chenyudaima.util.file;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileFilter;

/**
 * 当前服务器文件变化监听工具
 * @author 沉鱼代码
 * @date 2023/1/31
 */
public class FileListenerUtil {

    /**
     * 监听目录变化
     */
    public static void monitor(String path, FileFilter fileFilter, long time, FileAlterationListener... listener) {
        monitor(new File(path), fileFilter, time, listener);
    }


    /**
     * @param file 监听目录路径 例如 D:/dev
     * @param fileFilter 文件监听过滤器 file -> return file.getName().toLowerCase().endsWith(".pdf")
     * @param time 监听轮询间隔  TimeUnit.SECONDS.toMillis(1)
     * @param listener 文件变化监听器
     */
    public static void monitor(File file, FileFilter fileFilter, long time, FileAlterationListener... listener) {
        if(!file.exists() || !file.isDirectory() || listener == null || listener.length == 0) {
            return;
        }

        FileAlterationObserver observer = new FileAlterationObserver(file, fileFilter);

        for (FileAlterationListener fileAlterationListener : listener) {
            //添加监听
            observer.addListener(fileAlterationListener);
        }

        //文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(time, observer);

        try {
            //开启监听
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
