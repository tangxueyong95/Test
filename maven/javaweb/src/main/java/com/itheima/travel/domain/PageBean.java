package com.itheima.travel.domain;

import java.util.List;

/**
 * 包名:com.itheima.travel.domain
 * 作者:Leevi
 * 日期2019-05-29  10:24
 * 只要pageSize和totalSize确定了，那么totalPage肯定已经确定了
 */
public class PageBean<T> {
    private Integer currentPage;//当前页数
    private Integer totalSize;//总数据条数
    private Integer pageSize;//每页数据条数
    private Integer totalPage;//总页数
    private List<T> list;//当前页的数据集合

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;

        //就将totalPage设置好
        this.totalPage = totalSize % pageSize == 0 ?totalSize / pageSize : totalSize / pageSize + 1;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
