package com.example.test.TCP.haikangyongchuan;

import com.example.test.TCP.InformationService;
import com.example.test.util.DateUtils;
import com.example.test.util.HexConvert;

import java.io.*;
import java.net.Socket;

//服务端线程类
//继承Thread类的话，必须重写run方法，在run方法中定义需要执行的任务。
public class ServerThread extends Thread {
    private Socket socket;
    private InformationService informationService;
    InputStream InputStream;
    OutputStream OutputStream;

    public ServerThread(Socket socket,InformationService informationService) {
        this.socket = socket;
        this.informationService = informationService;
    }

    public void run() {
        try {
            while (true) {
                if (isServerClose(socket)) {
                    return;
                }
                InputStream = socket.getInputStream();
                byte[] bytes = readInputStream(InputStream);
                String str = HexConvert.BinaryToHexString(bytes);
                System.out.println("接收报文："+str);
                System.out.println("******************");
                String s5 = Substring(str,2,2);
                System.out.println("流水号：" + s5);
                String s = Substring(str,6,6);
                String byteToDate = ByteToDate(hexStringToByteArray(s));
                System.out.println("时间: "+byteToDate);
                String s6 = Substring(str,12,6);
                System.out.println("源地址：" + s6);
                String s7= s5+"0101"+s+"000000000000"+s6+"000003";
                String checksum = checksum(s7);
                System.out.println("校验和："+ checksum);
                //向客户端发送消息
                OutputStream = socket.getOutputStream();
                String outstr="4040"+s7+checksum+"2323";
                System.out.println("应答报文：" + outstr);
                byte[] outbytes = HexConvert.hexStringToBytes(outstr);
                OutputStream.write(outbytes);
                socket.shutdownOutput();

                int int1 = toInt(Substring(str,24,2));
                System.out.println("应用数据单元长度："+ int1);
                int int2 = toInt(Substring(str,26,1));
                System.out.println("命令字节：" + int2);
                String substring = Substring(str, 27, int1);
                if (int2!=2){
                    return;
                }
                if (int1!=0){
                    messageProcessing(s6,substring);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //操作结束，关闭socket
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("关闭连接出现异常");
            e.printStackTrace();
        }
    }

    public static String Substring(String s,int i,int n){
       return s.substring(2 * i, 2 * (i+n));
    }
    /**
     * 读取数据（解决数据不完整问题）
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024*2];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = inputStream.read(buffer);
        bos.write(buffer, 0, len);
        return bos.toByteArray();
    }
    /**
     * 将int数组 中6位转换为时间
     *
     * @param ints 源数组
     * @return
     */
    public static String ByteToDate(int[] ints) {
        String str = ints[5] + "-" + ints[4] + "-" + ints[3] + " " + ints[2] + ":" + ints[1] + ":" + ints[0];
        return DateUtils.setDate(str);
    }

    /**
     * 将16进制String 转换行10进制int
     * @return
     */
    public static int toInt(String hexString){
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int m=0;
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            int i1 = Character.digit(hexString.charAt(i), 16) << 4*(i+1);
            int i2 = Character.digit(hexString.charAt(i + 1), 16)<<4*i;
            m+=i1+i2;
        }
        return m;
    }

    /**
     * 将16进制String 转换行10进制long
     * @param hexString
     * @return
     */
    public static Long toLong(String hexString){
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        Long m=0L;
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            Long i1 = Integer.toUnsignedLong(Character.digit(hexString.charAt(i), 16) << 4*(i+1));
            Long i2 = Integer.toUnsignedLong(Character.digit(hexString.charAt(i + 1), 16)<<4*i);
            m+=i1+i2;
        }
        return m;
    }

    /**
     * 判断是否断开连接，断开返回true,没有返回false
     *
     * @param socket
     * @return
     */
    public Boolean isServerClose(Socket socket) {
        try {
            socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            System.out.println("客户端主动断开连接了");
            return true;
        }
    }

    public static String checksum(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int m=0;
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            int i1 = Character.digit(hexString.charAt(i), 16) << 4;
            int i2 = Character.digit(hexString.charAt(i + 1), 16);
            m += i1 +i2;
        }
        int i = m & 0xff;
        String s = HexConvert.byteToHex(i);
        return s;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static int[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int[] bytes = new int[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            int i1 = Character.digit(hexString.charAt(i), 16) << 4;
            int i2 = Character.digit(hexString.charAt(i + 1), 16);
            bytes[i / 2] = i1 +i2;
        }
        return bytes;
    }

    /**
     * 将设备号整数转换位15位字符串（前补0）
     * @param i
     * @return
     */
    public static String deviceNumberToString(Long i){
        String n=i+"";
        int length = n.length();
        StringBuffer hex = new StringBuffer();
        if (length !=15){
            for (int i1 = 0; i1 < 15 - length; i1++) {
                hex.append(0);
            }
        }
        return hex+n;
    }
    /**
     * 数据处理
     * @param deviceNumber 用传设备机号16进制
     * @param str 信息体
     */
    private void messageProcessing(String deviceNumber,String str) {
        int typeCode = toInt(Substring(str,0,1));
        System.out.println("类型标志：" + typeCode);
        int n = toInt(Substring(str,1,1));
        System.out.println("信息对象数目：" + n);
        System.out.println(deviceNumber);
        long l = toLong(deviceNumber);
        String imei = deviceNumberToString(l);
        System.out.println(imei);
        if (typeCode==21){
            for (int i = 0; i < n; i++) {
                String substring = Substring(str, i * 7 + 2, 7);
                String substring1 = Substring(substring, 0, 1);
                System.out.println(substring1);
                int type = toInt(substring1);
                System.out.println("用传状态：" + type);
                String time = ByteToDate(hexStringToByteArray(Substring(substring, 1, 6)));
                System.out.println("上传时间："+ time);
                informationService.dataProcess(imei,type,time);
            }
        }
        if (typeCode==2){
            for (int i = 0; i < n; i++) {
                String substring = Substring(str, i * 46 + 2, 46);
                String substring1 = Substring(substring, 7, 2);
                System.out.println(substring1);
                int type = toInt(substring1);
                System.out.println("部件状态：" + type);
                String time = ByteToDate(hexStringToByteArray(Substring(substring, 40, 6)));
                System.out.println("上传时间："+ time);
                if (type!=3 && type!=5){
                    return;
                }
                informationService.dataProcess(imei,type,time);
            }
        }
        if (typeCode==1){
            for (int i = 0; i < n; i++) {
                String substring = Substring(str, i * 10 + 2, 10);
                String substring1 = Substring(substring, 2, 2);
                System.out.println(substring1);
                int type = toInt(substring1);
                System.out.println("系统状态：" + type);
                String time = ByteToDate(hexStringToByteArray(Substring(substring, 4, 6)));
                System.out.println("上传时间："+ time);
                if (type!=3 && type!=5 && type!=1025 && type!=513 && type!=257){
                    return;
                }
                informationService.dataProcess(imei,type,time);
            }
        }
    }
}
