package com.chenyudaima.util.file;

import com.chenyudaima.constant.Property;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.function.Consumer;

/**
 * 文件操作工具类
 */
public class FileUtil {

    /**
     * 根据文件名和字节创建临时文件
     */
    public static File createFile(String fileName, byte[] bytes) {
        File file = new File(System.getProperty(Property.JAVA_IO_TMPDIR) + fileName);

        try(OutputStream os = Files.newOutputStream(file.toPath())) {
            os.write(bytes);
            os.flush();
            return file;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取文件字节
     * @param file 文件
     * @return 文件的字节
     */
    public static byte[] getByte(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * 复制文件
     * @param source 文件
     * @param dest 复制的路径 (D:/e)
     * @return 是否复制成功
     */
    public static boolean copyFile(File source, String dest) {
        File destFile = new File(dest);

        //如果目录不存在则创建
        if(!destFile.exists()) destFile.mkdirs();

        dest = destFile.getPath()  + "/" + source.getName();
        destFile = new File(dest);

        try(FileChannel inputChannel = new FileInputStream(source).getChannel();
            FileChannel outputChannel = new FileOutputStream(destFile).getChannel()
        ) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /**
     * 移动文件
     * @param source 文件
     * @param dest 移动的路径 (D:/e)
     * @return 是否移动成功
     */
    public static boolean moveFile(File source, String dest) {
        File destFile = new File(dest);
        destFile.mkdirs();
        dest = destFile.getPath() + "/" + source.getName();
        destFile = new File(dest);
        return source.renameTo(destFile);
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

    /**
     * 如果是文件不会执行查找过滤
     * @param file 目录或文件
     * @param consumer 执行策略
     * @param fileFilter 查找过滤
     */
    public static void searchOut(File file, Consumer<File> consumer, FileFilter fileFilter) {

        //如果是文件就直接执行
        if(file.isFile()) {
            consumer.accept(file);
            return;
        }

        //如果是目录就递归查找 并且过滤
        for(File fn: file.listFiles(fileFilter)) {
            searchOut(fn, consumer, fileFilter);
        }
    }

    /**
     * 判断文件字符集是否是gbk
     */
    public static boolean isGbk(File file) throws IOException {
        boolean isGbk = true;
        byte[] buffer = Files.readAllBytes(file.toPath());
        int end = buffer.length;
        for (int i = 0; i < end; i++) {
            byte temp = buffer[i];
            if ((temp & 0x80) == 0) {
                continue;// B0A1-F7FE//A1A1-A9FE
            } else if ((Byte.toUnsignedInt(temp) < 0xAA && Byte.toUnsignedInt(temp) > 0xA0)
                    || (Byte.toUnsignedInt(temp) < 0xF8 && Byte.toUnsignedInt(temp) > 0xAF)) {
                if (i + 1 < end) {
                    if (Byte.toUnsignedInt(buffer[i + 1]) < 0xFF && Byte.toUnsignedInt(buffer[i + 1]) > 0xA0
                            && Byte.toUnsignedInt(buffer[i + 1]) != 0x7F) {
                        i = i + 1;
                        continue;
                    }
                } // 8140-A0FE
            } else if (Byte.toUnsignedInt(temp) < 0xA1 && Byte.toUnsignedInt(temp) > 0x80) {
                if (i + 1 < end) {
                    if (Byte.toUnsignedInt(buffer[i + 1]) < 0xFF && Byte.toUnsignedInt(buffer[i + 1]) > 0x3F
                            && Byte.toUnsignedInt(buffer[i + 1]) != 0x7F) {
                        i = i + 1;
                        continue;
                    }
                } // AA40-FEA0//A840-A9A0
            } else if ((Byte.toUnsignedInt(temp) < 0xFF && Byte.toUnsignedInt(temp) > 0xA9)
                    || (Byte.toUnsignedInt(temp) < 0xAA && Byte.toUnsignedInt(temp) > 0xA7)) {
                if (i + 1 < end) {
                    if (Byte.toUnsignedInt(buffer[i + 1]) < 0xA1 && Byte.toUnsignedInt(buffer[i + 1]) > 0x3F
                            && Byte.toUnsignedInt(buffer[i + 1]) != 0x7F) {
                        i = i + 1;
                        continue;
                    }
                }
            }
            isGbk = false;
            break;
        }
        return isGbk;
    }

    /**
     * 判断文件字符集是不是UTF-8
     */
    public static boolean isUtf8(File file) throws IOException {
        boolean isUtf8 = true;
        byte[] buffer = Files.readAllBytes(file.toPath());
        int end = buffer.length;
        for (int i = 0; i < end; i++) {
            byte temp = buffer[i];
            if ((temp & 0x80) == 0) {// 0xxxxxxx
                continue;
            } else if ((temp & 0xC0) == 0xC0 && (temp & 0x20) == 0) {// 110xxxxx 10xxxxxx
                if (i + 1 < end && (buffer[i + 1] & 0x80) == 0x80 && (buffer[i + 1] & 0x40) == 0) {
                    i = i + 1;
                    continue;
                }
            } else if ((temp & 0xE0) == 0xE0 && (temp & 0x10) == 0) {// 1110xxxx 10xxxxxx 10xxxxxx
                if (i + 2 < end && (buffer[i + 1] & 0x80) == 0x80 && (buffer[i + 1] & 0x40) == 0
                        && (buffer[i + 2] & 0x80) == 0x80 && (buffer[i + 2] & 0x40) == 0) {
                    i = i + 2;
                    continue;
                }
            } else if ((temp & 0xF0) == 0xF0 && (temp & 0x08) == 0) {// 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
                if (i + 3 < end && (buffer[i + 1] & 0x80) == 0x80 && (buffer[i + 1] & 0x40) == 0
                        && (buffer[i + 2] & 0x80) == 0x80 && (buffer[i + 2] & 0x40) == 0
                        && (buffer[i + 3] & 0x80) == 0x80 && (buffer[i + 3] & 0x40) == 0) {
                    i = i + 3;
                    continue;
                }
            }
            isUtf8 = false;
            break;
        }
        return isUtf8;
    }

}
