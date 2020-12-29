package com.kaikeba.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-11 9:31
 */
public class ResultData<T> {
    //每次查询的数据集合
    private List<T> rows = new ArrayList<>();
    //总数量
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
