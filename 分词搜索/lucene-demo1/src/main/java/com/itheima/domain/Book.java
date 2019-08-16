package com.itheima.domain;

/*****
 * @Author: shenkunlin
 * @Date: 2019/7/18 9:58
 * @Description: com.itheima.domain
 ****/
public class Book {
    private Integer id;
    private String name;
    private Float price;
    private String pic;
    private String desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
