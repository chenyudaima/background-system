package com.chenyudaima.web.controller.business;

import com.chenyudaima.model.Result;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 沉鱼代码
 * @date 2023/4/28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/business/file")
public class FileUploadController {

    /**
     * 分片文件临时储存路径
     */
    private String fileBurstPath = "D:/temporary/";

    List<String> fileMd5List = new ArrayList<>();

    /**
     * 每次上传文件前调用的接口
     * @param guid 文件的唯一标识（前端计算的文件md5值）
     * @return 返回文件上传结果
     */
    @GetMapping("/resume")
    public Result<?> checkshard(String guid) {
        //查询数据库是否存在这个文件的md5值，存在则通知前端，实现秒传
        if(fileMd5List.contains(guid)) {
            return Result.success(-2);
        }

        //读取当前文件的分片目录
        File files = new File(fileBurstPath + guid);

        File[] child = files.listFiles();

        //文件没有上传过
        if (null == child) return Result.success(-1);


        //文件上传中断过，返回当前已经上传到的下标
        return Result.success(child.length - 1);
    }

    /**
     * 普通上传接口
     * @param guid
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public void upload(String guid, MultipartFile file) throws IOException {
        //检查是否有文件上传
        if(file.isEmpty()) return;

        //当前文件的存储路径
        File parentFileDir = new File(fileBurstPath + guid);

        //如果guid文件夹不存在则创建
        if (!parentFileDir.exists()) {
            parentFileDir.mkdirs();
        }

        //把分片文件输出到 parentFileDir 目录下
        File tempPartFile = new File(parentFileDir, file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
    }

    /**
     * 分片上传接口
     * @param guid   当前文件的唯一标识(文件md5)   根据唯一标识，把分片文件放到唯一标识目录下
     * @param chunk   当前上传的第n个分片
     * @param file   分片文件
     * @param chunks 当前文件分片总数量
     * @throws Exception
     */
    @PostMapping("/burstUpload")
    public void burstUpload(String guid, Integer chunk, MultipartFile file, Integer chunks) throws Exception {
        //检查是否有文件上传
        if(file.isEmpty()) return;

        //当前文件的分片存储路径
        File parentFileDir = new File(fileBurstPath + guid);

        //如果guid文件夹不存在则创建
        if (!parentFileDir.exists()) {
            parentFileDir.mkdirs();
        }

        //把分片文件输出到 parentFileDir 目录下
        File tempPartFile = new File(parentFileDir, chunk.toString());
        FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
    }

    /**
     * 合并文件接口
     * 当前端上传完成之后调用函数，请求合并文件
     * @param guid 文件的唯一标识 （文件md5）
     * @param expName 文件扩展名 （合并的文件类型）
     * @throws Exception
     */
    @PostMapping("/merge")
    public Result<?> mergeFile(String guid, String expName) {
        //分片的文件的存放路径
        File parentFileDir = new File(fileBurstPath + guid);

        //如果分片文件夹不存在
        if (!parentFileDir.exists() || !parentFileDir.isDirectory()) {
            return Result.error();
        }

        //合并分片的文件名
        String fileName = System.currentTimeMillis() + "." +expName;

        //合并分片的路径
        File destTempFile = new File("D:/", fileName);

        try(FileOutputStream destTempfos = new FileOutputStream(destTempFile, true)) {

            //先创建文件destTempFile
            if (!destTempFile.exists()) destTempFile.createNewFile();

            //循环分片文件，二进制数据追加到文件destTempFile中
            for (int i = 0; i < parentFileDir.listFiles().length; i++) {
                File partFile = new File(parentFileDir,i+"");
                FileUtils.copyFile(partFile, destTempfos);
            }

            //追加完之后在清空流
            destTempfos.flush();

            //删除临时目录中的分片文件夹及文件
            FileUtils.deleteDirectory(parentFileDir);

            //返回生成的文件名
            return Result.success(fileName);

        } catch (Exception e) {
            return Result.error();
        }
    }


}
