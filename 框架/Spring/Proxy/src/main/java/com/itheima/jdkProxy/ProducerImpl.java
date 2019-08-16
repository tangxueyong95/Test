package com.itheima.jdkProxy;

/**
 * @ClassName ProducerImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/12 9:19
 * @Version V1.0
 */
public class ProducerImpl implements Producer {

    public int saleProduct(float money) {
        System.out.println("销售：销售的金额是："+money);
        return 5; // 5表示销售合理
    }

    public void afterService(float money) {
        System.out.println("售后：售后服务的金额是："+money);
    }
}
