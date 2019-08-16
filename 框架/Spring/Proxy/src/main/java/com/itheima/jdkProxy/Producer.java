package com.itheima.jdkProxy;

public interface Producer {
    /**
     * 销售
     * @param money
     */
    public int saleProduct(float money);

    /**
     * 售后
     * @param money
     */
    public void afterService(float money);
}
