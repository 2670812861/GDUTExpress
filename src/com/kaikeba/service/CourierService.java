package com.kaikeba.service;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.dao.implement.CourierDaoMysql;
import exception.DuplicateUserPhoneException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-14 18:33
 */
public class CourierService  {
    private static BaseCourierDao dao = new CourierDaoMysql();

    /**
     * @return {total_size:���Ա������increase_day:���տ��Աע�������}
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ������п��Ա�����տ��Աע�������
     * @Date 2020��12��12��  21:12:03
     * @Param []
     * @Date Modify in 2020��12��12��  21:12:03
     * @Modify Content:
     **/
    public static Map<String, Integer> console() {
        return dao.console();
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return ���Ա�ļ���
     * @Author ������
     * @Description ���ڲ�ѯ���п��Ա
     * @Date 2020��12��12��  21:12:24
     * @Param [limit, offset, pageNumber]
     * limit:�Ƿ��ҳ�ı��,true��ʾ��ҳ��false��ʾ��ѯ���п�ݡ�
     * offset��SQL������ʵ����
     * pageNumber��ҳ��ѯ������
     * @Date Modify in 2020��12��12��  21:12:24
     * @Modify Content:
     */

    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * @param id
     * @return com.kaikeba.bean.Courier
     * @Author ������
     * @Description ���ݿ��Ա��ţ���ѯ���Ա��Ϣ
     * @Date 2020��12��12��  22:12:27
     * @Param [number]
     * @Date Modify in 2020��12��12��  22:12:27
     * @Modify Content:
     */

    public static Courier findById(int id) {
        return dao.findById(id);
    }

    /**
     * @param userPhone
     * @return com.kaikeba.bean.Courier
     * @Author ������
     * @Description ���ݿ��Ա���ֻ����룬���ҿ��Ա��Ϣ
     * @Date 2020��12��12��  22:12:34
     * @Param [userPhone]
     * @Date Modify in 2020��12��12��  22:12:34
     * @Modify Content:
     */

    public static Courier findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * @param IdentityCard
     * @return com.kaikeba.bean.Courier
     * @Author ������
     * @Description ���ݿ��Ա�����֤�����ҿ��Ա��Ϣ
     * @Date 2020��12��12��  22:12:34
     * @Param [IdentityCard]
     * @Date Modify in 2020��12��12��  22:12:34
     * @Modify Content:
     */

    public static Courier findByIdentityCard(String IdentityCard) {
        return dao.findByIdentityCard(IdentityCard);
    }

    /**
     * @param c
     * @return boolean
     * @Author ������
     * @Description Ҫ¼��Ŀ��Ա��Ϣ
     * @Date 2020��12��12��  22:12:26
     * @Param [c]
     * @Date Modify in 2020��12��12��  22:12:26
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
     * @Author ������
     * @Description ���Ա���޸�
     * @Date 2020��12��12��  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020��12��12��  22:12:50
     * @Modify Content:
     * UPDATE COURIER ����=?,�ֻ�����=?,���֤=?,����=? WHERE USERPHONE=?
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
     * @Author ������
     * @Description ����id, �����ϴε�¼ʱ��
     * @Date 2020��12��12��  22:12:46
     * @Param [userPhone, date]
     * @Date Modify in 2020��12��12��  22:12:46
     * @Modify Content:
     */
    public static boolean updateLastLoginTime(Courier c, Date date) {
        return dao.updateLastLoginTime(c, date);
    }


    public static boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * @Author ������
     * @Description ����Ͷ����
     * @Date 2020��12��27��  11:12:28
     * @Param [sysPhone]
     * @return boolean
     * @Date Modify in 2020��12��27��  11:12:28
     * @Modify Content:
     **/
    public static boolean updateDeliveryNumber(String sysPhone){
        return dao.updateDeliveryNumber(sysPhone);
    }

    /**
     * @Author ������
     * @Description ��ȡͶ����
     * @Date 2020��12��27��  11:12:29
     * @Param [sysPhone]
     * @return int
     * @Date Modify in 2020��12��27��  11:12:29
     * @Modify Content:
     **/
    public static int getDeliveryNumber(String sysPhone){
        return dao.getDeliveryNumber(sysPhone);
    }
}
