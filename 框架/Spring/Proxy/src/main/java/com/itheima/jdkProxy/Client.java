package com.itheima.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName Client
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/12 9:20
 * @Version V1.0
 */
public class Client {
    // 不使用代理技术
//    public static void main(String[] args) {
//        Producer producer = new ProducerImpl();
//        // 直接调用目标对象的方法
//        int i = producer.saleProduct(7000);
//        System.out.println(i);
//    }

    // 使用代理技术
    public static void main(String[] args) {
        // 目标对象
        final Producer producer = new ProducerImpl();

        // 面向接口的，使用JDK代理
        /**
         *Proxy.newProxyInstance(
         *      ClassLoader loader：类加载器，表示代理对象和目标对象使用同一个类加载器
                 Class<?>[] interfaces：表示代理对象和目标对象要使用同一个接口
                 InvocationHandler h：表示回调函数，在访问目标对象之前，先要执行的方法，通过该方法完成对目标对象方法的增强
                )
         */
        Producer proxyProducer = (Producer)Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * @param proxy：代理对象
                     * @param method：目标对象的方法（获取方法名，获取类名的路径，方法的参数）
                     * @param args：传递到目标方法的参数
                     * @return Object：表示目标对象方法的返回值
                     * @throws Throwable
                     */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        // 访问目标对象
                        /**
                         *  Object invoke(Object obj, Object... args)
                         *  Object obj：目标对象
                         *  Object... args：表示传递到目标对象的参数
                         */
                        // 【需求】，销售的时候7000，给代理商20%的钱，剩余的80%给厂家
                        Object returnValue = null;
                        if(method.getName()!=null && method.getName().equals("saleProduct")){
                            Float money = (Float)args[0]*0.8f;
                            returnValue = method.invoke(producer, money);
                        }else {
                            Float money = (Float)args[0];
                            returnValue = method.invoke(producer,money);
                        }
                        return returnValue;
                    }
                });

        // 通过代理对象调用目标对象的方法
        int i = proxyProducer.saleProduct(7000);
        proxyProducer.afterService(7000);
        System.out.println(i);
    }
}
