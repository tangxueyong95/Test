package com.example.test.TCP.tuoPu;

import com.example.test.TCP.InformationService;
import com.example.test.util.DateUtils;
import com.example.test.util.HexConvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//服务端线程类
//继承Thread类的话，必须重写run方法，在run方法中定义需要执行的任务。
public class ServerThread extends Thread {
    private Socket socket;
    private InformationService informationService;
    InputStream InputStream;
    OutputStream OutputStream;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

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
                //向客户端发送消息
                OutputStream = socket.getOutputStream();
                String out1="+TOPSAIL";
                String out2 = HexConvert.convertStringToHex(out1).toUpperCase();
                String[] byteDate = DateUtils.get6ByteDate();
                String outstr = jointString(out2, byteDate);
                System.out.println("应答报文：" + outstr);
                byte[] outbytes = HexConvert.hexStringToBytes(outstr);
                OutputStream.write(outbytes);
                socket.shutdownOutput();
                System.out.println("******************");

                String substring = Substring(str, 4, 16);
                String imei = HexConvert.convertHexToString(substring).substring(0,15);
                System.out.println("imei号：" + imei);
                String MsgType = Substring(str, 36, 1);
                System.out.println("消息类型：" + MsgType);
                if ("01".equals(MsgType)){
                    System.out.println("注册消息");
                    informationService.loginStatus(imei);
                    return;
                }
                if(!"09".equals(MsgType)){
                    System.out.println("未知的消息类型");
                    return;
                }
                String substring2 = Substring(str, 38, 2);
                int MsgLen = toInt(substring2);
//                System.out.println("消息体长度：" + MsgLen);
                String MsgBody = Substring(str, 40, MsgLen);
                String time = DateUtils.set6ByteDate(Substring(MsgBody, 0, 6));
                System.out.println("上传时间：" + time);
                int interval = toInt(Substring(MsgBody, 6, 1));
                int intervalType = toInt(Substring(MsgBody, 9, 1));
                if (intervalType==0){
                    System.out.println("采集间隔时间：" + interval + " 秒");
                }
                if (intervalType==1){
                    System.out.println("采集间隔时间：" + interval + " 分");
                }
                int electricity = Integer.valueOf(Substring(MsgBody, 7, 1));
                System.out.println("电池电量：" + electricity + "%");
                int signalStrength = toInt(Substring(MsgBody, 8, 1));
                System.out.println("信号强度：" + signalStrength );
                int n = (MsgLen - 10) / 4;
                List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
                while(n>0) {
                    System.out.println("******************");
                    String str1 = Substring(MsgBody, 10, 1);
                    String deviceStatus = str1.substring(0, 1);
                    System.out.println("设备状态：" + deviceStatus);
                    int valueType = Integer.valueOf(str1.substring(1));
                    System.out.println("模拟量值类型：" + valueType);
                    String str2 = Substring(MsgBody, 11, 1);
                    int digits = Integer.valueOf(str2.substring(0, 1));
                    System.out.println("小数位数：" + digits);
                    String s = "";
                    if (valueType == 4) {
                        if ("1".equals(str2.substring(1))) {
                            s = "-";
                        }
                    } else {
                        s = str2.substring(1);
                    }
                    double value = Integer.valueOf(s + Substring(MsgBody, 12, 2)) / Math.pow(10, digits);
                    //压力单位转换
                    if (valueType == 2){
                        value=value/10;
                    }
                    if (valueType == 3){
                        value=value/1000;
                    }
                    System.out.println("模拟量值：" + value);
                    Map<String, Object> map = new HashMap<>();
                    map.put("deviceStatus",deviceStatus);
                    map.put("valueType",valueType);
                    map.put("value",value);
                    mapList.add(map);
                    n--;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("mapList",mapList);
                map.put("imei",imei);
                map.put("time",time);
                map.put("electricity",electricity);
                map.put("signalStrength",signalStrength);
                informationService.dataProcess(map);
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
        byte[] buffer = new byte[1024*4];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = inputStream.read(buffer);
        bos.write(buffer, 0, len);
        return bos.toByteArray();
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
            Long i1 = Integer.toUnsignedLong(Character.digit(hexString.charAt(i), 16) << 4 * (len - i - 1));
            Long i2 = Integer.toUnsignedLong(Character.digit(hexString.charAt(i + 1), 16) << 4 * (len - i - 2));
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

    public static String jointString(String str,String s[]){
        StringBuffer hex = new StringBuffer(str);
        for (String s1 : s) {
            hex.append(s1);
        }
        return hex.toString();
    }

    public static void main(String[] args) {
        int i = toInt("33");
        System.out.println(i);
    }
}
