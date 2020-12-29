package com.kaikeba.service;

import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.dao.implement.UserDaoMysql;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-17 17:02
 */
public class UserService  {
    private static BaseUserDao dao = new UserDaoMysql();
    /**
     * @return {total_size:用户总数，increase_day:当日用户注册的数量}
     * @Author 李梓豪
     * @Description 用于查询数据库中所有用户，当日用户注册的数量
     * @Date 2020年12月13日  10:12:55
     * @Param []
     * @Date Modify in 2020年12月13日  10:12:55
     * @Modify Content:
     **/
    public static Map<String, Integer> console() {
        return dao.console();
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return 用户的集合
     * @Author 李梓豪
     * @Description 用于查询所有用户
     * @Date 2020年12月13日  10:12:38
     * @Param [limit, offset, pageNumber]
     * @Date Modify in 2020年12月13日  10:12:38
     * @Modify Content:
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * @param id
     * @return com.kaikeba.bean.User
     * @Author 李梓豪
     * @Description 根据用户编号，查询用户信息
     * @Date 2020年12月12日  22:12:27
     * @Param [number]
     * @Date Modify in 2020年12月12日  22:12:27
     * @Modify Content:
     */
    public static User findById(int id) {
        return dao.findById(id);
    }

    /**
     * @param userPhone
     * @return com.kaikeba.bean.User
     * @Author 李梓豪
     * @Description 根据用户的手机号码，查找用户信息
     * @Date 2020年12月13日  10:12:21
     * @Param [userPhone]
     * @Date Modify in 2020年12月13日  10:12:21
     * @Modify Content:
     */
    public static User findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * @param u
     * @return boolean
     * @Author 李梓豪
     * @Description 要录入的用户信息
     * @Date 2020年12月12日  22:12:26
     * @Param [c]
     * @Date Modify in 2020年12月12日  22:12:26
     * @Modify Content:
     */
    public static boolean insert(User u)  {
        try {
            boolean flag = dao.insert(u);
            if (flag){
                return true;
            }
        } catch (DuplicateUserPhoneException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param id
     * @param newUser
     * @return boolean
     * @Author 李梓豪
     * @Description 用户的修改
     * @Date 2020年12月12日  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020年12月12日  22:12:50
     * @Modify Content:
     */
    public static boolean update(int id, User newUser) {
        try {
            boolean flag = dao.update(id, newUser);
            if (flag){
                return true;
            }
        } catch (DuplicateUserPhoneException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param u
     * @param date
     * @return boolean
     * @Author 李梓豪
     * @Description 根据id, 更新上次登录时间
     * @Date 2020年12月12日  22:12:46
     * @Param [userPhone, date]
     * @Date Modify in 2020年12月12日  22:12:46
     * @Modify Content:
     */
    public boolean updateLastLoginTime(User u, Date date) {
        return dao.updateLastLoginTime(u, date);
    }

    /**
     * @param id
     * @return boolean
     * @Author 李梓豪
     * @Description 根据id, 删除用户信息
     * @Date 2020年12月17日  16:12:00
     * @Param [id]
     * @Date Modify in 2020年12月17日  16:12:00
     * @Modify Content:
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
