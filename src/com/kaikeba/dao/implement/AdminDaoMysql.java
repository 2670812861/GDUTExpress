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
 * @Author: ������
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
     * @Author ������
     * @Description �����û���������ʱ��͵�¼ip
     * @Date 2020��12��08��  19:12:05
     * @Param [username]
     * @Date Modify in 2020��12��08��  19:12:05
     * @Modify Content:
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1.��ȡ����
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state=null;
        //2.Ԥ����SQL���
        try {
            //3.������
            state  = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            state.setDate(1, new java.sql.Date(date.getTime()));
            state.setString(2,ip);
            state.setString(3,username);
            //4.ִ��
            state.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            //5.�ͷ���Դ
            DruidUtil.close(conn,state,null);
        }


    }

    /**
     * @param username
     * @param password
     * @return ��¼�Ľ����true��ʾ��¼�ɹ�
     * @Author ������
     * @Description ����Ա�����ʺ������¼
     * @Date 2020��12��08��  19:12:45
     * @Param [username, password] ��[�ʺţ�����]
     * @Date Modify in 2020��12��08��  19:12:45
     * @Modify Content:
     */
    @Override
    public boolean login(String username, String password) {
        //1.��ȡ����
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet rs = null;
        //2.Ԥ����SQL���
        try {
            state  = conn.prepareStatement(SQL_LOGIN);
            //3.������
            state.setString(1,username);
            state.setString(2,password);
            //4.ִ�в���ȡ���
            rs = state.executeQuery();
            //5.���ݲ�ѯ����������ѯ�������ݣ��򷵻�ture,���û�����򷵻�false
            return rs.next();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            //6.�ͷ���Դ
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
