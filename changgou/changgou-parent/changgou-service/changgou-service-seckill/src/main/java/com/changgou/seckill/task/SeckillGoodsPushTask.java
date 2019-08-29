package com.changgou.seckill.task;

import com.changgou.seckill.dao.SeckillGoodsMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import entity.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.seckill.task
 ****/
@Component
public class SeckillGoodsPushTask {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /****
     * 定时执行
     */
    @Scheduled(cron ="0/30 * * * * ?" )
    public void loadGoodsPushRedis(){
        //时间限制->查询出当前需要展示的时间段
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            //Redis的命名空间
            String namespace = "SeckillGoods_"+DateUtil.data2str(dateMenu,DateUtil.PATTERN_YYYYMMDDHH);

            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();

            //1.审核通过
            criteria.andEqualTo("status","1");

            //2.剩余库存>0
            criteria.andGreaterThan("stockCount",0);

            //3.时间范围查询?   dateMenu=< start_time <dateMenu+2小时
            criteria.andGreaterThanOrEqualTo("startTime",dateMenu);
            criteria.andLessThan("endTime",DateUtil.addDateHour(dateMenu,2));

            //4.查询Redis中当前时间段已经存在的商品，将他们排除在查询范围内
            //  select * from table where id not in(ids)
            Set ids = redisTemplate.boundHashOps(namespace).keys();
            if(ids!=null && ids.size()>0){
                criteria.andNotIn("id",ids);
            }

            //5.查询数据
            String timestr = DateUtil.data2str(dateMenu, DateUtil.PATTERN_YYYY_MM_DDHHMM);
            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);
            for (SeckillGoods seckillGood : seckillGoods) {
                //将完整商品存入到Redis
                redisTemplate.boundHashOps(namespace).put(seckillGood.getId(),seckillGood);
                //将商品的个数存入到队列中o o o o o o o
                redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGood.getId()).leftPushAll(pushIds(seckillGood.getStockCount(),seckillGood.getId()));
                //商品个数计数器
                redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillGood.getId(),seckillGood.getStockCount());
            }
        }
    }

    /***
     * 将商品ID组装成数组
     * @param count
     * @param id
     * @return
     */
    public Long[] pushIds(Integer count,Long id){
        Long[] ids = new Long[count];
        for (int i = 0; i <count ; i++) {
            ids[i]= id;
        }
        return ids;
    }

}
