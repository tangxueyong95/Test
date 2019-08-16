package com.changgou.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.service.PageService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.service.impl
 ****/
@Service
public class PageServiceImpl implements PageService {

    //模板引擎对象，用于生成静态页
    @Autowired
    private TemplateEngine templateEngine;

    //生成静态页存储路径
    @Value("${pagepath}")
    private String pagePath;


    //分类
    @Autowired
    private CategoryFeign categoryFeign;

    //Spu
    @Autowired
    private SpuFeign spuFeign;

    //List<Sku>
    @Autowired
    private SkuFeign skuFeign;

    /****
     * 加载所有数据
     */
    public Map<String,Object> buildDataModel(Long spuId){
        Map<String,Object> dataMap = new HashMap<>();
        //1:查询Spu
        Result<Spu> spuResult = spuFeign.findById(spuId);
        Spu spu = spuResult.getData();

        //2:查询分类-3个分类
        Category category1 = categoryFeign.findById(spu.getCategory1Id()).getData();
        Category category2 = categoryFeign.findById(spu.getCategory2Id()).getData();
        Category category3 = categoryFeign.findById(spu.getCategory3Id()).getData();
        dataMap.put("category1",category1);
        dataMap.put("category2",category2);
        dataMap.put("category3",category3);

        //取出Spu的图片
        String[] imageList = spu.getImages().split(",");
        dataMap.put("imageList",imageList);
        //规格集合
        dataMap.put("specificationList",JSON.parseObject(spu.getSpecItems(),Map.class));

        //3:查询List<Sku>
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        Result<List<Sku>> skuResult = skuFeign.findList(sku);
        dataMap.put("skuList",skuResult.getData());
        return dataMap;
    }

    /***
     * 生成静态页实现
     * @param spuid
     */
    @Override
    public void createHtml(Long spuid) throws Exception{
        //创建存储数据的容器
        Context context = new Context();
        //查询数据
        Map<String, Object> mapModel = buildDataModel(spuid);
        context.setVariables(mapModel);

        //创建生成的文件存储对象
        File dir = new File(pagePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //指定生成的文件对象
        File dest = new File(dir,spuid+".html");

        /***
         * 生成文件
         * 1:模板名称
         * 2:存储数据的容器对象
         * 3:输出对象
         */
        PrintWriter writer = new PrintWriter(dest,"UTF-8");
        templateEngine.process("item",context,writer);
    }

}
