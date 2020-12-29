package com.kaikeba.dao.implement;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.util.DruidUtil;
import exception.DuplicateUserPhoneException;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-12 22:53
 */

public class CourierDaoMysql implements BaseCourierDao {
    //���ڲ�ѯ���ݿ��е�ȫ�����Ա�������Լ�ÿ��ע����
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(���) total_size," +
            "COUNT(TO_DAYS(ע��ʱ��)=TO_DAYS(NOW()) OR NULL) increase_day" +
            " FROM COURIER";
    //���ڲ�ѯ���ݿ��е����п��Ա��Ϣ
    public static final  String SQL_FIND_ALL = "SELECT * FROM COURIER";
    //���ڷ�ҳ��ѯ���ݿ��еĿ��Ա��Ϣ
    public static final String SQL_FIND_LIMIT ="SELECT * FROM COURIER LIMIT ?,?";
    //ͨ�����Ա��Ų�ѯ���Ա��Ϣ
    public static final String SQL_FIND_BY_ID ="SELECT * FROM COURIER WHERE ���=?";
    //ͨ�����Ա�ֻ������ѯ���Ա��Ϣ
    public static final String SQL_FIND_BY_USERPHONE ="SELECT * FROM COURIER WHERE �ֻ�����=?";
    //ͨ�����Ա���֤��ѯ���Ա��Ϣ
    public static final String SQL_FIND_BY_IDENTITYCARD ="SELECT * FROM COURIER WHERE ���֤=?";
    //¼����Ա��Ϣ
    public static final String SQL_INSERT ="INSERT INTO COURIER(����,�ֻ�����,���֤,����,�ɼ���,ע��ʱ��,�ϴε�¼ʱ��) VALUES(?,?,?,?,?,NOW(),?)";
    //���Ա��Ϣ�޸�
    public static final String SQL_UPDATE ="UPDATE COURIER SET ����=?,�ֻ�����=?,���֤=?,����=?,�ɼ���=?,�ϴε�¼ʱ��=? WHERE ���=?";
    //���Ա�ϴε�¼ʱ��ı�
    public static final String SQL_UPDATELASTLOGINTIME = "UPDATE COURIER �ϴε�¼ʱ��=? WHERE �ֻ�����=?";
    //���Ա��ɾ��
    public static final String SQL_DELETE ="DELETE FROM COURIER WHERE ���=?";
    //��ѯͶ����
    public static final String SQL_GETDELIVERYNUMBER = "SELECT  COUNT(ID) DeliveryNumber FROM EXPRESS WHERE SYSPHONE=?";
    //����Ͷ����
    public static final String SQL_UPDATE_DELIVERYNUMBER = "UPDATE COURIER SET �ɼ���=((SELECT  COUNT(ID) FROM EXPRESS WHERE SYSPHONE=?)+1) WHERE �ֻ�����=?";

    /**
     * @return {total_size:���Ա������increase_day:���տ��Աע�������}
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ������п��Ա�����տ��Աע�������
     * @Date 2020��12��12��  21:12:03
     * @Param []
     * @Date Modify in 2020��12��12��  21:12:03
     * @Modify Content:
     **/
    @Override
    public Map<String,Integer> console() {
        Map data = new HashMap();
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        try {
            //2.Ԥ����SQL���
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.������(��ѡ)
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�н��
            if(result.next()){
                int total_size = result.getInt("total_size");
                int increase_day = result.getInt("increase_day");

                data.put("total_size",total_size);
                data.put("increase_day",increase_day);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }

        return data;
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
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data =new ArrayList<>();
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            //�Ƿ��ҳ
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.������(��ѡ)
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }
            else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }

            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            while (result.next()){
                int id = result.getInt("���");
                String username = result.getString("����");
                String userPhone = result.getString("�ֻ�����");
                String IdentityCard = result.getString("���֤");
                String password = result.getString("����");
                int DeliveryNumber = result.getInt("�ɼ���");
                Timestamp RegistrationTime = result.getTimestamp("ע��ʱ��");
                Timestamp LastLoginTime = result.getTimestamp("�ϴε�¼ʱ��");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                data.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }
        return data;
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
    @Override
    public Courier findById(int id) {
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_ID);
            //3.������(��ѡ)
            state.setInt(1,id);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            if (result.next()){
                String username = result.getString("����");
                String userPhone = result.getString("�ֻ�����");
                String IdentityCard = result.getString("���֤");
                String password = result.getString("����");
                int DeliveryNumber = result.getInt("�ɼ���");
                Timestamp RegistrationTime = result.getTimestamp("ע��ʱ��");
                Timestamp LastLoginTime = result.getTimestamp("�ϴε�¼ʱ��");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                return c;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }
        return null;
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
    @Override
    public Courier findByUserPhone(String userPhone) {
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.������(��ѡ)
            state.setString(1,userPhone);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            while (result.next()){
                int id = result.getInt("���");
                String username = result.getString("����");
                String IdentityCard = result.getString("���֤");
                String password = result.getString("����");
                int DeliveryNumber = result.getInt("�ɼ���");
                Timestamp RegistrationTime = result.getTimestamp("ע��ʱ��");
                Timestamp LastLoginTime = result.getTimestamp("�ϴε�¼ʱ��");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }
        return null;
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
    @Override
    public Courier findByIdentityCard(String IdentityCard) {
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_IDENTITYCARD);
            //3.������(��ѡ)
            state.setString(1,IdentityCard);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            while (result.next()){
                int id = result.getInt("���");
                String username = result.getString("����");
                String userPhone = result.getString("�ֻ�����");
                String password = result.getString("����");
                int DeliveryNumber = result.getInt("�ɼ���");
                Timestamp RegistrationTime = result.getTimestamp("ע��ʱ��");
                Timestamp LastLoginTime = result.getTimestamp("�ϴε�¼ʱ��");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * @Author ������
     * @Description ��ȡͶ����
     * @Date 2020��12��27��  11:12:59
     * @Param [sysPhone]
     * @return int
     * @Date Modify in 2020��12��27��  11:12:59
     * @Modify Content:
     **/
    @Override
    public int getDeliveryNumber(String sysPhone) {
        //1.��ȡ���ݿ������
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        int deliveryNumber=0;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_GETDELIVERYNUMBER);
            //3.������(��ѡ)
            state.setString(1, sysPhone);
            //4.ִ��SQL���
            result = state.executeQuery();
            //5.��ȡִ�еĽ��
            if (result.next()) {
                deliveryNumber =result.getInt("DeliveryNumber");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }
        return deliveryNumber;
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
    @Override
    public boolean insert(Courier c) throws DuplicateUserPhoneException {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.������
            state.setString(1,c.getUsername());
            state.setString(2,c.getUserPhone());
            state.setString(3,c.getIdentityCard());
            state.setString(4,c.getPassword());
            state.setInt(5,c.getDeliveryNumber());
            state.setTimestamp(6,c.getLastLoginTime());
            //4.ִ��SQL��䣬����ȡִ�н��
            return  state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            // �������ֻ������ظ�����ô�������ʧ�ܵ�ԭ���ֻ�����֤�����ظ���
            if (e1.getMessage().endsWith("for key '�ֻ�����'")){
                //����Ϊ�ֻ����ظ�,���������쳣
                DuplicateUserPhoneException e2 = new DuplicateUserPhoneException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.�ͷ���Դ
            DruidUtil.close(conn,state,null);
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
    @Override
    public boolean update(int id, Courier newCourier) throws DuplicateUserPhoneException {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            //3.������
            state.setString(1,newCourier.getUsername());
            state.setString(2 ,newCourier.getUserPhone());
            state.setString(3,newCourier.getIdentityCard());
            state.setString(4,newCourier.getPassword());
            state.setInt(5,newCourier.getDeliveryNumber());
            state.setTimestamp(6,newCourier.getLastLoginTime());
            state.setInt(7,newCourier.getId());
            //4.ִ��SQL��䣬����ȡִ�н��
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            // �������ֻ������ظ�����ô�������ʧ�ܵ�ԭ���ֻ�����֤�����ظ���
            if (e1.getMessage().endsWith("for key '�ֻ�����'")){
                //����Ϊ�ֻ����ظ�,���������쳣
                DuplicateUserPhoneException e2 = new DuplicateUserPhoneException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.�ͷ���Դ
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * @Author ������
     * @Description ����Ͷ����
     * @Date 2020��12��27��  09:12:03
     * @Param [sysPhone]
     * @return boolean
     * @Date Modify in 2020��12��27��  09:12:03
     * @Modify Content:
     **/
    @Override
    public boolean updateDeliveryNumber(String sysPhone){
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        ResultSet result = null;
        int count=0;
        try {
            state = conn.prepareStatement(SQL_UPDATE_DELIVERYNUMBER);
            //3.������
            state.setString(1,sysPhone);
            state.setString(2,sysPhone);
            //4.ִ��SQL��䣬����ȡִ�н��
            //4.ִ��SQL��䣬����ȡִ�н��
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.�ͷ���Դ
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * @param c
     * @param date
     * @return boolean
     * @Author ������
     * @Description ����id, �����ϴε�¼ʱ��
     * @Date 2020��12��12��  22:12:46
     * @Param [userPhone, date]
     * @Date Modify in 2020��12��12��  22:12:46
     * @Modify Content:
     */
    @Override
    public boolean updateLastLoginTime(Courier c, Date date) {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATELASTLOGINTIME);
            //3.������
            //������ֱ�Ӵ�data�����ǵ�data��util���µģ��˷�����Ҫsql���µ�data���룬�����sql��data���췽��
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,c.getUserPhone());
            //4.ִ��SQL��䣬����ȡִ�н��
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.�ͷ���Դ
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            //3.������
            state.setInt(1,id);
            //4.ִ��SQL��䣬����ȡִ�н��
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.�ͷ���Դ
            DruidUtil.close(conn,state,null);
        }
        return false;
    }


}
