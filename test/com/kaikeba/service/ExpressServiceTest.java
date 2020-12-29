package com.kaikeba.service;

import com.kaikeba.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-10 23:59
 */
public class ExpressServiceTest {

    @Test
    public void insert() {
        Express e = new Express("12311","何文宇","13751768661","顺丰快递","18888888888","666666");
        boolean flag = ExpressService.insert(e);
        System.out.println(flag);
    }
}