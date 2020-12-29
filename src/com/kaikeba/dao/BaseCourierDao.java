package com.kaikeba.dao;

import com.kaikeba.bean.Courier;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-12 21:31
 */
public interface BaseCourierDao {
    /**
     * @Author 李梓豪
     * @Description 用于查询数据库中所有快递员，当日快递员注册的数量
     * @Date 2020年12月13日  10:12:55
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @Date Modify in 2020年12月13日  10:12:55
     * @Modify Content:
     **/
    Map<String,Integer> console();

    /**
     * @Author 李梓豪
     * @Description 用于查询所有快递员
     * @Date 2020年12月13日  10:12:38
     * @Param [limit, offset, pageNumber]
     * @return java.util.List<com.kaikeba.bean.Courier>
     * @Date Modify in 2020年12月13日  10:12:38
     * @Modify Content:
     **/
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * @Author 李梓豪
     * @Description  根据快递员编号，查询快递员信息
     * @Date 2020年12月12日  22:12:27
     * @Param [number]
     * @return com.kaikeba.bean.Courier
     * @Date Modify in 2020年12月12日  22:12:27
     * @Modify Content:
     **/
    Courier findById(int id);

    /**
     * @Author 李梓豪
     * @Description 根据快递员的手机号码，查找快递员信息
     * @Date 2020年12月13日  10:12:21
     * @Param [userPhone]
     * @return com.kaikeba.bean.Courier
     * @Date Modify in 2020年12月13日  10:12:21
     * @Modify Content:
     **/
    Courier findByUserPhone(String userPhone);

    /**
     * @Author 李梓豪
     * @Description 根据快递员的身份证，查找快递员信息
     * @Date 2020年12月12日  22:12:34
     * @Param [IdentityCard]
     * @return com.kaikeba.bean.Courier
     * @Date Modify in 2020年12月12日  22:12:34
     * @Modify Content:
     **/
    Courier findByIdentityCard(String IdentityCard);

    /**
     * @Author 李梓豪
     * @Description 要录入的快递员信息
     * @Date 2020年12月12日  22:12:26
     * @Param [c]
     * @return boolean
     * @Date Modify in 2020年12月12日  22:12:26
     * @Modify Content:
     **/
    boolean insert(Courier c) throws DuplicateUserPhoneException;

    /**
     * @Author 李梓豪
     * @Description 快递员的修改
     * @Date 2020年12月12日  22:12:50
     * @Param [userPhone, newCourier]
     * @return boolean
     * @Date Modify in 2020年12月12日  22:12:50
     * @Modify Content:
     **/
    boolean update(int id,Courier newCourier) throws DuplicateUserPhoneException;

    /**
     * @Author 李梓豪
     * @Description 根据id,更新上次登录时间
     * @Date 2020年12月12日  22:12:46
     * @Param [userPhone, date]
     * @return boolean
     * @Date Modify in 2020年12月12日  22:12:46
     * @Modify Content:
     **/
    boolean updateLastLoginTime(Courier c, Date date);

    /**
     * @Author 李梓豪
     * @Description
     * @Date 2020年12月27日  03:12:14
     * @Param [id]
     * @return boolean
     * @Date Modify in 2020年12月27日  03:12:14
     * @Modify Content:
     **/
    boolean delete(int id);

    /**
     * @Author 李梓豪
     * @Description 更新投递数
     * @Date 2020年12月27日  09:12:13
     * @Param [sysPhone]
     * @return boolean
     * @Date Modify in 2020年12月27日  09:12:13
     * @Modify Content:
     **/
    boolean updateDeliveryNumber(String sysPhone);

    /**
     * @Author 李梓豪
     * @Description 获取投递数
     * @Date 2020年12月27日  11:12:22
     * @Param [sysPhone]
     * @return int
     * @Date Modify in 2020年12月27日  11:12:22
     * @Modify Content:
     **/
    int getDeliveryNumber(String sysPhone);
}
