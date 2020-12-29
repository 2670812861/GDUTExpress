package com.kaikeba.service;

import com.kaikeba.dao.BaseAdminDao;
import com.kaikeba.dao.implement.AdminDaoMysql;

import java.util.Date;


/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-08 20:38
 */
public class AdminService {

    private static BaseAdminDao dao = new AdminDaoMysql();
    /**
     * @Author ������
     * @Description ���µ�¼ʱ����ip
     * @Date 2020��12��08��  20:12:07
     * @Param [username, date, ip]
     * @return void
     * @Date Modify in 2020��12��08��  20:12:07
     * @Modify Content:
     **/
    public static void updateLoginTimeAndIP(String username, Date date, String ip) {
        dao.updateLoginTime(username,date,ip);
    }
    /**
     * @Author ������
     * @Description ��¼
     * @Date 2020��12��08��  20:12:31
     * @Param [username, password]
     * @return boolean true��ʾ�ɹ���false��ʾʧ��
     * @Date Modify in 2020��12��08��  20:12:31
     * @Modify Content:
     **/
    public static boolean login(String username,String password){
        return dao.login(username,password);
    }

}
