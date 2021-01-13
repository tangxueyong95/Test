package com.example.test.util;

/**
 * Created by wly on 2018/4/17.
 */
public class HexConvert {

    public static String  convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

    public static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();

        for( int i=0; i<hex.length()-1; i+=2 ){

            String s = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            sb.append((char)decimal);
        }

        return sb.toString();
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        int length = hexString.length() / 2;
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    //返回匹配字符
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //将字节数组转换为short类型，即统计字符串长度
    public static short bytes2Short2(byte[] b) {
        short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
        return i;
    }
    //将字节数组转换为16进制字符串
    public static String BinaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex;
        }
        return result;
    }

    /**
     * 将int数据转换为16进制字符串
     * @param n
     * @return
     */
    public static String byteToHex(int n) {
        String hexStr = "0123456789ABCDEF";
        char[] hexArray = hexStr.toCharArray();
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return String.valueOf(hexArray[d1]) + String.valueOf(hexArray[d2]);
    }

    /**
     * 将int数据转换为16进制字符串
     * @param n
     * @return
     */
    public static String intToHex(int n){
        String s = Integer.toHexString(n);
        int length = s.length();
        if (length%2==1){   //前补0
            s="0"+s;
        }
        return s.toUpperCase();
    }

    /**
     * 16进制字符串前补0
     * @param s
     * @return
     */
    public static String fill(String s){
        int length = s.length();
        if (length%2==1){   //前补0
            s="0"+s;
        }
        return s;
    }

    /**
     * byte数组转换为二进制字符串,并将字符串颠倒
     **/
    public static String byteArrToBinStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2));
        }
        return result.reverse().toString();
    }

    /**
     * 计算CRC16校验码
     *
     * @param bytes
     * @return
     */
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC).toUpperCase();
    }

    public static void main(String[] args) {
        String s="+TOPSAIL";
        String s1 = convertStringToHex(s).toUpperCase();
        System.out.println(s1);
    }
}