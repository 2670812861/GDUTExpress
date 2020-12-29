package com.kaikeba.dao.implement;

import com.kaikeba.dao.BaseAdminDao;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-08 19:48
 */
public class AdminDaoMysql implements BaseAdminDao {

    private static final String SQL_UPDATE_LOGIN_TIME ="UPDATE EADMIN SET LOGINTIME=?,LOGINIP=? WHERE USERNAME=?" ;
    private static final String SQL_LOGIN = "select ID FROM EADMIN WHERE USERNAME=? AND PASSWORD=?";

    /**
     * @param username
     * @param date
     * @param ip
     * @return void
     * @Author 李梓豪
     * @Description 根据用户名，更新时间和登录ip
     * @Date 2020年12月08日  19:12:05
     * @Param [username]
     * @Date Modify in 2020年12月08日  19:12:05
     * @Modify Content:
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state=null;
        //2.预编译SQL语句
        try {
            //3.填充参数
            state  = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            state.setDate(1, new java.sql.Date(date.getTime()));
            state.setString(2,ip);
            state.setString(3,username);
            //4.执行
            state.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }


    }

    /**
     * @param username
     * @param password
     * @return 登录的结果，true表示登录成功
     * @Author 李梓豪
     * @Description 管理员根据帐号密码登录
     * @Date 2020年12月08日  19:12:45
     * @Param [username, password] ：[帐号，密码]
     * @Date Modify in 2020年12月08日  19:12:45
     * @Modify Content:
     */
    @Override
    public boolean login(String username, String password) {
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet rs = null;
        //2.预编译SQL语句
        try {
            state  = conn.prepareStatement(SQL_LOGIN);
            //3.填充参数
            state.setString(1,username);
            state.setString(2,password);
            //4.执行并获取结果
            rs = state.executeQuery();
            //5.根据查询结果，如果查询到有数据，则返回ture,如果没数据则返回false
            return rs.next();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            //6.释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
