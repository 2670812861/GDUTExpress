package com.kaikeba.service;

import com.kaikeba.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-10 23:59
 */
public class ExpressServiceTest {

    @Test
    public void insert() {
        Express e = new Express("12311","������","13751768661","˳����","18888888888","666666");
        boolean flag = ExpressService.insert(e);
        System.out.println(flag);
    }
}