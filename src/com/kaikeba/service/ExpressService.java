package com.kaikeba.service;

import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.dao.implement.ExpressDaoMysql;
import com.kaikeba.util.RandomUtil;
import com.kaikeba.util.SMSUtil;
import exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-10 22:48
 */
public class ExpressService  {
    private static BaseExpressDao dao =  new ExpressDaoMysql();
    /**
     * @return [{size:������day:����},{size:������day:����}]
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ��е�ȫ����ݣ�����+����������ȡ����ݣ�����+������
     * @Date 2020��12��10��  08:12:53
     * @Param []
     * @Date Modify in 2020��12��10��  08:12:53
     * @Modify Content:
     **/
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return ��ݵļ���
     * @Author ������
     * @Description ���ڲ�ѯ���п��
     * @Date 2020��12��10��  08:12:17
     * @Param [limit, offset, pageNumber]
     * limit:�Ƿ��ҳ�ı��,true��ʾ��ҳ��false��ʾ��ѯ���п�ݡ�
     * offset��SQL������ʵ����
     * pageNumber��ҳ��ѯ������
     * @Date Modify in 2020��12��10��  08:12:17
     * @Modify Content:
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * @param number
     * @return ��ѯ�Ŀ����Ϣ�����Ų�����ʱ����null
     * @Author ������
     * @Description ���ݿ�ݷ��ţ���ѯ�����Ϣ
     * @Date 2020��12��10��  08:12:13
     * @Param [number]������
     * @Date Modify in 2020��12��10��  08:12:13
     * @Modify Content:
     */

    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * @param code
     * @return ��ѯ�Ŀ����Ϣ��ȡ���벻����ʱ����null
     * @Author ������
     * @Description ���ݿ��ȡ���룬��ѯ�����Ϣ
     * @Date 2020��12��10��  08:12:31
     * @Param [code] ȡ����
     * @Date Modify in 2020��12��10��  08:12:31
     * @Modify Content:
     */

    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * @param userPhone
     * @return ��ѯ�Ŀ����Ϣ�б�
     * @Author ������
     * @Description �����û��ֻ����룬��ѯ�����еĿ����Ϣ
     * @Date 2020��12��10��  09:12:01
     * @Param [phoneNumber] �ֻ�����
     * @Date Modify in 2020��12��10��  09:12:01
     * @Modify Content:
     */
    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * @Author ������
     * @Description
     * @Date 2020��12��19��  14:12:01
     * @Param [userPhone, status] �ֻ���,״̬��
     * @return ����б�
     * @Date Modify in 2020��12��19��  14:12:01
     * @Modify Content:
     **/
    public static List<Express> findByUserPhoneAndStatus(String userPhone,int status) {
        return dao.findByUserPhoneAndStatus(userPhone,status);
    }

    /**
     * @param sysPhone
     * @return ��ѯ�Ŀ����Ϣ�б�
     * @Author ������
     * @Description ����¼�����ֻ����룬��ѯ¼������м�¼
     * @Date 2020��12��10��  09:12:57
     * @Param [SysPhone]���ֻ�����
     * @Date Modify in 2020��12��10��  09:12:57
     * @Modify Content:
     */
    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * @param e
     * @return boolean ��¼��Ľ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description Ҫ¼��Ŀ�ݶ���
     * @Date 2020��12��10��  09:12:13
     * @Param [e]:Ҫ¼��Ŀ�ݶ���
     * @Date Modify in 2020��12��10��  09:12:13
     * @Modify Content:
     */

    public static boolean insert(Express e)  {
        //1.����ȡ����
         e.setCode(RandomUtil.get()+"");
        try {
            boolean flag =dao.insert(e);
            if (flag){
                //¼��ɹ������Ͷ���
                SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            //��Ϊȡ�����ظ��ˣ�����ʹ�õݹ����ٴ��������ȡ����
            return insert(e);
        }
    }

    /**
     * @param id
     * @param newExpress
     * @return boolean �޸ĵĽ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ��ݵ��޸�
     * @Date 2020��12��10��  09:12:48
     * @Param [id, newExpress] id:Ҫ�޸ĵĿ��id  newExpress:�µĿ�ݶ���(number,company,username,userPhone)
     * @Date Modify in 2020��12��10��  09:12:48
     * @Modify Content:
     *
     */
    public static boolean update(int id, Express newExpress) {
        if (newExpress.getUserPhone()!=null){
            //1.�������޸ģ���ֻ�޸��ֻ��š���Ҫ��ɾ����ݣ������²����µĿ��
            // (��ע�⣬��ΪҪ����ȡ���룬��˵��õ��Ǳ�service���insert������dao���insert)
            dao.delete(id);
            return insert(newExpress);
        }else {
            boolean update = dao.update(id,newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            //��Ϊǰ�˿��Ը���ȡ��״̬��������������ȡ��״̬��Ҫ���¸���ȡ��ʱ�����Ϣ
            if (newExpress.getStatus()==1){
                //δȡ��״̬��Ϊȡ��״̬
                updateStatus(e.getCode());
            }else{
                // TODO: 2020/12/14 0014 ��sysphone������  �ѽ������controller���update��������newExpress.setSysPhone(UserUtil.getAdminPhone(request.getSession()));
                Express e1 = dao.findByNumber(newExpress.getNumber());
                newExpress.setUserPhone(e1.getUserPhone());
                newExpress.setSysPhone(e1.getSysPhone());
                dao.delete(id);
                return insert(newExpress);
            }
            return update;
        }
    }

    /**
     * @param code
     * @return boolean  �޸ĵĽ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ���Ŀ�ݵ�״̬Ϊ1, ��ʾȡ�����
     * @Date 2020��12��10��  09:12:45
     * @Param [code]:Ҫ�޸ĵĿ��ȡ����
     * @Date Modify in 2020��12��10��  09:12:45
     * @Modify Content:ng
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * @Author ������
     * @Description ����Ͷ����
     * @Date 2020��12��27��  11:12:26
     * @Param [number]
     * @return boolean
     * @Date Modify in 2020��12��27��  11:12:26
     * @Modify Content:
     **/
    public static boolean updateStatusByExpressNum(String number){
        return dao.updateStatusByExpressNum(number);
    }

    /**
     * @param id
     * @return boolean ɾ���Ľ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ����id, ɾ�����������Ϣ
     * @Date 2020��12��10��  09:12:53
     * @Param [id] Ҫɾ���Ŀ��id
     * @Date Modify in 2020��12��10��  09:12:53
     * @Modify Content:
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
