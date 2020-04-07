package com.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SocketService {
    //监听端口
    private static final int PORT = 6000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            //建立服务器的Socket，并设定一个监听的端口PORT
            serverSocket = new ServerSocket(PORT);
            //由于需要进行循环监听，因此获取消息的操作应放在一个while大循环中
            while(true){
                try {
                    //建立跟客户端的连接
                    socket = serverSocket.accept();
                } catch (Exception e) {
                    System.out.println("建立与客户端的连接出现异常");
                    e.printStackTrace();
                }
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("端口被占用");
            e.printStackTrace();
        }
        finally {
            System.out.println("服务端关闭");
            serverSocket.close();
        }
    }
}

//服务端线程类
//继承Thread类的话，必须重写run方法，在run方法中定义需要执行的任务。
class ServerThread extends Thread {
    private Socket socket ;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public  ServerThread(Socket socket){
        this.socket=socket;
    }
    public void run(){
        try {
            while (true){
                if (isServerClose(socket)){
                    return;
                }
                dataInputStream=new DataInputStream(socket.getInputStream());
                writeOK();
//                String s = dataInputStream.readUTF();
//                System.out.println(s);
                byte[] bytes = readInputStream(dataInputStream);
                /*byte[] bytes = new byte[1024];
                int len=0;
                while ((len=dataInputStream.read(bytes))!=-1){
                    for (int i = 0; i < len; i++) {
                        System.out.print(bytes[i]+",");
                    }
                }
                System.out.println();*/
                System.out.println(Arrays.toString(bytes));
                System.out.println("******************");
                byte b = bytes[26];
                System.out.println("命令字节：" + b);
                byte b1 = bytes[27];
                System.out.println("类型标志：" + b1);
//                if (b !=2 || b1 !=21){
//                    writeOK();
//                    return;
//                }
//                int i = byte2ToUnsignedShort(bytes, 24);
//                System.out.println(i);
                int n = bytes[28];
                System.out.println("信息对象数目：" + n);
                for (int i1 = 0; i1 < n; i1++) {
                    byte[] bytes1 = byteArraycopy(bytes, 29+(i1*7), 7);
                    int type = bytes1[0];
                    System.out.println("设备状态：" + type);
                    String s = ByteToDate(bytes1, 1);
                    System.out.println(s);
                    dataProcess(type,s);
                }
//                writeOK();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //操作结束，关闭socket
        try{
            socket.close();
        }catch(IOException e){
            System.out.println("关闭连接出现异常");
            e.printStackTrace();
        }
    }

    //读取数据
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024*2];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    //响应客户端
    private void writeOK() throws IOException {
        //向客户端发送消息
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                dataOutputStream.writeUTF("OK");
        dataOutputStream.write("OK".getBytes());
        socket.shutdownOutput();
    }

    //拷贝btye数组
    private static byte[] byteArraycopy(byte[] bytes,int srcPos,int length){
        byte[] bytes1 = new byte[length];
        System.arraycopy(bytes,srcPos,bytes1,0,length);
        return bytes1;
    }

    //将byte 中6个字节转换为时间
    private static String ByteToDate(byte[] bytes,int i) {
        String str=bytes[i]+"-"+bytes[i+1]+"-"+bytes[i+2]+" "+bytes[i+3]+":"+bytes[i+4]+":"+bytes[i+5];
        return DateUtils.setDate(str);
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     * @param socket
     * @return
     */
    public Boolean isServerClose(Socket socket){
        try{
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        }catch(Exception se){
            System.out.println("客户端主动断开连接了");
            return true;
        }
    }

    /**
     * 低字节在前，byte数组转换为无符号short整数,并按16进制转换为10进制
     *
     * @param bytes
     *            byte数组
     * @param off
     *            开始位置
     * @return short整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes, int off) {
        int i = (bytes[off + 1] << 8 & 0xFFFF) | (bytes[off] & 0xFF);
        String s = String.valueOf(i);
        return Integer.parseUnsignedInt(s,16);
    }

    private void dataProcess(int type,String time){
        // type：1正常，2火警，3故障，4主电故障，5备电故障，6通信信道故障，7连接线路故障
        if (type==1){
            return;
        }
        if (type==2){

        }
        if (type==4){

        }
        if (type==8){

        }
        if (type==16){

        }
        if (type==32){

        }
        if (type==64){

        }
    }
}