package com.kaikeba.service;

import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.dao.implement.UserDaoMysql;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-17 17:02
 */
public class UserService  {
    private static BaseUserDao dao = new UserDaoMysql();
    /**
     * @return {total_size:�û�������increase_day:�����û�ע�������}
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ��������û��������û�ע�������
     * @Date 2020��12��13��  10:12:55
     * @Param []
     * @Date Modify in 2020��12��13��  10:12:55
     * @Modify Content:
     **/
    public static Map<String, Integer> console() {
        return dao.console();
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return �û��ļ���
     * @Author ������
     * @Description ���ڲ�ѯ�����û�
     * @Date 2020��12��13��  10:12:38
     * @Param [limit, offset, pageNumber]
     * @Date Modify in 2020��12��13��  10:12:38
     * @Modify Content:
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * @param id
     * @return com.kaikeba.bean.User
     * @Author ������
     * @Description �����û���ţ���ѯ�û���Ϣ
     * @Date 2020��12��12��  22:12:27
     * @Param [number]
     * @Date Modify in 2020��12��12��  22:12:27
     * @Modify Content:
     */
    public static User findById(int id) {
        return dao.findById(id);
    }

    /**
     * @param userPhone
     * @return com.kaikeba.bean.User
     * @Author ������
     * @Description �����û����ֻ����룬�����û���Ϣ
     * @Date 2020��12��13��  10:12:21
     * @Param [userPhone]
     * @Date Modify in 2020��12��13��  10:12:21
     * @Modify Content:
     */
    public static User findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * @param u
     * @return boolean
     * @Author ������
     * @Description Ҫ¼����û���Ϣ
     * @Date 2020��12��12��  22:12:26
     * @Param [c]
     * @Date Modify in 2020��12��12��  22:12:26
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
     * @Author ������
     * @Description �û����޸�
     * @Date 2020��12��12��  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020��12��12��  22:12:50
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
     * @Author ������
     * @Description ����id, �����ϴε�¼ʱ��
     * @Date 2020��12��12��  22:12:46
     * @Param [userPhone, date]
     * @Date Modify in 2020��12��12��  22:12:46
     * @Modify Content:
     */
    public boolean updateLastLoginTime(User u, Date date) {
        return dao.updateLastLoginTime(u, date);
    }

    /**
     * @param id
     * @return boolean
     * @Author ������
     * @Description ����id, ɾ���û���Ϣ
     * @Date 2020��12��17��  16:12:00
     * @Param [id]
     * @Date Modify in 2020��12��17��  16:12:00
     * @Modify Content:
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
