package com.kaikeba.dao;

import java.util.Date;

/**
 * @Author: ������
 * @Description:���ڶ���eadmin���Ĳ����淶
 * @Date Created in 2020-12-08 19:25
 */
public interface BaseAdminDao {
    /**
     * @Author ������
     * @Description �����û���������ʱ��͵�¼ip
     * @Date 2020��12��08��  19:12:05
     * @Param [username]
     * @return void
     * @Date Modify in 2020��12��08��  19:12:05
     * @Modify Content:
     **/
    void updateLoginTime(String username, Date date, String ip);
    /**
     * @Author ������
     * @Description ����Ա�����ʺ������¼
     * @Date 2020��12��08��  19:12:45
     * @Param [username, password] ��[�ʺţ�����]
     * @return ��¼�Ľ����true��ʾ��¼�ɹ�
     * @Date Modify in 2020��12��08��  19:12:45
     * @Modify Content:
     **/
    boolean login(String username,String password);
}
