package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.order.service.impl
 ****/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    /***
     * 购物车集合
     * @param username
     * @return
     */
    @Override
    public List<OrderItem> list(String username) {
        //values返回当前用户命名空间下的所有商品结合
        return redisTemplate.boundHashOps("Cart_"+username).values();
    }

    /****
     * 添加购物车
     * @param id :商品ID
     * @param num :购买数量
     * @param username :购买的用户的用户名
     */
    @Override
    public void add(Long id, Integer num, String username) {
        //如果购买商品个数为0，则删除该购物车中该商品信息
        if(num<=0){
            //移除购物车中该商品详情
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            return;
        }

        //Sku  数据  根据SkuId查询Sku信息
        Result<Sku> skuResult = skuFeign.findById(id);

        //Spu  3个分类ID 根据SpuID查询Spu信息
        Result<Spu> spuResult = spuFeign.findById(skuResult.getData().getSpuId());

        //创建购物车明细
        OrderItem orderItem = createOrderItem(id,num,skuResult.getData(),spuResult.getData());

        /***
         * RedisTempalte
         * Redis存储购物车数据
         * Hash
         * key:用户名
         *     购物车明细
         */
        redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);
    }


    /****
     *创建购物车明细
     * @param id :sku的id
     */
    public OrderItem createOrderItem(Long id, Integer num,Sku sku,Spu spu){
        OrderItem orderItem = new OrderItem();
        orderItem.setNum(num); //购买数量
        orderItem.setSkuId(id); //skuid

        //Sku
        orderItem.setSpuId(sku.getSpuId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setMoney(num*sku.getPrice()*100); //单价(单位：分)  price*num
        orderItem.setImage(spu.getImage()); //

        //Spu
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        return orderItem;
    }
}
