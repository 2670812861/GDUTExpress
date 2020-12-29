package com.kaikeba.service;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.dao.implement.CourierDaoMysql;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-14 18:33
 */
public class CourierService  {
    private static BaseCourierDao dao = new CourierDaoMysql();

    /**
     * @return {total_size:快递员总数，increase_day:当日快递员注册的数量}
     * @Author 李梓豪
     * @Description 用于查询数据库中所有快递员，当日快递员注册的数量
     * @Date 2020年12月12日  21:12:03
     * @Param []
     * @Date Modify in 2020年12月12日  21:12:03
     * @Modify Content:
     **/
    public static Map<String, Integer> console() {
        return dao.console();
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return 快递员的集合
     * @Author 李梓豪
     * @Description 用于查询所有快递员
     * @Date 2020年12月12日  21:12:24
     * @Param [limit, offset, pageNumber]
     * limit:是否分页的标记,true表示分页，false表示查询所有快递。
     * offset：SQL语句的其实索引
     * pageNumber：页查询的数量
     * @Date Modify in 2020年12月12日  21:12:24
     * @Modify Content:
     */

    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * @param id
     * @return com.kaikeba.bean.Courier
     * @Author 李梓豪
     * @Description 根据快递员编号，查询快递员信息
     * @Date 2020年12月12日  22:12:27
     * @Param [number]
     * @Date Modify in 2020年12月12日  22:12:27
     * @Modify Content:
     */

    public static Courier findById(int id) {
        return dao.findById(id);
    }

    /**
     * @param userPhone
     * @return com.kaikeba.bean.Courier
     * @Author 李梓豪
     * @Description 根据快递员的手机号码，查找快递员信息
     * @Date 2020年12月12日  22:12:34
     * @Param [userPhone]
     * @Date Modify in 2020年12月12日  22:12:34
     * @Modify Content:
     */

    public static Courier findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * @param IdentityCard
     * @return com.kaikeba.bean.Courier
     * @Author 李梓豪
     * @Description 根据快递员的身份证，查找快递员信息
     * @Date 2020年12月12日  22:12:34
     * @Param [IdentityCard]
     * @Date Modify in 2020年12月12日  22:12:34
     * @Modify Content:
     */

    public static Courier findByIdentityCard(String IdentityCard) {
        return dao.findByIdentityCard(IdentityCard);
    }

    /**
     * @param c
     * @return boolean
     * @Author 李梓豪
     * @Description 要录入的快递员信息
     * @Date 2020年12月12日  22:12:26
     * @Param [c]
     * @Date Modify in 2020年12月12日  22:12:26
     * @Modify Content:
     */

    public static boolean insert(Courier c) {
        try {
            boolean flag = dao.insert(c);
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
     * @param newCourier
     * @return boolean
     * @Author 李梓豪
     * @Description 快递员的修改
     * @Date 2020年12月12日  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020年12月12日  22:12:50
     * @Modify Content:
     * UPDATE COURIER 姓名=?,手机号码=?,身份证=?,密码=? WHERE USERPHONE=?
     */

    public static boolean update(int id, Courier newCourier) {
        try {
            boolean flag = dao.update(id, newCourier);
            if (flag){
                return true;
            }
        } catch (DuplicateUserPhoneException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param  c
     * @param date
     * @return boolean
     * @Author 李梓豪
     * @Description 根据id, 更新上次登录时间
     * @Date 2020年12月12日  22:12:46
     * @Param [userPhone, date]
     * @Date Modify in 2020年12月12日  22:12:46
     * @Modify Content:
     */
    public static boolean updateLastLoginTime(Courier c, Date date) {
        return dao.updateLastLoginTime(c, date);
    }


    public static boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * @Author 李梓豪
     * @Description 更新投递数
     * @Date 2020年12月27日  11:12:28
     * @Param [sysPhone]
     * @return boolean
     * @Date Modify in 2020年12月27日  11:12:28
     * @Modify Content:
     **/
    public static boolean updateDeliveryNumber(String sysPhone){
        return dao.updateDeliveryNumber(sysPhone);
    }

    /**
     * @Author 李梓豪
     * @Description 获取投递数
     * @Date 2020年12月27日  11:12:29
     * @Param [sysPhone]
     * @return int
     * @Date Modify in 2020年12月27日  11:12:29
     * @Modify Content:
     **/
    public static int getDeliveryNumber(String sysPhone){
        return dao.getDeliveryNumber(sysPhone);
    }
}
