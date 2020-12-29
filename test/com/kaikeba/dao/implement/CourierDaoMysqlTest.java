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
 * @Author: ������
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
        //��������������set�������������ȥ��OK
        //System.out.println(c);
        //�ٹ��췽��

        //boolean insert = false;
        boolean insert = dao.insert(c);
//        try {
//            insert = dao.insert(c);
//        } catch (DuplicateCodeException duplicateCodeException) {
//            System.out.println("ȡ�����ظ����쳣��������");
//        }
        System.out.println(insert);
    }

    @Test
    public void findAll() {
        List<Courier> all= dao.findAll(false,0,0);
        System.out.println(all);
    }
}