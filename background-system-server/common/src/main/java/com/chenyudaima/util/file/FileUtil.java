package com.chenyudaima.util.file;

import com.chenyudaima.constant.Property;
import org.apache.commons.codec.digest.DigestUtils;


import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 文件操作工具类
 */
public class FileUtil {
    /**
     * 文件类型头部
     */
    private final static HashMap<String, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg");
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        FILE_TYPE_MAP.put("47494638396126026f01", "gif");
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");
        FILE_TYPE_MAP.put("41433130313500000000", "dwg");
        FILE_TYPE_MAP.put("3c21444f435459504520", "html");
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm");
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
        FILE_TYPE_MAP.put("38425053000100000000", "psd");
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc");
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf");
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb");
        FILE_TYPE_MAP.put("464c5601050000000900", "flv");
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv");
        FILE_TYPE_MAP.put("52494646e27807005741", "wav");
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");
        FILE_TYPE_MAP.put("7061636b616765207765", "java");
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
        FILE_TYPE_MAP.put("49545346030000006000", "chm");
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6D6F6F76", "mov");
        FILE_TYPE_MAP.put("FF575043", "wpd");
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");
        FILE_TYPE_MAP.put("2142444E", "pst");
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf");
        FILE_TYPE_MAP.put("E3828596", "pwl");
        FILE_TYPE_MAP.put("2E7261FD", "ram");
    }

    public static String getFileTypeByMagicNumber(File file) throws Exception {
        return getFileTypeByMagicNumber(Files.newInputStream(file.toPath()));
    }

    /**
     * 注意：
     * 1.有些重要的文件，没有固定的文件头
     * TXT 没固定文件头定义
     * TMP 没固定文件头定义
     * INI 没固定文件头定义
     * BIN 没固定文件头定义
     * DBF 没固定文件头定义
     * C 没没固定文件头定义
     * CPP 没固定文件头定义
     * H 没固定文件头定义
     * BAT 没固定文件头定义
     * 2.不同的文件有相同的文件头
     * 4D5A90 EXE
     * 4D5A90 dll
     * 4D5A90 OCX
     * 4D5A90 OLB
     * 4D5A90 IMM
     * 4D5A90 IME
     * 根据文件流中的文件头获取文件的类型
     * @param inputStream
     * @return png  dpf mov
     */
    public static String getFileTypeByMagicNumber(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[3];
        try {
            // 获取文件头前三位魔数的二进制
            inputStream.read(bytes, 0, bytes.length);

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int v = bytes[i] & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }

            String code = stringBuilder.toString();

            for (Map.Entry<String, String> item : FILE_TYPE_MAP.entrySet()) {
                if (item.getKey().contains(code)) {
                    return item.getValue();
                }
            }

            return null;

        }finally {
            inputStream.close();
        }
    }


    /**
     * 计算文件md5
     */
    public static String computeMD5(File file) throws IOException {
        return DigestUtils.md5Hex(Files.newInputStream(file.toPath()));

    }

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
    public static byte[] getBytes(File file) throws Exception {
        return Files.readAllBytes(file.toPath());
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
        if(!destFile.exists()) {
            destFile.mkdirs();
        }

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
