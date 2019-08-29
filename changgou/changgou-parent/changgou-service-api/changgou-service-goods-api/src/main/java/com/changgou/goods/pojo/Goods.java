package com.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/*****
 * @Author: www.itheima.com
 * @Date: 2019/7/29 10:50
 * @Description: com.changgou.goods.pojo
 *  主要是接收Sku集合和Spu数据
 ****/
public class Goods implements Serializable {
    //SPU
    private Spu spu;

    //List<Sku>
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
