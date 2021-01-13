package com.example.test.util;

import com.example.test.entity.Msg;

public class MasUtils {

    private final static String zero = "成功";
    private final static String one = "失败";
    private final static String two = "接收方号码为空";
    private final static String three = "接收方号码错误";
    private final static String four = "短信内容为空";
    private final static String five = "鉴权ID 为空";
    private final static String six = "鉴权失败";

    private static String url="http://223.82.209.38:9990/sjb/HttpSendSMSService";
    private static String id="37";   //MAS分配编号
    private static String pwd="wKh67BXOAfrPD1DpjSUUUuJo1SxHnybeFuMAiRDSafw8Zbeya8bexQ==";  //MAS分配密钥
    private static String serviceid;    //短信代码扩展码

    /**
     * 通过向MAS服务器提交POST请求来发送短信
     *
     * @param url     接口地址
     * @param phone   接收方号码(多个号码用英文逗号隔开)
     * @param message 短信内容
     */
    public static Msg sendMas(String url, String id, String pwd,String serviceid, String phone, String message) {
        String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<svc_init ver=\"2.0.0\">" +
                "<sms ver=\"2.0.0\">" +
                "<client>" +
                "<id>" + id + "</id>" +
                "<pwd>" + pwd + "</pwd>" +
                "<serviceid>" + serviceid + "</serviceid>" +
                "</client>" +
                "<sms_info>" +
                "<phone>" + phone + "</phone>" +
                "<content>" + message + "</content>" +
                "</sms_info>" +
                "</sms>" +
                "</svc_init>";
        String s = HttpClientUtils.sendPost(url, param);
        System.out.println(s);
        return new Msg("0",s);
//        Document document = null;
//        try {
//            document = DocumentHelper.parseText(s);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Element rootElement = document.getRootElement();
//        Element element = rootElement.element("response_info");
//        String gwid = element.element("gwid").getStringValue();
//        System.out.println(gwid);
//        String retcode = element.element("retcode").getStringValue().trim();
//        String retmesg = element.element("retmesg").getStringValue().trim();
//        System.out.println(retmesg);
//        String msg =null;
//        if ("00".equals(retcode)) {
//            msg= zero;
//        }
//        if ("01".equals(retcode)) {
//            msg= one;
//        }
//        if ("02".equals(retcode)) {
//            msg= two;
//        }
//        if ("03".equals(retcode)) {
//            msg= three;
//        }
//        if ("04".equals(retcode)) {
//            msg= four;
//        }
//        if ("05".equals(retcode)) {
//            msg= five;
//        }
//        if ("06".equals(retcode)) {
//            msg= six;
//        }
//        return new Msg(retcode,msg);
    }

    /*public static void main(String[] args) {
        String url="http://223.82.209.38:9990/sjb/HttpSendSMSService";
        String phone="18679057963";
        String message="123";
        Msg msg = MasUtils.sendMas(url, phone, message);
        System.out.println(msg);
    }*/
}
