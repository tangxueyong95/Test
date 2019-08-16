package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.goods.feign
 * 1.指定当前Feign调用的远程服务名字
 ****/
@FeignClient(value = "goods")   //跟商品微服务的服务名字保持一致
@RequestMapping(value = "/sku")
public interface SkuFeign {

    /***
     * 库存递减
     * @param username:最好从令牌获取,防止别人恶意访问
     * @return
     */
    @PostMapping(value = "/decr/count")
    Result decrCount(@RequestParam(value = "username") String username);

    /***
     * 根据ID查询Sku数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Sku> findById(@PathVariable Long id);

    /***
     * 根据审核状态查询Sku
     * @param status
     * @return
     */
    @GetMapping(value = "/status/{status}")
    List<Sku> findByStatus(@PathVariable(value = "status")String status);


    /***
     * 多条件搜索品牌数据
     * @param sku
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Sku>> findList(@RequestBody(required = false)  Sku sku);
}
