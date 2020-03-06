import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;

import java.net.URISyntaxException;

public class GETClient {

    public static void main(String[] args) throws URISyntaxException {
        String uri ="localhost:5683/hello";  //创建一个资源请求hello资源，注意默认端口为5683
        CoapClient client = new CoapClient(uri);
        CoapResponse response = null;
        try {
            response = client.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (response != null) {
            System.out.println(response.getCode());  //打印请求状态码
            System.out.println(response.getOptions());  //选项参数
            System.out.println(response.getResponseText());  //获取内容文本信息
            System.out.println("\nAdvanced\n");    //
            System.out.println(Utils.prettyPrint(response));  //打印格式良好的输出
        }
        String uri1="localhost:5683POST/time";
        CoapClient client1 = new CoapClient(uri1);
        CoapResponse response1 = null;
        try {
//            client1.useNONs();
            response1 = client1.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (response1 != null) {
            System.out.println(response1.getCode());  //打印请求状态码
            System.out.println(response1.getOptions());  //选项参数
            System.out.println(response1.getResponseText());  //获取内容文本信息
            System.out.println("\nAdvanced\n");    //
            System.out.println(Utils.prettyPrint(response1));  //打印格式良好的输出
        }
    }
}