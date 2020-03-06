package com.cglibProxy;

public class Producer2 {

    public int saleProduct(float money) {
        System.out.println("销售：销售的金额是："+money);
        return 5; // 5表示销售合理
    }

    public void afterService(float money) {
        System.out.println("售后：售后服务的金额是："+money);
    }
}
