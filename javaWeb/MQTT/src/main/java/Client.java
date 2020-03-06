import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ScheduledExecutorService;

public class Client {

    public static final String HOST = "ssl://city-test10-api.sensoro.com:8883";
    public static final String TOPIC1 = "malfunction/dYpqqGzVBxf6/590c235044aa4369905d455b";
    public static final String TOPIC2 = "alarm/dYpqqGzVBxf6/590c235044aa4369905d455b";
    public static final String TOPIC3 = "deploy/dYpqqGzVBxf6/590c235044aa4369905d455b";
    public static final String TOPIC4 = "remove/dYpqqGzVBxf6/590c235044aa4369905d455b";
    public static final String TOPIC5 = "report/dYpqqGzVBxf6/590c235044aa4369905d455b";
    private static final String clientid = "dYpqqGzVBxf6_590c235044aa4369905d455b_xiaofang";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "590c235044aa4369905d455b";
    private String passWord = "MEFbQe3PfkcHXnx7kIfn8d8p";

    private ScheduledExecutorService scheduler;

    private void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置回调
            client.setCallback(new MyPushCallback());
            MqttTopic topic1 = client.getTopic(TOPIC1);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic1, "close".getBytes(), 2, true);
            MqttTopic topic2 = client.getTopic(TOPIC2);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic2, "close".getBytes(), 2, true);
            MqttTopic topic3 = client.getTopic(TOPIC3);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic3, "close".getBytes(), 2, true);
            MqttTopic topic4 = client.getTopic(TOPIC4);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic4, "close".getBytes(), 2, true);
            MqttTopic topic5 = client.getTopic(TOPIC5);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic5, "close".getBytes(), 2, true);

            client.connect(options);
            //订阅消息
            int[] Qos  = {1,1,1,1,1};
            String[] topic = {TOPIC1,TOPIC2,TOPIC3,TOPIC4,TOPIC5};
            client.subscribe(topic, Qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MqttException {
        Client client = new Client();
        client.start();
    }
}