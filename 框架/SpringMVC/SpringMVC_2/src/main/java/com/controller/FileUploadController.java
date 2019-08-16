package com.controller;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/16 9:16
 * @Version V1.0
 */
@Controller
@RequestMapping(value = "/fileUpload")
public class FileUploadController {
    // File.separator 文件分隔符
    private String path = "D:" + File.separator + "upload";
    // 普通测试
    @RequestMapping(value = "/testFileUpload")
    public String testFileUpload(){
        System.out.println("欢迎访问FileUploadController里的testFileUpload方法！");
        return "success";
    }

    // 不使用配置文件解析器组件文件上传
    @RequestMapping(value = "/testFileUpload1")
    public String testFileUpload1(HttpServletRequest request) throws Exception {
        System.out.println("欢迎访问FileUploadController里的testFileUpload1方法！");
        // 上传文件存放到哪？指定上传文件存放的路径webapp下upload
//        String path = request.getSession().getServletContext().getRealPath("/upload/");
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 文件上传对象
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        // 解析request请求
        List<FileItem> fileItems = fileUpload.parseRequest(request);
        for (FileItem fileItem : fileItems) {
            if(fileItem.isFormField()){
                // 此时查找的是表单中的除了<input type=file/>的其他表单
            }else{
                // 处理文件上传
                // 文件名（唯一）
                String fileName = fileItem.getName();
                // sdfsdfs2342342-234234dssfsdf-23423423ds -->sdfsdfs2342342234234dssfsdf23423423ds
                String uuid = UUID.randomUUID().toString().replace("-","").toUpperCase(); // 变成大写
                fileName = uuid+"_"+fileName;
                // 完成上传
                fileItem.write(new File(file,fileName));
                // 删除临时文件
                fileItem.delete();
            }
        }
        return "success";
    }


    // 使用配置文件解析器组件普通文件上传
    @RequestMapping(value = "/testFileUpload2")
    public String testFileUpload2(HttpServletRequest request, MultipartFile upload) throws Exception {
        System.out.println("欢迎访问FileUploadController里的testFileUpload2方法！");
        // 上传文件存放到哪？指定上传文件存放的路径webapp下upload
//        String path = request.getSession().getServletContext().getRealPath("/upload/");
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 处理文件上传
        // 文件名（唯一）
        String fileName = upload.getOriginalFilename();
        // sdfsdfs2342342-234234dssfsdf-23423423ds -->sdfsdfs2342342234234dssfsdf23423423ds
        String uuid = UUID.randomUUID().toString().replace("-","").toUpperCase(); // 变成大写
        fileName = uuid+"_"+fileName;
        // 完成上传
        upload.transferTo(new File(file,fileName));
        return "success";
    }

    // 跨服务器的文件上传
    @RequestMapping(value = "/testFileUpload3")
    public String testFileUpload3(MultipartFile upload, Model model) throws Exception {
        System.out.println("欢迎访问FileUploadController里的testFileUpload3方法！");

        String path = "http://localhost:9090/SpringMVC_1/upload/";

        // 处理文件上传
        // 文件名（唯一）
        String fileName = upload.getOriginalFilename();
        // sdfsdfs2342342-234234dssfsdf-23423423ds -->sdfsdfs2342342234234dssfsdf23423423ds
        String uuid = UUID.randomUUID().toString().replace("-","").toUpperCase(); // 变成大写
        fileName = uuid+"_"+fileName;
        // 完成上传
        Client client = Client.create();
        WebResource webResource = client.resource(path + fileName);
        webResource.put(upload.getBytes()); // 跨服务器的文件上传
        model.addAttribute("url",path+fileName);
        return "success";
    }
}
