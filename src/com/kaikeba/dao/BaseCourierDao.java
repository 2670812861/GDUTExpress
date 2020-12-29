package com.kaikeba.dao;

import com.kaikeba.bean.Courier;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-12 21:31
 */
public interface BaseCourierDao {
    /**
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ������п��Ա�����տ��Աע�������
     * @Date 2020��12��13��  10:12:55
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @Date Modify in 2020��12��13��  10:12:55
     * @Modify Content:
     **/
    Map<String,Integer> console();

    /**
     * @Author ������
     * @Description ���ڲ�ѯ���п��Ա
     * @Date 2020��12��13��  10:12:38
     * @Param [limit, offset, pageNumber]
     * @return java.util.List<com.kaikeba.bean.Courier>
     * @Date Modify in 2020��12��13��  10:12:38
     * @Modify Content:
     **/
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * @Author ������
     * @Description  ���ݿ��Ա��ţ���ѯ���Ա��Ϣ
     * @Date 2020��12��12��  22:12:27
     * @Param [number]
     * @return com.kaikeba.bean.Courier
     * @Date Modify in 2020��12��12��  22:12:27
     * @Modify Content:
     **/
    Courier findById(int id);

    /**
     * @Author ������
     * @Description ���ݿ��Ա���ֻ����룬���ҿ��Ա��Ϣ
     * @Date 2020��12��13��  10:12:21
     * @Param [userPhone]
     * @return com.kaikeba.bean.Courier
     * @Date Modify in 2020��12��13��  10:12:21
     * @Modify Content:
     **/
    Courier findByUserPhone(String userPhone);

    /**
     * @Author ������
     * @Description ���ݿ��Ա�����֤�����ҿ��Ա��Ϣ
     * @Date 2020��12��12��  22:12:34
     * @Param [IdentityCard]
     * @return com.kaikeba.bean.Courier
     * @Date Modify in 2020��12��12��  22:12:34
     * @Modify Content:
     **/
    Courier findByIdentityCard(String IdentityCard);

    /**
     * @Author ������
     * @Description Ҫ¼��Ŀ��Ա��Ϣ
     * @Date 2020��12��12��  22:12:26
     * @Param [c]
     * @return boolean
     * @Date Modify in 2020��12��12��  22:12:26
     * @Modify Content:
     **/
    boolean insert(Courier c) throws DuplicateUserPhoneException;

    /**
     * @Author ������
     * @Description ���Ա���޸�
     * @Date 2020��12��12��  22:12:50
     * @Param [userPhone, newCourier]
     * @return boolean
     * @Date Modify in 2020��12��12��  22:12:50
     * @Modify Content:
     **/
    boolean update(int id,Courier newCourier) throws DuplicateUserPhoneException;

    /**
     * @Author ������
     * @Description ����id,�����ϴε�¼ʱ��
     * @Date 2020��12��12��  22:12:46
     * @Param [userPhone, date]
     * @return boolean
     * @Date Modify in 2020��12��12��  22:12:46
     * @Modify Content:
     **/
    boolean updateLastLoginTime(Courier c, Date date);

    /**
     * @Author ������
     * @Description
     * @Date 2020��12��27��  03:12:14
     * @Param [id]
     * @return boolean
     * @Date Modify in 2020��12��27��  03:12:14
     * @Modify Content:
     **/
    boolean delete(int id);

    /**
     * @Author ������
     * @Description ����Ͷ����
     * @Date 2020��12��27��  09:12:13
     * @Param [sysPhone]
     * @return boolean
     * @Date Modify in 2020��12��27��  09:12:13
     * @Modify Content:
     **/
    boolean updateDeliveryNumber(String sysPhone);

    /**
     * @Author ������
     * @Description ��ȡͶ����
     * @Date 2020��12��27��  11:12:22
     * @Param [sysPhone]
     * @return int
     * @Date Modify in 2020��12��27��  11:12:22
     * @Modify Content:
     **/
    int getDeliveryNumber(String sysPhone);
}
