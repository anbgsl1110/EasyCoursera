package com.jasonwangex.easyCoursera.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by wangjz
 * on 17/5/8.
 */
public class PageBean<T> {
    private int page;
    private int size;
    private int total;
    private List<T> items;

    public PageBean(int page, int size, int total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @JsonIgnore
    public int getOffset() {
        if (page <= 0 || size <= 0) return 0;

        return (page - 1) * size;
    }
}
