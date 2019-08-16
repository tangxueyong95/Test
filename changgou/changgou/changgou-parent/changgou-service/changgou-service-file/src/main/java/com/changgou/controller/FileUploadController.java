package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*****
 * @Author: www.itheima.com
 * @Date: 2019/7/27 11:10
 * @Description: com.changgou.controller
 ****/
@RestController
@CrossOrigin        //跨域
public class FileUploadController {

    /***
     * 文件上传
     * http://localhost:18082/upload   POST
     * @param file:MultipartFile  上传的文件封装对象
     *            MultipartFile.getOriginalFilename()获取文件名字    例如：heheh.jpeg
     *            StringUtils.getFilenameExtension("heheh.jpeg")     值jpeg
     * @return 文件的访问URL
     */
    @PostMapping(value = "/upload")
    public String upload(@RequestParam(value = "file")MultipartFile file) throws Exception{
        //封装一个文件上传对象 FastDFSFile
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getOriginalFilename(),     //文件名字
                file.getBytes(),                //文件字节数组
                StringUtils.getFilenameExtension(file.getOriginalFilename())); //获取文件扩展名

        /***
         * 调用FastDFSClient工具类实现将文件上传到FastDFS中
         * 返回值uploads：
         *     1:组名 group1
         *     2:文件存储路径
         */
        String[] uploads = FastDFSClient.upload(fastDFSFile);
        //拼接访问地址  http://192.168.211.132:8080/group1/M00/00/44/SDFSFDSFSDFSDFSDF.png
        //String url = "http://192.168.211.132:8080/"+uploads[0]+"/"+uploads[1];
        //Nginx默认8080端口没有发生过变化
        String url = FastDFSClient.getTrackerUrl()+"/"+uploads[0]+"/"+uploads[1];
        return url;
    }

}
