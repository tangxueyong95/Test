package com.changgou.goods.service.impl;
import com.alibaba.fastjson.JSON;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/****
 * @Author:www.itheima.com
 * @Description:Spu业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    /***
     * 找回商品
     * @param spuId
     */
    @Override
    public void restore(Long spuId) {
        //查询商品
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //删除状态
        if(spu.getIsDelete().equalsIgnoreCase("0")){
            throw new RuntimeException("无法找回未删除的商品！");
        }
        //修改状态
        spu.setIsDelete("0");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /***
     * 逻辑删除
     * @param spuId
     */
    @Override
    public void logicDelete(Long spuId) {
        //获取Spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //如果已经删除,则不能再删除
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("该商品已经删除，不能重复删！");
        }
        //修改删除状态
        spu.setIsDelete("1");
        //修改审核状态
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /***
     * 批量上架
     * @param ids:Long[] 需要上架的商品ID(SpuId)集合
     */
    @Override
    public void putMany(Long[] ids) {
        //update ismarkertable=1 where id in(1,2,3,4,6,7,8) and status=1 and isdelete=0 and ismarkertable=0
        Spu spu = new Spu();
        spu.setIsMarketable("1"); //上架

        //构建条件 Example
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));  // in(1,2,3,4,6,7,8)
        //状态
        criteria.andEqualTo("status","1");
        //是否删除判断
        criteria.andEqualTo("isDelete","0");
        //没有上架的就上架
        criteria.andEqualTo("isMarketable","0");
        spuMapper.updateByExampleSelective(spu,example);
    }

    /***
     * 商品上架
     * @param spuId
     */
    @Override
    public void put(Long spuId) {
        //获取Spu数据
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        //判断是否已删除
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("该商品已经删除，无法上架！");
        }
        //判断状态
        if(spu.getStatus().equalsIgnoreCase("0")){
            throw new RuntimeException("该商品未审核，无法上架！");
        }
        //上架操作
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /***
     * 商品下架
     * @param spuId
     */
    @Override
    public void pull(Long spuId) {
        //获取Spu数据
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        //判断是否已删除
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("该商品已经删除，无法下架！");
        }
        //下架操作
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /***
     * 商品审核
     * 修改status审核状态  1：审核
     * 修改ismarkerTable商家 1:上架
     */
    @Override
    public void audit(Long spuId) {
        //查询出Spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断当前商品是否被删除
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            throw new RuntimeException("只能审核未删除的商品！");
        }

        //没有删除，则审核通过并且上架
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /***
     * 查询Goods数据
     * @param spuId
     * @return
     */
    @Override
    public Goods findGoodsBySpuId(Long spuId) {
        //查询Spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //List<Sku>
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    /***
     * 保存商品数据
     * @param goods=Spu+List<Sku>
     */
    @Override
    public void saveGoods(Goods goods) {
        //Spu->保存
        //获取Spu
        Spu spu = goods.getSpu();
        if(spu.getId()==null){
            //增加
            //Spu的ID不是自增，需要给他设置值
            spu.setId(idWorker.nextId());
            //保存Spu
            spuMapper.insertSelective(spu);
        }else{
            //修改Spu
            spuMapper.updateByPrimaryKeySelective(spu);
            //删除之前的List<Sku> delete from tb_sku where spu_id=?
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }


        //List<Sku>->循环保存
        Date date = new Date();
        //3级分类
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        //查询品牌数据
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        List<Sku> skuList = goods.getSkuList();
        if(skuList!=null && skuList.size()>0){
            for (Sku sku : skuList) {
                //SKU的ID
                sku.setId(idWorker.nextId());

                //防止spec为空
                if(StringUtils.isEmpty(sku.getSpec())){
                    sku.setSpec("{}");
                }

                //SKU的name  华为P30  环绕   20英寸  165
                String name=spu.getName();
                //获取规格  {"电视音响效果":"环绕","电视屏幕尺寸":"20英寸","尺码":"165"}
                Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                for (Map.Entry<String, String> entry : specMap.entrySet()) {
                    name+="   "+entry.getValue();
                }
                sku.setName(name);

                //创建时间
                sku.setCreateTime(date);
                //修改时间
                sku.setUpdateTime(date);
                //SpuID
                sku.setSpuId(spu.getId());
                //分类ID   3级分类
                sku.setCategoryId(spu.getCategory3Id());
                //分类名字
                sku.setCategoryName(category.getName());
                //品牌名字
                sku.setBrandName(brand.getName());
                // 1-正常，2-下架，3-删除
                sku.setStatus("1");

                //增加数据
                skuMapper.insertSelective(sku);
            }
        }

    }

    /**
     * Spu条件+分页查询
     * @param spu 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu){
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     * @param spu
     * @return
     */
    public Example createExample(Spu spu){
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(spu!=null){
            // 主键
            if(!StringUtils.isEmpty(spu.getId())){
                    criteria.andEqualTo("id",spu.getId());
            }
            // 货号
            if(!StringUtils.isEmpty(spu.getSn())){
                    criteria.andEqualTo("sn",spu.getSn());
            }
            // SPU名
            if(!StringUtils.isEmpty(spu.getName())){
                    criteria.andLike("name","%"+spu.getName()+"%");
            }
            // 副标题
            if(!StringUtils.isEmpty(spu.getCaption())){
                    criteria.andEqualTo("caption",spu.getCaption());
            }
            // 品牌ID
            if(!StringUtils.isEmpty(spu.getBrandId())){
                    criteria.andEqualTo("brandId",spu.getBrandId());
            }
            // 一级分类
            if(!StringUtils.isEmpty(spu.getCategory1Id())){
                    criteria.andEqualTo("category1Id",spu.getCategory1Id());
            }
            // 二级分类
            if(!StringUtils.isEmpty(spu.getCategory2Id())){
                    criteria.andEqualTo("category2Id",spu.getCategory2Id());
            }
            // 三级分类
            if(!StringUtils.isEmpty(spu.getCategory3Id())){
                    criteria.andEqualTo("category3Id",spu.getCategory3Id());
            }
            // 模板ID
            if(!StringUtils.isEmpty(spu.getTemplateId())){
                    criteria.andEqualTo("templateId",spu.getTemplateId());
            }
            // 运费模板id
            if(!StringUtils.isEmpty(spu.getFreightId())){
                    criteria.andEqualTo("freightId",spu.getFreightId());
            }
            // 图片
            if(!StringUtils.isEmpty(spu.getImage())){
                    criteria.andEqualTo("image",spu.getImage());
            }
            // 图片列表
            if(!StringUtils.isEmpty(spu.getImages())){
                    criteria.andEqualTo("images",spu.getImages());
            }
            // 售后服务
            if(!StringUtils.isEmpty(spu.getSaleService())){
                    criteria.andEqualTo("saleService",spu.getSaleService());
            }
            // 介绍
            if(!StringUtils.isEmpty(spu.getIntroduction())){
                    criteria.andEqualTo("introduction",spu.getIntroduction());
            }
            // 规格列表
            if(!StringUtils.isEmpty(spu.getSpecItems())){
                    criteria.andEqualTo("specItems",spu.getSpecItems());
            }
            // 参数列表
            if(!StringUtils.isEmpty(spu.getParaItems())){
                    criteria.andEqualTo("paraItems",spu.getParaItems());
            }
            // 销量
            if(!StringUtils.isEmpty(spu.getSaleNum())){
                    criteria.andEqualTo("saleNum",spu.getSaleNum());
            }
            // 评论数
            if(!StringUtils.isEmpty(spu.getCommentNum())){
                    criteria.andEqualTo("commentNum",spu.getCommentNum());
            }
            // 是否上架,0已下架，1已上架
            if(!StringUtils.isEmpty(spu.getIsMarketable())){
                    criteria.andEqualTo("isMarketable",spu.getIsMarketable());
            }
            // 是否启用规格
            if(!StringUtils.isEmpty(spu.getIsEnableSpec())){
                    criteria.andEqualTo("isEnableSpec",spu.getIsEnableSpec());
            }
            // 是否删除,0:未删除，1：已删除
            if(!StringUtils.isEmpty(spu.getIsDelete())){
                    criteria.andEqualTo("isDelete",spu.getIsDelete());
            }
            // 审核状态，0：未审核，1：已审核，2：审核不通过
            if(!StringUtils.isEmpty(spu.getStatus())){
                    criteria.andEqualTo("status",spu.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        //查询Spu数据
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //未审核的才能删除
        if(spu.getStatus().equalsIgnoreCase("1")){
            throw new RuntimeException("不能直接删除已审核的商品！");
        }
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spu
     * @param spu
     */
    @Override
    public void update(Spu spu){
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     * @param spu
     */
    @Override
    public void add(Spu spu){
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     * 根据Spu的ID查询Spu和List<Sku>信息
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id){
       return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }
}
