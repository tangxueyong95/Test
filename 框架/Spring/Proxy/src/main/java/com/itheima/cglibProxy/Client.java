package com.itheima.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

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
//        Producer2 producer = new Producer2();
//        // 直接调用目标对象的方法
//        int i = producer.saleProduct(7000);
//        System.out.println(i);
//    }

    // 使用代理技术
    public static void main(String[] args) {
        // 目标对象（实现类）
        final Producer2 producer = new Producer2();

        // 面向类的，使用CGLIB代理
        /**
         *  Enhancer.create(Producer2.class：目标对象的字节码
         *  new MethodInterceptor()：回调函数
         */
        Producer2 proxyProducer = (Producer2)Enhancer.create(Producer2.class, new MethodInterceptor() {
            /**
             * @param proxy：代理对象
             * @param method：目标对象的方法（获取方法名，获取类名的路径，方法的参数）
             * @param args：传递到目标方法的参数
             * @params MethodProxy methodProxy:代理对象的方法（不常用）
             * @return Object：表示目标对象方法的返回值
             * @throws Throwable
             */
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                // 访问目标对象
                /**
                 *  Object invoke(Object obj, Object... args)
                 *  Object obj：目标对象
                 *  Object... args：表示传递到目标对象的参数
                 */
                // 【需求】，销售的时候7000，给代理商20%的钱，剩余的80%给厂家
                Object returnValue = null;
                if (method.getName() != null && method.getName().equals("saleProduct")) {
                    Float money = (Float) args[0] * 0.8f;
                    returnValue = method.invoke(producer, money);
                }else {
                    Float money = (Float) args[0];
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
