package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.order.dao.OrderItemMapper;
import com.changgou.order.dao.OrderLogMapper;
import com.changgou.order.dao.OrderMapper;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.pojo.OrderLog;
import com.changgou.order.service.OrderService;
import com.changgou.user.feign.UserFeign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:Order业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 增加Order
     * 金额校验:后台校验
     * @param order
     */
    @Override
    public void add(Order order){
        /***
         * 1.查询所有购物车列表
         * 2.从购物车中移除选中的商品(勾选的ID集合传入进来)
         */
        List<OrderItem> orderItems = redisTemplate.boundHashOps("Cart_" + order.getUsername()).values();

        //2.计算合计总数量、计算总金额
        int totalMoney = 0;
        int totalNum=0;
        //循环统计数据
        for (OrderItem orderItem : orderItems) {
            //金额叠加
            totalMoney+=orderItem.getMoney();
            //数量叠加
            totalNum+=orderItem.getNum();
        }
        order.setTotalNum(totalNum);
        order.setTotalMoney(totalMoney);
        order.setPayMoney(order.getTotalMoney());
        order.setId(String.valueOf(idWorker.nextId()));
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setSourceType("1");
        order.setOrderStatus("0");
        order.setPayStatus("0");

        //添加订单信息
        orderMapper.insertSelective(order);

       //添加订单明细
        for (OrderItem orderItem : orderItems) {
            orderItem.setId(String.valueOf(idWorker.nextId()));
            orderItem.setOrderId(order.getId());
            orderItem.setIsReturn("0");
            //添加到订单明细中
            orderItemMapper.insertSelective(orderItem);
        }

        //修改库存
        skuFeign.decrCount(order.getUsername());

        //添加用户积分
        userFeign.addPoints(2);

        //线上支付，记录日志
        if(order.getPayType().equalsIgnoreCase("1")){
            //创建日志信息
            OrderLog orderLog = new OrderLog();
            orderLog.setId(String.valueOf(idWorker.nextId()));  //唯一ID
            orderLog.setOrderId(order.getId());     //订单的ID
            orderLog.setMoney(order.getPayMoney()); //支付金额
            orderLog.setOrderStatus(order.getOrderStatus()); //订单状态
            orderLog.setPayStatus(order.getPayStatus());   //支付状态
            orderLog.setUsername(order.getUsername());      //下单用户
            orderLog.setRemarks("创建支付记录！");    //备注

            //将支付记录存入到Reids namespace  key  value
            redisTemplate.boundHashOps("OrderLog").put(orderLog.getId(),orderLog);

            //创建一个订单和日志的映射关系
            redisTemplate.boundHashOps("OrderMappingLog"+order.getUsername()).put(order.getId(),orderLog.getId());
        }


        //删除购物车信息
        //redisTemplate.delete("Cart_" + order.getUsername());
    }


    /***
     * 订单的删除操作
     * @param orderLog
     */
    @Override
    public void deleteOrder(OrderLog orderLog) {
        //改状态
        Order order = orderMapper.selectByPrimaryKey(orderLog.getOrderId());
        order.setUpdateTime(new Date());
        order.setPayStatus("2");    //支付失败
        orderMapper.updateByPrimaryKeySelective(order);

        //删除日志缓存
        redisTemplate.boundHashOps("OrderLog").delete(orderLog.getId());

        //删除映射缓存
        redisTemplate.boundHashOps("OrderMappingLog"+order.getUsername()).delete(orderLog.getOrderId());
    }

    /***
     * 订单修改
     * @param orderId
     * @param transactionid  微信支付的交易流水号
     * @param username 清空Reids日志->username->  OrderMappingLog+username.delete(order.id)
     * @param orderLog
     */
    @Override
    public void updateStatus(String orderId, String username,String transactionid, OrderLog orderLog) {
        //1.修改订单
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setUpdateTime(new Date());
        order.setPayTime(order.getUpdateTime());    //不允许这么写
        order.setTransactionId(transactionid);  //交易流水号
        order.setPayStatus("1");    //已支付
        orderMapper.updateByPrimaryKeySelective(order);

        //2.将日志从Redis中取出，同步到MySQL中
        orderLog.setPayStatus(order.getPayStatus());    //支付状态
        orderLogMapper.insertSelective(orderLog);

        //3.删除Redis中的日志
        redisTemplate.boundHashOps("OrderLog").delete(orderLog.getId());
        redisTemplate.boundHashOps("OrderMappingLog"+order.getUsername()).delete(orderId);
    }

    /**
     * Order条件+分页查询
     * @param order 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Order> findPage(Order order, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(order);
        //执行搜索
        return new PageInfo<Order>(orderMapper.selectByExample(example));
    }

    /**
     * Order分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Order> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Order>(orderMapper.selectAll());
    }

    /**
     * Order条件查询
     * @param order
     * @return
     */
    @Override
    public List<Order> findList(Order order){
        //构建查询条件
        Example example = createExample(order);
        //根据构建的条件查询数据
        return orderMapper.selectByExample(example);
    }


    /**
     * Order构建查询对象
     * @param order
     * @return
     */
    public Example createExample(Order order){
        Example example=new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if(order!=null){
            // 订单id
            if(!StringUtils.isEmpty(order.getId())){
                    criteria.andEqualTo("id",order.getId());
            }
            // 数量合计
            if(!StringUtils.isEmpty(order.getTotalNum())){
                    criteria.andEqualTo("totalNum",order.getTotalNum());
            }
            // 金额合计
            if(!StringUtils.isEmpty(order.getTotalMoney())){
                    criteria.andEqualTo("totalMoney",order.getTotalMoney());
            }
            // 优惠金额
            if(!StringUtils.isEmpty(order.getPreMoney())){
                    criteria.andEqualTo("preMoney",order.getPreMoney());
            }
            // 邮费
            if(!StringUtils.isEmpty(order.getPostFee())){
                    criteria.andEqualTo("postFee",order.getPostFee());
            }
            // 实付金额
            if(!StringUtils.isEmpty(order.getPayMoney())){
                    criteria.andEqualTo("payMoney",order.getPayMoney());
            }
            // 支付类型，1、在线支付、0 货到付款
            if(!StringUtils.isEmpty(order.getPayType())){
                    criteria.andEqualTo("payType",order.getPayType());
            }
            // 订单创建时间
            if(!StringUtils.isEmpty(order.getCreateTime())){
                    criteria.andEqualTo("createTime",order.getCreateTime());
            }
            // 订单更新时间
            if(!StringUtils.isEmpty(order.getUpdateTime())){
                    criteria.andEqualTo("updateTime",order.getUpdateTime());
            }
            // 付款时间
            if(!StringUtils.isEmpty(order.getPayTime())){
                    criteria.andEqualTo("payTime",order.getPayTime());
            }
            // 发货时间
            if(!StringUtils.isEmpty(order.getConsignTime())){
                    criteria.andEqualTo("consignTime",order.getConsignTime());
            }
            // 交易完成时间
            if(!StringUtils.isEmpty(order.getEndTime())){
                    criteria.andEqualTo("endTime",order.getEndTime());
            }
            // 交易关闭时间
            if(!StringUtils.isEmpty(order.getCloseTime())){
                    criteria.andEqualTo("closeTime",order.getCloseTime());
            }
            // 物流名称
            if(!StringUtils.isEmpty(order.getShippingName())){
                    criteria.andEqualTo("shippingName",order.getShippingName());
            }
            // 物流单号
            if(!StringUtils.isEmpty(order.getShippingCode())){
                    criteria.andEqualTo("shippingCode",order.getShippingCode());
            }
            // 用户名称
            if(!StringUtils.isEmpty(order.getUsername())){
                    criteria.andLike("username","%"+order.getUsername()+"%");
            }
            // 买家留言
            if(!StringUtils.isEmpty(order.getBuyerMessage())){
                    criteria.andEqualTo("buyerMessage",order.getBuyerMessage());
            }
            // 是否评价
            if(!StringUtils.isEmpty(order.getBuyerRate())){
                    criteria.andEqualTo("buyerRate",order.getBuyerRate());
            }
            // 收货人
            if(!StringUtils.isEmpty(order.getReceiverContact())){
                    criteria.andEqualTo("receiverContact",order.getReceiverContact());
            }
            // 收货人手机
            if(!StringUtils.isEmpty(order.getReceiverMobile())){
                    criteria.andEqualTo("receiverMobile",order.getReceiverMobile());
            }
            // 收货人地址
            if(!StringUtils.isEmpty(order.getReceiverAddress())){
                    criteria.andEqualTo("receiverAddress",order.getReceiverAddress());
            }
            // 订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面
            if(!StringUtils.isEmpty(order.getSourceType())){
                    criteria.andEqualTo("sourceType",order.getSourceType());
            }
            // 交易流水号
            if(!StringUtils.isEmpty(order.getTransactionId())){
                    criteria.andEqualTo("transactionId",order.getTransactionId());
            }
            // 订单状态,0:未完成,1:已完成，2：已退货
            if(!StringUtils.isEmpty(order.getOrderStatus())){
                    criteria.andEqualTo("orderStatus",order.getOrderStatus());
            }
            // 支付状态,0:未支付，1：已支付，2：支付失败
            if(!StringUtils.isEmpty(order.getPayStatus())){
                    criteria.andEqualTo("payStatus",order.getPayStatus());
            }
            // 发货状态,0:未发货，1：已发货，2：已收货
            if(!StringUtils.isEmpty(order.getConsignStatus())){
                    criteria.andEqualTo("consignStatus",order.getConsignStatus());
            }
            // 是否删除
            if(!StringUtils.isEmpty(order.getIsDelete())){
                    criteria.andEqualTo("isDelete",order.getIsDelete());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        orderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Order
     * @param order
     */
    @Override
    public void update(Order order){
        orderMapper.updateByPrimaryKey(order);
    }

    /**
     * 根据ID查询Order
     * @param id
     * @return
     */
    @Override
    public Order findById(String id){
        return  orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Order全部数据
     * @return
     */
    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }
}
