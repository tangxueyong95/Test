package com.example.test.TCP.zhiAn;

import com.example.test.TCP.InformationService;
import com.example.test.util.DateUtils;
import com.example.test.util.HexConvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//服务端线程类
//继承Thread类的话，必须重写run方法，在run方法中定义需要执行的任务。
public class ServerThread extends Thread {
    private Socket socket;
    private InformationService informationService;
    InputStream InputStream;
    OutputStream OutputStream;

    public ServerThread(Socket socket, InformationService informationService) {
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
                String s = new String(bytes);
                System.out.println("接收报文："+s);
                String substring = s.substring(0, 4);
                System.out.println("标志位：" + substring);
                String str = s.substring(7, s.length() - 4);
                System.out.println(str);
                Map<String, String> map = StringToMap(str);
                String sId = map.get("ID");
                String S1 = map.get("S1");
                Long aLong = toLong(HexConvert.fill(S1));
                Long time = new Date().getTime()/1000-aLong;
                String difference = getDifference(time);
                //向客户端发送消息
                OutputStream = socket.getOutputStream();
                String sout = "|ID:" + sId + ";I:E;E:1;T3:5;T4:3C;S1:50;S2:14;S3:8C,1F0,9B2,640,3DE;T9:" + S1 + ","+difference+";";
                int i = sout.length() + 6;
                String n = HexConvert.intToHex(i);
                System.out.println("数据长度16进制：" + n);
                String outs = "0000" + n + sout;
                String crc = HexConvert.fill(HexConvert.getCRC(outs.getBytes()));
                System.out.println("校验码: " + crc);
                String outstr = outs + crc;
                System.out.println("应答报文：" + outstr);
                byte[] outbytes = outstr.getBytes();
                OutputStream.write(outbytes);
                OutputStream.flush();

                byte[] bytes1 = readInputStream(InputStream);
                String s1 = new String(bytes1);
                System.out.println("接收报文："+s1);
                String substring1 = s1.substring(0, 4);
                System.out.println("标志位：" + substring1);
                String str1 = s1.substring(7, s1.length() - 4);
                System.out.println(str1);
                Map<String, String> map1 = StringToMap(str1);
                String sN = map1.get("N"); //下位机数据采集次数
                String sT = map1.get("T"); //时间
                Long aLong1 = toLong(HexConvert.fill(sT));
                String date1 = DateUtils.getDate(aLong1);
                System.out.println("时间：" + date1);
                //向客户端发送消息
                OutputStream = socket.getOutputStream();
                String sout1 = "|N:" + sN + ";";
                int i1 = sout1.length() + 6;
                String n1 = HexConvert.intToHex(i1);
                System.out.println("数据长度16进制：" + n1);
                String outs1 = "0001" + n1 + sout1;
                String crc1 = HexConvert.fill(HexConvert.getCRC(outs1.getBytes()));
                System.out.println("校验码: " + crc1);
                String outstr1 = outs1 + crc1;
                System.out.println("应答报文：" + outstr1);
                byte[] outbytes1 = outstr1.getBytes();
                OutputStream.write(outbytes1);
                socket.shutdownOutput();

                String sD = map1.get("D"); //告警信息
                String sA0 = map1.get("A0");//信号强度(ASU)
                String sA1 = map1.get("A1");//电池电压(V)
                String sA2 = map1.get("A2");//温度(°C)
                String sA3 = map1.get("A3");//水压、水位值(MPa、m)

                byte[] bytes2 = HexConvert.hexStringToBytes(HexConvert.fill(sD));
                String binStr = HexConvert.byteArrToBinStr(bytes2);
                System.out.println("告警：" + binStr);
                int A0 = toLong(HexConvert.fill(sA0)).intValue();
                System.out.println("信号强度：" + A0 + " ASU");
                double A1 = toLong(HexConvert.fill(sA1)).intValue() * 1.0 / 100;
                System.out.println("电池电压：" + A1 + " V");
                String string = toLong(HexConvert.fill(sA2)).toString();
                String substring2 = string.substring(0, 1);
                double t = Integer.valueOf(string.substring(1)) * 1.0 / 10;
                String A2 = "" + t;
                if ("0".equals(substring2)) {
                    A2 = "-" + t;
                }
                System.out.println("温度：" + A2 + " °C");
                int A3 = toLong(HexConvert.fill(sA3)).intValue();
                System.out.println("水压、水位值：" + A3 + " MPa(m)");

                Map<String,String> map2 = new HashMap<>();
                map2.put("imei",sId);
                map2.put("time",date1);
                map2.put("binStr",binStr); //二进制数据 告警
                map2.put("A0",String.valueOf(A0));  //信号强度
                map2.put("A1",String.valueOf(A1)); //电池电压
                map2.put("A2",A2);  //温度
                map2.put("A3",String.valueOf(A3)); //水压、水位值
//                informationService.dataProcess(map2);
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

    /**
     * 读取数据（解决数据不完整问题）
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024 * 2];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = inputStream.read(buffer);
        bos.write(buffer, 0, len);
        return bos.toByteArray();
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

    /**
     * 将16进制String 转换行10进制long,高字节在前
     *
     * @param hexString
     * @return
     */
    public static Long toLong(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        Long m = 0L;
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            Long i1 = Integer.toUnsignedLong(Character.digit(hexString.charAt(i), 16) << 4 * (len - i - 1));
            Long i2 = Integer.toUnsignedLong(Character.digit(hexString.charAt(i + 1), 16) << 4 * (len - i - 2));
            m += i1 + i2;
        }
        return m;
    }

    /**
     * 将报文key:value;转换为map格式
     * @param s
     * @return
     */
    public static Map<String, String> StringToMap(String s) {
        Map<String, String> map = new HashMap<>();
        String[] split = s.split(";");
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split(":");
            map.put(split1[0], split1[1]);
        }
        return map;
    }

    /**
     * 将时间差转换为16进制
     * @param time
     * @return
     */
    public static String getDifference(Long time){
        String difference=null;
        if (time>=0){
            difference=Long.toHexString(time);
        }
        if (time<0){
            difference ="-" + Long.toHexString(Math.abs(time));
        }
        return difference.toUpperCase();
    }

    public static void main(String[] args) {
        Long aLong = toLong("0012");
        System.out.println(aLong);
    }
}
