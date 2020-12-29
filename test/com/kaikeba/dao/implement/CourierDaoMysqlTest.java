package com.kaikeba.dao.implement;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.dao.BaseExpressDao;
import exception.DuplicateCodeException;
import exception.DuplicateUserPhoneException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-13 10:42
 */
public class CourierDaoMysqlTest {
    BaseCourierDao dao = new CourierDaoMysql();

    @Test
    public void insert() throws DuplicateUserPhoneException {
        //username,userPhone,idCard,password,deliveryNumber
        Courier c = new Courier("ttt","13536774129","999","123");
        //c.setUsername("testusername");
        //c.setPassword("123456");
        //把你想插入的属性set到这个对象里面去就OK
        //System.out.println(c);
        //少构造方法

        //boolean insert = false;
        boolean insert = dao.insert(c);
//        try {
//            insert = dao.insert(c);
//        } catch (DuplicateCodeException duplicateCodeException) {
//            System.out.println("取件码重复的异常被捕获到了");
//        }
        System.out.println(insert);
    }

    @Test
    public void findAll() {
        List<Courier> all= dao.findAll(false,0,0);
        System.out.println(all);
    }
}