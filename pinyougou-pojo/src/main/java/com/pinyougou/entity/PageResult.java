package com.pinyougou.entity;

import java.io.Serializable;
import java.util.List;

//分页结果类
public class PageResult implements Serializable {

    private Long total;//总记录
    private List rows;//当前页记录.写泛型

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
