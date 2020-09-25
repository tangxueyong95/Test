package com.java;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

//服务端线程类
//继承Thread类的话，必须重写run方法，在run方法中定义需要执行的任务。
public class ServerThread extends Thread {
    private Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            while (true) {
                if (isServerClose(socket)) {
                    return;
                }
                writeOK();
                dataInputStream = new DataInputStream(socket.getInputStream());
                byte[] bytes = readInputStream(dataInputStream);
                System.out.println(Arrays.toString(bytes));
                String str = new String(bytes);
                System.out.println(str);
                System.out.println(str.length());
                System.out.println();
                System.out.println("******************");
                String s = str.substring(2 * 6, 2 * (6+6));
                String byteToDate = ByteToDate(hexStringToByteArray(s),0);
                System.out.println(byteToDate);
                String s1 = str.substring(2 * 24, 2 * (24+1));
                int[] ints1 = hexStringToByteArray(s1);
                System.out.println("应用数据单元长度："+ ints1[0]);
                String s2 = str.substring(2 * 26, 2 * (26+1));
                System.out.println("命令字节：" + s2);
                String s3 = str.substring(2 * 27, 2 * (27+1));
                if ("00".equals(s1)){
                    return;
                }
                System.out.println("类型标志：" + s3);
                String s4 = str.substring(2 * 28,2 * 28 + 2);
                System.out.println("信息对象数目：" + s4);
//                for (int i1 = 0; i1 < n; i1++) {
//                    byte[] bytes1 = new byte[0];
//                    try {
//                        bytes1 = byteArraycopy(bytes, 29+(i1*7), 7);
//                    } catch (Exception e) {
//                        System.out.println("信息对象数目大于实际数目");
//                        e.printStackTrace();
//                    }
//                    int type = byteToInt(bytes1[0]);
//                    System.out.println("设备状态：" + type);
//                    String s = ByteToDate(bytes1, 1);
//                    System.out.println("上报时间：" + s);
//                    dataProcess(type,s);
//                }
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
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    //响应客户端
    private void writeOK() throws IOException {
        //向客户端发送消息
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        String str="4040000001012d1a101f0314000000000000f1fb09000000000003872323";
        dataOutputStream.write(str.getBytes());
        socket.shutdownOutput();
    }

    /**
     * 拷贝byte数组
     *
     * @param bytes  源数组
     * @param srcPos 起始位置
     * @param length 拷贝长度
     * @return
     */
    private static byte[] byteArraycopy(byte[] bytes, int srcPos, int length) {
        byte[] bytes1 = new byte[length];
        System.arraycopy(bytes, srcPos, bytes1, 0, length);
        return bytes1;
    }

    /**
     * 将int数组 中6位转换为时间,低字节在前
     *
     * @param ints 源数组
     * @param i     时间的起始位置
     * @return
     */
    public static String ByteToDate(int[] ints, int i) {
        String str = ints[i+5] + "-" + ints[i + 4] + "-" + ints[i + 3] + " " + ints[i + 2] + ":" + ints[i + 1] + ":" + ints[i];
        return DateUtils.setDate(str);
    }

    /**
     * 将int 中6位转换为ip,低字节在前
     *
     * @param ints 源数组
     * @return
     */
    public static String ByteToIp(int[] ints) {
        String str = ints[5] + "." + ints[4] + "." + ints[3] + "." + ints[2] + ":" + ints[1] + ":" + ints[0];
        return str;
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
     * 低字节在前，byte数组转换为无符号short整数,并按16进制转换为10进制
     *
     * @param bytes byte数组
     * @param off   开始位置
     * @return int整数
     */
    public static int byte2ToUnsignedShort(byte[] bytes, int off) {
        int i = (bytes[off + 1] << 8 & 0xFFFF) | (bytes[off] & 0xFF);
        String s = String.valueOf(i);
        return Integer.parseUnsignedInt(s, 16);
    }

    /**
     * byte转换为无符号short整数,并按16进制转换为10进制
     */
    public static int byteToInt(byte bytes) {
        int i = bytes & 0xFF;
        String s = String.valueOf(i);
        return Integer.parseUnsignedInt(s, 16);
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s1 = Integer.toHexString(ch);
            str+=s1;
        }
        return str;
    }

    public static String intTo16(int i) {
        String str = "";
        String s = Integer.toHexString(i);
        if (s.length() % 2 ==1){
            str+="0";
        }
        str+=s;
        return str;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
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
     * 将16进制字符串转换为int[]
     * @param s
     * @return
     */
    public static int[] StringToInt(String s){

        return null;
    }
    /**
     * 信息入库
     *
     * @param type 类型
     * @param time 时间
     */
    private void dataProcess(int type, String time) {
        // type：1正常，2火警，3故障，4主电故障，5备电故障，6通信信道故障，7连接线路故障
        if (type == 1) {
            return;
        }
        if (type == 2) {

        }
        if (type == 4) {

        }
        if (type == 8) {

        }
        if (type == 16) {

        }
        if (type == 32) {

        }
        if (type == 64) {

        }
    }
}
