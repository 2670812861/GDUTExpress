package com.kaikeba.dao.implement;

import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseExpressDao;
import exception.DuplicateCodeException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-10 18:07
 */
public class ExpressDaoMysqlTest {
    BaseExpressDao dao = new ExpressDaoMysql();

    @Test
    public void console() {
        List<Map<String,Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<Express> all= dao.findAll(false,0,0);
        System.out.println(all);
    }

    @Test
    public void findByNumber() {
        Express e = dao.findByNumber("122");
        System.out.println(e);
    }

    @Test
    public void findByCode() {
        Express e = dao.findByCode("123456");
        System.out.println(e);
    }

    @Test
    public void findByUserPhone() {
       List<Express> es= dao.findByUserPhone("13751768661");
        System.out.println(es);

    }

    @Test
    public void findBySysPhone() {
        List<Express> es = dao.findBySysPhone("15218456480");
        System.out.println(es);
    }

    @Test
    public void insert() {
        // String number, String username, String userPhone, String company, String sysPhone
        Express e = new Express("12311","何文宇","13751768661","顺丰快递","18888888888","666666");
        boolean insert = false;
        try {
            insert = dao.insert(e);
        } catch (DuplicateCodeException duplicateCodeException) {
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);

    }

    @Test
    public void insert2() {
        // String number, String username, String userPhone, String company, String sysPhone
        boolean insert = false;
        try {
            for (int i=0;i<100;i++){
                Express e = new Express("12311"+i,"何文宇","13751768661","顺丰快递","18888888888",666000+i+"");
                insert = dao.insert(e);
            }
        } catch (DuplicateCodeException duplicateCodeException) {
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);

    }

    @Test
    public void update() {
        Express e = new Express();
        e.setNumber("321");
        e.setCompany("哈哈快递");
        e.setUsername("王二");
        e.setStatus(1);
        boolean flag = dao.update(1,e);
        System.out.println(flag);
    }

    @Test
    public void updateStatus() {
        boolean flag = dao.updateStatus("666666");
        System.out.println(flag);
    }

    @Test
    public void delete() {
       boolean flag = dao.delete(1);
        System.out.println(flag);
    }
}