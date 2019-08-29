package com.changgou.controller;

import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/*****
 * @Author: www.itheima.com
 * @Description: com.changgou.controller
 ****/
@Controller
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    /***
     * 搜索服务
     * @RequestParam(required = false)Map<String,String> searchMap
     *          required = false:表示可以不用传数据
     * @return
     */
    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false)Map<String,String> searchMap, Model model){
        //搜索调用搜索微服务
        Map result = skuFeign.search(searchMap);
        //搜索数据
        model.addAttribute("result",result);

        //搜索的条件，用于回显
        model.addAttribute("searchMap",searchMap);

        //创建分页对象
        Page<SkuInfo> page = new Page<SkuInfo>(
                Long.parseLong(result.get("total").toString()),
                Integer.parseInt(result.get("pageNum").toString()),
                Integer.parseInt(result.get("pageSize").toString())
        );
        model.addAttribute("page",page);

        //URL组装
        String[] urls = url(searchMap);
        model.addAttribute("url",urls[0]);
        model.addAttribute("sorturl",urls[1]);
        return "search";
    }

    /***
     * 组装每次请求的URL地址
     * @param searchMap
     * @return
     */
    public String[] url(Map<String,String> searchMap){
        //基础的URL地址
        String url="/search/list";
        String sorturl="/search/list";

        if(searchMap!=null && searchMap.size()>0){
            //添加一个？
            url+="?";
            sorturl+="?";

            //循环searchMap
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                //"/search/list?spec_颜色=红";
                String key = entry.getKey();
                String value = entry.getValue();

                //跳过分页参数
                if(key.equalsIgnoreCase("pageNum")){
                    continue;
                }

                //往基础的URL中追加参数
                url+=key+"="+value+"&";

                //排序url需要跳过排序
                if(key.equalsIgnoreCase("sortRule") || key.equalsIgnoreCase("sortFeild")){
                    continue;
                }
                sorturl+=key+"="+value+"&";
            }
            //将最后一个&去掉
            url=url.substring(0,url.length()-1);
        }
        return new String[]{url,sorturl};
    }
}
