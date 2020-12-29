package com.kaikeba.service;

import com.kaikeba.dao.BaseAdminDao;
import com.kaikeba.dao.implement.AdminDaoMysql;

import java.util.Date;


/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-08 20:38
 */
public class AdminService {

    private static BaseAdminDao dao = new AdminDaoMysql();
    /**
     * @Author 李梓豪
     * @Description 更新登录时间与ip
     * @Date 2020年12月08日  20:12:07
     * @Param [username, date, ip]
     * @return void
     * @Date Modify in 2020年12月08日  20:12:07
     * @Modify Content:
     **/
    public static void updateLoginTimeAndIP(String username, Date date, String ip) {
        dao.updateLoginTime(username,date,ip);
    }
    /**
     * @Author 李梓豪
     * @Description 登录
     * @Date 2020年12月08日  20:12:31
     * @Param [username, password]
     * @return boolean true表示成功，false表示失败
     * @Date Modify in 2020年12月08日  20:12:31
     * @Modify Content:
     **/
    public static boolean login(String username,String password){
        return dao.login(username,password);
    }

}
