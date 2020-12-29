package com.kaikeba.dao;


import com.kaikeba.bean.Courier;
import com.kaikeba.bean.User;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-17 16:23
 */
public interface BaseUserDao {
    /**
     * @Author 李梓豪
     * @Description 用于查询数据库中所有用户，当日用户注册的数量
     * @Date 2020年12月13日  10:12:55
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     * @Date Modify in 2020年12月13日  10:12:55
     * @Modify Content:
     **/
    Map<String,Integer> console();

    /**
     * @Author 李梓豪
     * @Description 用于查询所有用户
     * @Date 2020年12月13日  10:12:38
     * @Param [limit, offset, pageNumber]
     * @return 用户的集合
     * @Date Modify in 2020年12月13日  10:12:38
     * @Modify Content:
     **/
    List<User> findAll(boolean limit, int offset, int pageNumber);

    /**
     * @Author 李梓豪
     * @Description  根据用户编号，查询用户信息
     * @Date 2020年12月12日  22:12:27
     * @Param [number]
     * @return com.kaikeba.bean.User
     * @Date Modify in 2020年12月12日  22:12:27
     * @Modify Content:
     **/
    User findById(int id);

    /**
     * @Author 李梓豪
     * @Description 根据用户的手机号码，查找用户信息
     * @Date 2020年12月13日  10:12:21
     * @Param [userPhone]
     * @return com.kaikeba.bean.User
     * @Date Modify in 2020年12月13日  10:12:21
     * @Modify Content:
     **/
    User findByUserPhone(String userPhone);

    /**
     * @Author 李梓豪
     * @Description 要录入的用户信息
     * @Date 2020年12月12日  22:12:26
     * @Param [c]
     * @return boolean
     * @Date Modify in 2020年12月12日  22:12:26
     * @Modify Content:
     **/
    boolean insert(User u) throws DuplicateUserPhoneException;

    /**
     * @Author 李梓豪
     * @Description 用户的修改
     * @Date 2020年12月12日  22:12:50
     * @Param [userPhone, newCourier]
     * @return boolean
     * @Date Modify in 2020年12月12日  22:12:50
     * @Modify Content:
     **/
    boolean update(int id,User newUser) throws DuplicateUserPhoneException;

    /**
     * @Author 李梓豪
     * @Description 根据id,更新上次登录时间
     * @Date 2020年12月12日  22:12:46
     * @Param [userPhone, date]
     * @return boolean
     * @Date Modify in 2020年12月12日  22:12:46
     * @Modify Content:
     **/
    boolean updateLastLoginTime(User u, Date date);

    /**
     * @Author 李梓豪
     * @Description 根据id,删除用户信息
     * @Date 2020年12月17日  16:12:00
     * @Param [id]
     * @return boolean
     * @Date Modify in 2020年12月17日  16:12:00
     * @Modify Content:
     **/
    boolean delete(int id);
}
