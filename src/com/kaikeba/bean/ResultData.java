package com.kaikeba.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-11 9:31
 */
public class ResultData<T> {
    //ÿ�β�ѯ�����ݼ���
    private List<T> rows = new ArrayList<>();
    //������
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
