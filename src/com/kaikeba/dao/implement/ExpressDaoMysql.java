package com.kaikeba.dao.implement;

import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.util.DruidUtil;
import com.kaikeba.util.UserUtil;
import exception.DuplicateCodeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-10 9:24
 */
public class ExpressDaoMysql implements BaseExpressDao {
    //���ڲ�ѯ���ݿ��е�ȫ����ݣ�����+����������ȡ����ݣ�����+������
    public static final String SQL_CONSOLE ="SELECT " +
            "COUNT(ID) data1_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) data1_day," +
            "COUNT(STATUS=0 OR NULL) data2_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day" +
            " FROM EXPRESS";
    //���ڲ�ѯ���ݿ��е����п����Ϣ
    public static final String SQL_FIND_ALL ="SELECT * FROM EXPRESS";
    //���ڷ�ҳ��ѯ���ݿ��еĿ����Ϣ
    public static final String SQL_FIND_LIMIT ="SELECT * FROM EXPRESS LIMIT ?,?";
    //ͨ��ȡ�����ѯ�����Ϣ
    public static final String SQL_FIND_BY_CODE ="SELECT * FROM EXPRESS WHERE CODE=?";
    //ͨ����ݵ��Ų�ѯ�����Ϣ
    public static final String SQL_FIND_BY_NUMBER ="SELECT * FROM EXPRESS WHERE NUMBER=?";
    //ͨ��¼�����ֻ��Ų�ѯ�����Ϣ
    public static final String SQL_FIND_BY_SYSPHONE ="SELECT * FROM EXPRESS WHERE SYSPHONE=?";
    //ͨ���û��ֻ��Ų�ѯ�û����п��
    public static final String SQL_FIND_BY_USERPHONE ="SELECT * FROM EXPRESS WHERE USERPHONE=?";
    //ͨ���û��ֻ��Ų�ѯ�û����п��
    public static final String SQL_FIND_BY_USERPHONE_AND_STATUS ="SELECT * FROM EXPRESS WHERE USERPHONE=? AND STATUS=? ";
    //¼����
    public static final String SQL_INSERT ="INSERT INTO EXPRESS(NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    //����޸�
    public static final String SQL_UPDATE ="UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    //��ݵ�״̬��ı䣨ȡ����
    public static final String SQL_UPDATE_STATUS ="UPDATE EXPRESS SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE CODE=?";
    public static final String SQL_UPDATE_STATUS_By_Number ="UPDATE EXPRESS SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE NUMBER=?";
    //��ݵ�ɾ��
    public static final String SQL_DELETE ="DELETE FROM EXPRESS WHERE ID=?";


    /**
     * @return [{size:������day:����},{size:������day:����}]
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ��е�ȫ����ݣ�����+����������ȡ����ݣ�����+������
     * @Date 2020��12��10��  08:12:53
     * @Param []
     * @Date Modify in 2020��12��10��  08:12:53
     * @Modify Content:
     **/
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String,Integer>> data = new ArrayList<>();
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.������(��ѡ)
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            if (result.next()){
                int data1_size = result.getInt("data1_size");
                int data1_day = result.getInt("data1_day");
                int data2_size = result.getInt("data2_size");
                int data2_day = result.getInt("data2_day");

                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);

                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);

                data.add(data1);
                data.add(data2);
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
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data =new ArrayList<>();
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
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userPhone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                int status  = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
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
     * @param number
     * @return ��ѯ�Ŀ����Ϣ�����Ų�����ʱ����null
     * @Author ������
     * @Description ���ݿ�ݷ��ţ���ѯ�����Ϣ
     * @Date 2020��12��10��  08:12:13
     * @Param [number]������
     * @Date Modify in 2020��12��10��  08:12:13
     * @Modify Content:
     */
    @Override
    public Express findByNumber(String number) {
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            //3.������(��ѡ)
            state.setString(1,number);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            if (result.next()){
                int id = result.getInt("id");
                String username = result.getString("username");
                String userPhone = result.getString("userPhone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                int status  = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
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
     * @param code
     * @return ��ѯ�Ŀ����Ϣ��ȡ���벻����ʱ����null
     * @Author ������
     * @Description ���ݿ��ȡ���룬��ѯ�����Ϣ
     * @Date 2020��12��10��  08:12:31
     * @Param [code] ȡ����
     * @Date Modify in 2020��12��10��  08:12:31
     * @Modify Content:
     */
    @Override
    public Express findByCode(String code) {

        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_CODE);
            //3.������(��ѡ)
            state.setString(1,code);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            if (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userPhone");
                String company = result.getString("company");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                int status  = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
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
     * @return ��ѯ�Ŀ����Ϣ�б�
     * @Author ������
     * @Description �����û��ֻ����룬��ѯ�����еĿ����Ϣ
     * @Date 2020��12��10��  09:12:01
     * @Param [phoneNumber] �ֻ�����
     * @Date Modify in 2020��12��10��  09:12:01
     * @Modify Content:
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> data = new ArrayList<>();
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
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                int status  = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
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
     * @param userPhone
     * @param status
     * @return ������ǩ�ջ�δǩ�յĿ���б�
     * @Author ������
     * @Description ͨ���绰�����ȡ��״̬����ѯ��ݣ�������ǩ�ջ�δǩ�յĿ���б�
     * @Date 2020��12��19��  13:12:27
     * @Param [userPhone]
     * @Date Modify in 2020��12��19��  13:12:27
     * @Modify Content:
     */
    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        ArrayList<Express> data = new ArrayList<>();
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE_AND_STATUS);
            //3.������(��ѡ)
            state.setString(1,userPhone);
            state.setInt(2,status);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            while (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
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
     * @param sysPhone
     * @return ��ѯ�Ŀ����Ϣ�б�
     * @Author ������
     * @Description ����¼�����ֻ����룬��ѯ¼������м�¼
     * @Date 2020��12��10��  09:12:57
     * @Param [SysPhone]���ֻ�����
     * @Date Modify in 2020��12��10��  09:12:57
     * @Modify Content:
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.��ȡ���ݿ������
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.Ԥ����SQL���
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //3.������(��ѡ)
            state.setString(1,sysPhone);
            //4.ִ��SQL���
            result =  state.executeQuery();
            //5.��ȡִ�еĽ��
            while (result.next()){
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userPhone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("inTime");
                Timestamp outTime = result.getTimestamp("outTime");
                int status  = result.getInt("status");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.��Դ���ͷ�
            DruidUtil.close(conn,state,result);
        }return data;
    }

    /**
     * @param e
     * @return boolean ��¼��Ľ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ���¼��
     * INSERT INTO EXPRESS(NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)
     * @Date 2020��12��10��  09:12:13
     * @Param [e]:Ҫ¼��Ŀ�ݶ���
     * @Date Modify in 2020��12��10��  09:12:13
     * @Modify Content:
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.������
            state.setString(1,e.getNumber());
            state.setString(2,e.getUsername());
            state.setString(3,e.getUserPhone());
            state.setString(4,e.getCompany());
            state.setString(5,e.getCode());
            state.setString(6,e.getSysPhone());

            //4.ִ��SQL��䣬����ȡִ�н��
            return  state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            if (e1.getMessage().endsWith("for key 'code'")){
                //����Ϊȡ�����ظ�,���������쳣
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
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
     * @param newExpress
     * @return boolean �޸ĵĽ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ��ݵ��޸�
     * @Date 2020��12��10��  09:12:48
     * @Param [id, newExpress] id:Ҫ�޸ĵĿ��id  newExpress:�µĿ�ݶ���(number,company,username,userPhone)
     * @Date Modify in 2020��12��10��  09:12:48
     * @Modify Content:
     * UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=? STATUS=? WHRER ID=?
     */
    @Override
    public boolean update(int id, Express newExpress) {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            //3.������
            state.setString(1,newExpress.getNumber());
            state.setString(2 ,newExpress.getUsername());
            state.setString(3,newExpress.getCompany());
            state.setInt(4,newExpress.getStatus());
          //  state.setString(5,newExpress.getUserPhone());
            state.setInt(5,id);
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
     * @param code
     * @return boolean  �޸ĵĽ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ���Ŀ�ݵ�״̬Ϊ1, ��ʾȡ�����
     * @Date 2020��12��10��  09:12:45
     * @Param [code]:Ҫ�޸ĵĿ��ȡ����
     * @Date Modify in 2020��12��10��  09:12:45
     * @Modify Content:
     */
    @Override
    public boolean updateStatus(String code) {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            //3.������
            state.setString(1,code);
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
     * @param id
     * @return boolean ɾ���Ľ����true��ʾ�ɹ���false��ʾʧ��
     * @Author ������
     * @Description ����id, ɾ�����������Ϣ
     * @Date 2020��12��10��  09:12:53
     * @Param [id] Ҫɾ���Ŀ��id
     * @Date Modify in 2020��12��10��  09:12:53
     * @Modify Content:
     */
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

    @Override
    public boolean updateStatusByExpressNum(String number) {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS_By_Number);
            //3.������
            state.setString(1,number);
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
