package com.example.test.TCP.zhiAn;

import java.io.*;
import java.net.Socket;


/**
 * 注意用到的输入输出流DataInputStream和DataOutputStream，成对出现，最好用字节流
 */
// 客户端类
public class SocketClient {//创建公共类
    private String host = "localhost";// 默认连接到本机 localhost 223.84.188.55
    private int port = 8083;// 默认连接到端口 8083

    public SocketClient() {

    }

    // 连接到指定的主机和端口
    public SocketClient(String host, int port) {//构造方法
        this.host = host;//将构造方法的参数host传递给类变量host
        this.port = port;//将构造方法的参数port传递给类变量port
    }

    public void chat() {//chat方法
        try {
            // 连接到服务器
            Socket socket = new Socket(host, port);//创建Socket类对象

            System.out.println(socket);

            try {

                DataInputStream inputStream = new DataInputStream(socket
                        .getInputStream());// 读取服务器端传过来信息的DataInputStream

                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());// 向服务器端发送信息的DataOutputStream
                String outs="00003C|ID:460000000000007;S1:5C004B7A;G0:1946944;G1:65C0AED;00F3";
                byte[] bytes = outs.getBytes();
                out.write(bytes);
                out.flush();
                byte[] bytes1 = readInputStream(inputStream);
                String str = new String(bytes1);
                System.out.println(str);

                String outs1="00013A|N:49;T:5C49CF93;D:4;A0:12;A1:2D4;A2:473;A3:325;I:4;B09D";
                byte[] bytes2 = outs1.getBytes();
                out.write(bytes2);
                out.flush();
                socket.shutdownOutput();
                byte[] bytes3 = readInputStream(inputStream);
                String str1 = new String(bytes3);
                System.out.println(str1);
            } finally {
                socket.close();//关闭Socket监听
            }
        } catch (IOException e) {//捕获异常
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

    public static void main(String[] args) {//主程序方法
        new SocketClient().chat();//调用chat方法
//        String s = HexConvert.intToHex(18);
//        System.out.println(s);
//        Long aLong = ServerThread.toLong("5C49CF93");
//        System.out.println(aLong);
//        int length = "00010a|N:49;".length();
//        System.out.println(length);
//        byte[] bytes = HexConvert.hexStringToBytes("00");
//        String s = HexConvert.byteArrToBinStr(bytes);
//        System.out.println(s);
//        String s = "00013A|N:49;T:5C49CF93;D:4;A0:12;A1:2D4;A2:473;A3:325;I:4;B09D";
//        String substring = s.substring(7, s.length()-4);
//        Map<String, String> map = ServerThread.StringToMap(substring);
//        System.out.println(map);
    }
}