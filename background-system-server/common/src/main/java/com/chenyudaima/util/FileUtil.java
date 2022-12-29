package com.chenyudaima.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 文件操作工具类
 */
public class FileUtil{


    /**
     * 复制文件
     * @param source 源文件 D:/w/a.txt
     * @param dest 需要复制到哪个路径下 D:/img
     */
    public static void copyFileUsingFileChannels(File source, String dest) {
        File destFile = new File(dest);

        //如果目录不存在则创建
        if(!destFile.exists()) destFile.mkdirs();


        dest = dest+"/"+source.getName();
        destFile = new File(dest);

        try(FileChannel inputChannel = new FileInputStream(source).getChannel();
            FileChannel outputChannel = new FileOutputStream(destFile).getChannel()
        ) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 移动文件
     * @param source 源文件
     * @param dest 需要移动到哪个路径下
     */
    public static void moveFile(File source, String dest) {
        File destFile = new File(dest);
        destFile.mkdirs();
        dest = dest + "/" + source.getName();
        destFile = new File(dest);
        source.renameTo(destFile);
    }

    /**
     * 解决file.delete()有时候删除不了文件
     */
    public static void deleteFile(File file) {
        //删除失败继续循环
        while(!file.delete()) {
            System.gc();
        }
    }
}
