package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/*****
 * @Author: www.itheima.com
 * @Date: 2019/7/27 10:50
 * @Description: com.changgou.util
 * 实现FastDFS文件上传、下载等功能
 ****/
public class FastDFSClient {

    /***
     * 文件上传，读取Tracker服务配置
     */
    static {
        try {
            //获取fdfs_client.conf的路径,new ClassPathResource()获取类路径下的文件路径信息
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            //加载读取fdfs_client.conf配置文件内容
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * 文件上传
     * @param file:FastDFSFile上传的文件封装对象
     */
    public static String[] upload(FastDFSFile file){
        //文件上传后返回数据
        String[] uploadResults = null;
        try {
            //附加数据
            NameValuePair[] meta_list = new NameValuePair[1];
            meta_list[0] = new NameValuePair("author",file.getAuthor());

            //获取StorageClient
            StorageClient storageClient = getStorageClient();

            /***
             * 通过StorageClient操作Storage[文件上传],并获取返回的文件上传信息
             * 1:要上传的文件的字节数组
             * 2:文件的扩展名
             * 3:附加价值对数据
             *
             * 返回数据：
             *      1:当前文件所存储的Storage的组  例如:group1
             *      2:文件存储的路径  例如: M00/02/44/2345678.jpg
             */
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResults;
    }


    /****
     * 获取文件的信息
     * @param groupName:组名
     * @param remoteFileName:文件存储的名字
     * @return FileInfo
     */
    public static FileInfo getFile(String groupName,String remoteFileName){
        try {
            //获取StorageClient
            StorageClient storageClient =getStorageClient();

            //通过StorageClient获取图片信息
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /****
     * 文件下载
     * @param groupName:组名
     * @param remoteFileName:文件存储的名字
     * @return InputStream
     */
    public static InputStream downloadFile(String groupName, String remoteFileName){
        try {
            //获取StorageClient
            StorageClient storageClient =getStorageClient();

            //通过StorageClient获取图片信息
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            return new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /****
     * 删除文件
     * @param groupName:组名
     * @param remoteFileName:文件存储的名字
     * @return InputStream
     */
    public static void deleteFile(String groupName, String remoteFileName){
        try {
            //获取TrackerServer
            TrackerServer trackerServer = getTrackerServer();

            //通过TrackerServer获取Storage信息，并且将信息存储到StorageClient中
            StorageClient storageClient = new StorageClient(trackerServer,null);
            //通过StorageClient删除图片
            storageClient.delete_file(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取Storage信息
     * @param groupName ：Storage的组名
     */
    public static StorageServer getStorage(String groupName){
        try {
            //创建一个TrackerClient
            TrackerClient trackerClient = new TrackerClient();

            //用TrackerClient获取TrackerServer的链接信息
            TrackerServer trackerServer = trackerClient.getConnection();

            //获取Storage信息
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer, groupName);
            return storeStorage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /***
     * 获取Storage的IP和端口信息
     * @param groupName ：Storage的组名
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName){
        try {
            //创建一个TrackerClient
            TrackerClient trackerClient = new TrackerClient();

            //用TrackerClient获取TrackerServer的链接信息
            TrackerServer trackerServer = trackerClient.getConnection();

            //获取Storage信息
            return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * TrackerServer的Http请求地址   http://192.168.211.132:8080
     */
    public static String getTrackerUrl(){
        try {
            //获取TrackerServer
            TrackerServer trackerServer = getTrackerServer();

            //获取获取TrackerServer的Http请求地址
            return "http://"+trackerServer.getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /****
     * TrackerServer的获取
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception{
        //创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();

        //通过TrackerClient对象链接TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /***
     * 获取StorageClient对象
     * @return
     * @throws Exception
     */
    public static StorageClient getStorageClient() throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //通过TrackerServer来获取Storage信息,将Storage信息封装到一个StorageClient中
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }

    public static void main(String[] args) throws Exception{
        //测试获取文件信息
        //FileInfo fileInfo = getFile("group1","M00/00/00/wKjThF07w0uANa_wAAnAAJuzIB4065.jpg");
        //System.out.println(fileInfo.getSourceIpAddr());
        //System.out.println(fileInfo.getFileSize());
        //System.out.println(fileInfo.getCrc32());

        //文件下载测试
        //InputStream is = downloadFile("group1", "M00/00/00/wKjThF07w0uANa_wAAnAAJuzIB4065.jpg");
        //文件输出流对象
        //FileOutputStream os = new FileOutputStream("D:/1.jpg");
        //创建缓冲区
        //byte[] buffer = new byte[1024];
        //while (is.read(buffer)!=-1){
        //    os.write(buffer);
        //}
        //os.flush();
        //os.close();
        //is.close();

        //删除文件测试
        deleteFile("group1", "M00/00/00/wKjThF0-QQyAGIVZAAFOK49JSFo57.jpeg");

        //获取Storage信息
        //StorageServer storageServer = getStorage("group1");
        //System.out.println(storageServer.getInetSocketAddress().getHostString());
        //System.out.println(storageServer.getStorePathIndex());
        //System.out.println(storageServer.getInetSocketAddress().getPort());

        //获取StorageIP和端口信息
        //ServerInfo[] serverInfos = getServerInfo("group1", "M00/00/00/wKjThF07wzGAYFi8AAXz2mMp9oM88.jpeg");
        //for (ServerInfo serverInfo : serverInfos) {
        //    System.out.println("IP:"+serverInfo.getIpAddr()+",端口号："+serverInfo.getPort());
        //}

        //获取TrackerServer请求地址
        //String trackerUrl = getTrackerUrl();
        //System.out.println(trackerUrl);
    }
}
