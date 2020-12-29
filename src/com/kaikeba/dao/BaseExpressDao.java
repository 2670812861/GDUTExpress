package com.kaikeba.dao;

import com.kaikeba.bean.Express;
import exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-10 8:45
 */
public interface BaseExpressDao {
    /**
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ��е�ȫ����ݣ�����+����������ȡ����ݣ�����+������
     * @Date 2020��12��10��  08:12:53
     * @Param []
     * @return [{size:������day:����},{size:������day:����}]
     * @Date Modify in 2020��12��10��  08:12:53
     * @Modify Content:
     **/
    List<Map<String,Integer>> console();

    /**
     * @Author ������
     * @Description ���ڲ�ѯ���п��
     * @Date 2020��12��10��  08:12:17
     * @Param [limit, offset, pageNumber]
     * limit:�Ƿ��ҳ�ı��,true��ʾ��ҳ��false��ʾ��ѯ���п�ݡ�
     * offset��SQL������ʵ����
     * pageNumber��ҳ��ѯ������
     * @return ��ݵļ���
     * @Date Modify in 2020��12��10��  08:12:17
     * @Modify Content:
     **/
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * @Author ������
     * @Description ���ݿ�ݵ��ţ���ѯ�����Ϣ
     * @Date 2020��12��10��  08:12:13
     * @Param [number]������
     * @return ��ѯ�Ŀ����Ϣ�����Ų�����ʱ����null
     * @Date Modify in 2020��12��10��  08:12:13
     * @Modify Content:
     **/
    Express findByNumber(String number);

    /**
     * @Author ������
     * @Description ���ݿ��ȡ���룬��ѯ�����Ϣ
     * @Date 2020��12��10��  08:12:31
     * @Param [code] ȡ����
     * @return ��ѯ�Ŀ����Ϣ��ȡ���벻����ʱ����null
     * @Date Modify in 2020��12��10��  08:12:31
     * @Modify Content:
     **/
    Express findByCode(String code);

    /**
     * @Author ������
     * @Description �����û��ֻ����룬��ѯ�����еĿ����Ϣ
     * @Date 2020��12��10��  09:12:01
     * @Param [phoneNumber] �ֻ�����
     * @return ��ѯ�Ŀ����Ϣ�б�
     * @Date Modify in 2020��12��10��  09:12:01
     * @Modify Content:
     **/
    List<Express> findByUserPhone(String userPhone);

    /**
     * @Author ������
     * @Description ͨ���绰�����ȡ��״̬����ѯ��ݣ�������ǩ�ջ�δǩ�յĿ���б�
     * @Date 2020��12��19��  13:12:27
     * @Param [userPhone]
     * @return ������ǩ�ջ�δǩ�յĿ���б�
     * @Date Modify in 2020��12��19��  13:12:27
     * @Modify Content:
     **/
    List<Express> findByUserPhoneAndStatus(String userPhone,int status);
    /**
     * @Author ������
     * @Description ����¼�����ֻ����룬��ѯ¼������м�¼
     * @Date 2020��12��10��  09:12:57
     * @Param [SysPhone]���ֻ�����
     * @return ��ѯ�Ŀ����Ϣ�б�
     * @Date Modify in 2020��12��10��  09:12:57
     * @Modify Content:
     **/
    List<Express> findBySysPhone(String sysPhone);

    /**
     * @Author ������
     * @Description Ҫ¼��Ŀ�ݶ���
     * @Date 2020��12��10��  09:12:13
     * @Param [e]:Ҫ¼��Ŀ�ݶ���
     * @return boolean ��¼��Ľ����true��ʾ�ɹ���false��ʾʧ��
     * @Date Modify in 2020��12��10��  09:12:13
     * @Modify Content:
     **/
    boolean insert(Express e) throws DuplicateCodeException;

    /**
     * @Author ������
     * @Description ��ݵ��޸�
     * @Date 2020��12��10��  09:12:48
     * @Param [id, newExpress] id:Ҫ�޸ĵĿ��id  newExpress:�µĿ�ݶ���(number,company,username,userPhone)
     * @return boolean �޸ĵĽ����true��ʾ�ɹ���false��ʾʧ��
     * @Date Modify in 2020��12��10��  09:12:48
     * @Modify Content:
     **/
    boolean update(int id,Express newExpress);

   /**
    * @Author ������
    * @Description ���Ŀ�ݵ�״̬Ϊ1,��ʾȡ�����
    * @Date 2020��12��10��  09:12:45
    * @Param [code]:Ҫ�޸ĵĿ��ȡ����
    * @return boolean  �޸ĵĽ����true��ʾ�ɹ���false��ʾʧ��
    * @Date Modify in 2020��12��10��  09:12:45
    * @Modify Content:ng
    **/
    boolean updateStatus(String code);

    /**
     * @Author ������
     * @Description ����id,ɾ�����������Ϣ
     * @Date 2020��12��10��  09:12:53
     * @Param [id] Ҫɾ���Ŀ��id
     * @return boolean ɾ���Ľ����true��ʾ�ɹ���false��ʾʧ��
     * @Date Modify in 2020��12��10��  09:12:53
     * @Modify Content:
     **/
    boolean delete(int id);


    boolean updateStatusByExpressNum(String number);
}
