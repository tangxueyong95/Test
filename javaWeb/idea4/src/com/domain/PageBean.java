package com.domain;

import java.util.List;

/**
 * 包名:com.itheima.domain
 * 作者:Leevi
 * 日期2019-05-15  10:02
 */
public class PageBean {
    private Integer pageSize;//每页的数据条数
    private Integer totalSize;//总数据条数
    private Integer totalPage;//总页数
    private Integer currentPage;//当前页数
    private List<Contact> list;//当前页显示的联系人集合

    @Override
    public String toString() {
        return "PageBean{" +
                "pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", list=" + list +
                '}';
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Contact> getList() {
        return list;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }
}
