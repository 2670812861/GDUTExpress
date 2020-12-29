package com.kaikeba.dao;

import java.util.Date;

/**
 * @Author: 李梓豪
 * @Description:用于定义eadmin表格的操作规范
 * @Date Created in 2020-12-08 19:25
 */
public interface BaseAdminDao {
    /**
     * @Author 李梓豪
     * @Description 根据用户名，更新时间和登录ip
     * @Date 2020年12月08日  19:12:05
     * @Param [username]
     * @return void
     * @Date Modify in 2020年12月08日  19:12:05
     * @Modify Content:
     **/
    void updateLoginTime(String username, Date date, String ip);
    /**
     * @Author 李梓豪
     * @Description 管理员根据帐号密码登录
     * @Date 2020年12月08日  19:12:45
     * @Param [username, password] ：[帐号，密码]
     * @return 登录的结果，true表示登录成功
     * @Date Modify in 2020年12月08日  19:12:45
     * @Modify Content:
     **/
    boolean login(String username,String password);
}
