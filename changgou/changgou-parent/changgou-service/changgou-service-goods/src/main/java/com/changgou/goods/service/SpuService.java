package com.changgou.goods.service;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:www.itheima.com
 * @Description:Spu业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface SpuService {

    /***
     * 找回商品
     */
    void restore(Long spuId);

    /***
     * 逻辑删除
     * 修改删除状态
     */
    void logicDelete(Long spuId);

    /***
     * 批量上架
     * @param ids:Long[] 需要上架的商品ID(SpuId)集合
     */
    void putMany(Long[] ids);

    /***
     * 上架
     */
    void put(Long spuId);


    /***
     * 下架
     */
    void pull(Long spuId);

    /***
     * 商品审核
     * 修改status审核状态  1：审核
     * 修改ismarkerTable商家 1:上架
     */
    void audit(Long spuId);

    /***
     * 根据Spu的ID查询Spu和List<Sku>信息
     * @param spuId
     */
    Goods findGoodsBySpuId(Long spuId);

    /****
     * 保存商品数据
     * @param goods=Spu+List<Sku>
     */
    void saveGoods(Goods goods);

    /***
     * Spu多条件分页查询
     * @param spu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(Spu spu, int page, int size);

    /***
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Spu> findPage(int page, int size);

    /***
     * Spu多条件搜索方法
     * @param spu
     * @return
     */
    List<Spu> findList(Spu spu);

    /***
     * 删除Spu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Spu数据
     * @param spu
     */
    void update(Spu spu);

    /***
     * 新增Spu
     * @param spu
     */
    void add(Spu spu);

    /**
     * 根据ID查询Spu
     * @param id
     * @return
     */
     Spu findById(Long id);

    /***
     * 查询所有Spu
     * @return
     */
    List<Spu> findAll();
}
