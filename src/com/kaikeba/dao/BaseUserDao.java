package com.kaikeba.dao;


import com.kaikeba.bean.Courier;
import com.kaikeba.bean.User;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-17 16:23
 */
public interface BaseUserDao {
    /**
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ��������û��������û�ע�������
     * @Date 2020��12��13��  10:12:55
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @Date Modify in 2020��12��13��  10:12:55
     * @Modify Content:
     **/
    Map<String,Integer> console();

    /**
     * @Author ������
     * @Description ���ڲ�ѯ�����û�
     * @Date 2020��12��13��  10:12:38
     * @Param [limit, offset, pageNumber]
     * @return �û��ļ���
     * @Date Modify in 2020��12��13��  10:12:38
     * @Modify Content:
     **/
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * @Author ������
     * @Description  �����û���ţ���ѯ�û���Ϣ
     * @Date 2020��12��12��  22:12:27
     * @Param [number]
     * @return com.kaikeba.bean.User
     * @Date Modify in 2020��12��12��  22:12:27
     * @Modify Content:
     **/
    User findById(int id);

    /**
     * @Author ������
     * @Description �����û����ֻ����룬�����û���Ϣ
     * @Date 2020��12��13��  10:12:21
     * @Param [userPhone]
     * @return com.kaikeba.bean.User
     * @Date Modify in 2020��12��13��  10:12:21
     * @Modify Content:
     **/
    User findByUserPhone(String userPhone);

    /**
     * @Author ������
     * @Description Ҫ¼����û���Ϣ
     * @Date 2020��12��12��  22:12:26
     * @Param [c]
     * @return boolean
     * @Date Modify in 2020��12��12��  22:12:26
     * @Modify Content:
     **/
    boolean insert(User u) throws DuplicateUserPhoneException;

    /**
     * @Author ������
     * @Description �û����޸�
     * @Date 2020��12��12��  22:12:50
     * @Param [userPhone, newCourier]
     * @return boolean
     * @Date Modify in 2020��12��12��  22:12:50
     * @Modify Content:
     **/
    boolean update(int id,User newUser) throws DuplicateUserPhoneException;

    /**
     * @Author ������
     * @Description ����id,�����ϴε�¼ʱ��
     * @Date 2020��12��12��  22:12:46
     * @Param [userPhone, date]
     * @return boolean
     * @Date Modify in 2020��12��12��  22:12:46
     * @Modify Content:
     **/
    boolean updateLastLoginTime(User u, Date date);

    /**
     * @Author ������
     * @Description ����id,ɾ���û���Ϣ
     * @Date 2020��12��17��  16:12:00
     * @Param [id]
     * @return boolean
     * @Date Modify in 2020��12��17��  16:12:00
     * @Modify Content:
     **/
    boolean delete(int id);
}
