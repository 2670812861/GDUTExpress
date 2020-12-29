package com.kaikeba.controller;

import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.dao.implement.ExpressDaoMysql;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-16 21:16
 */
public class CourierControllerTest {

    @Test
    public void list() {
        CourierController controller = new CourierController();
        //String json = controller.list(HttpServletRequest, HttpServletResponse)
    }
}