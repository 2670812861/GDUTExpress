package com.kaikeba.util;

import com.kaikeba.bean.User;
import com.kaikeba.service.CourierService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-11 13:19
 */
public class UserUtil {
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUsername");
    }


    public static String getAdminPhone(HttpSession session){
        String usrPhone = (String) session.getAttribute("userPhone");
        return usrPhone;
    }

    public static int getDeliveryNumber(HttpSession session){
        String sysPhone = (String) session.getAttribute("sysPhone");
        int count = CourierService.getDeliveryNumber(sysPhone);
        return count;
    }

    public static Timestamp getLastLoginTime(HttpSession session){
        Timestamp time1 = new Timestamp(System.currentTimeMillis());
        return time1;
    }

    public static String getLoginSMS(HttpSession session,String userPhone){
        return (String) session.getAttribute(userPhone);
    }

    public static void setLoginSMS(HttpSession session,String userPhone,String code){
        session.setAttribute(userPhone,code);
    }

    public static void setWXUser(HttpSession session, User user){
        session.setAttribute("wxUser",user);
    }

    public static User getWXUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }
}
