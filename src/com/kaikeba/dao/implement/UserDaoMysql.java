package com.kaikeba.dao.implement;



import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.util.DruidUtil;
import exception.DuplicateUserPhoneException;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-17 16:28
 */
public class UserDaoMysql implements BaseUserDao {
    //���ڲ�ѯ���ݿ��е�ȫ���û��������Լ�ÿ��ע����
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(id) total_size," +
            "COUNT(TO_DAYS(RegistrationTime)=TO_DAYS(NOW()) OR NULL) increase_day" +
            " FROM USER";
    //���ڲ�ѯ���ݿ��е������û���Ϣ
    public static final  String SQL_FIND_ALL = "SELECT * FROM USER";
    //���ڷ�ҳ��ѯ���ݿ��е��û���Ϣ
    public static final String SQL_FIND_LIMIT ="SELECT * FROM USER LIMIT ?,?";
    //ͨ�����Ա��Ų�ѯ�û���Ϣ
    public static final String SQL_FIND_BY_ID ="SELECT * FROM USER WHERE ID=?";
    //ͨ�����Ա�ֻ������ѯ���Ա��Ϣ
    public static final String SQL_FIND_BY_USERPHONE ="SELECT * FROM USER WHERE USERPHONE=?";
    //¼����Ա��Ϣ
    public static final String SQL_INSERT ="INSERT INTO USER(USERNAME,USERPHONE,PASSWORD,REGISTRATIONTIME,LASTLOGINTIME) VALUES(?,?,?,NOW(),?)";
    //���Ա��Ϣ�޸�
    public static final String SQL_UPDATE ="UPDATE USER SET USERNAME=?,USERPHONE=?,PASSWORD=?,LASTLOGINTIME=? WHERE ID=?";
    //���Ա�ϴε�¼ʱ��ı�
    public static final String SQL_UPDATELASTLOGINTIME = "UPDATE USER LASTLOGINTIME=? WHERE USERPHONE=?";
    //���Ա��ɾ��
    public static final String SQL_DELETE ="DELETE FROM USER WHERE ID=?";

    /**
     * @return {total_size:���Ա������increase_day:���տ��Աע�������}
     * @Author ������
     * @Description ���ڲ�ѯ���ݿ��������û��������û�ע�������
     * @Date 2020��12��13��  10:12:55
     * @Param []
     * @Date Modify in 2020��12��13��  10:12:55
     * @Modify Content:
     **/
    @Override
    public Map<String, Integer> console() {
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
     * @return �û��ļ���
     * @Author ������
     * @Description ���ڲ�ѯ�����û�
     * @Date 2020��12��13��  10:12:38
     * @Param [limit, offset, pageNumber]
     * limit:�Ƿ��ҳ�ı��,true��ʾ��ҳ��false��ʾ��ѯ���п�ݡ�
     * offset��SQL������ʵ����
     * pageNumber��ҳ��ѯ������
     * @Date Modify in 2020��12��13��  10:12:38
     * @Modify Content:
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> data =new ArrayList<>();
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
                String username = result.getString("username");
                String userPhone = result.getString("userPhone");
                String password = result.getString("password");
                Timestamp RegistrationTime = result.getTimestamp("RegistrationTime");
                Timestamp LastLoginTime = result.getTimestamp("LastLoginTime");
                User u = new User(id,username,userPhone,password,RegistrationTime,LastLoginTime);
                data.add(u);
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
     * @Description �����û���ţ���ѯ�û���Ϣ
     * @Date 2020��12��12��  22:12:27
     * @Param [number]
     * @Date Modify in 2020��12��12��  22:12:27
     * @Modify Content:
     */
    @Override
    public User findById(int id) {
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
                String username = result.getString("username");
                String userPhone = result.getString("userPhone");
                String password = result.getString("password");
                Timestamp RegistrationTime = result.getTimestamp("RegistrationTime");
                Timestamp LastLoginTime = result.getTimestamp("LastLoginTime");
                User u = new User(id,username,userPhone,password,RegistrationTime,LastLoginTime);
                return u;
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
     * @Description �����û����ֻ����룬�����û���Ϣ
     * @Date 2020��12��13��  10:12:21
     * @Param [userPhone]
     * @Date Modify in 2020��12��13��  10:12:21
     * @Modify Content:
     */
    @Override
    public User findByUserPhone(String userPhone) {
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
                String username = result.getString("username");
                String password = result.getString("password");
                Timestamp RegistrationTime = result.getTimestamp("RegistrationTime");
                Timestamp LastLoginTime = result.getTimestamp("LastLoginTime");
                User u = new User(id,username,userPhone,password,RegistrationTime,LastLoginTime);
                return u;
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
     * @param u
     * @return boolean
     * @Author ������
     * @Description Ҫ¼����û���Ϣ
     * @Date 2020��12��12��  22:12:26
     * @Param [c]
     * @Date Modify in 2020��12��12��  22:12:26
     * @Modify Content:
     * INSERT INTO USER(USERNAME,USERPHONE,PASSWORD,REGISTRATIONTIME,LASTLOGINTIME) VALUES(?,?,?,NOW(),?)"
     */
    @Override
    public boolean insert(User u) throws DuplicateUserPhoneException {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.������
            state.setString(1,u.getUsername());
            state.setString(2,u.getUserPhone());
            state.setString(3,u.getPassword());
            state.setTimestamp(4,u.getLastLoginTime());
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
     * @param newUser
     * @return boolean
     * @Author ������
     * @Description �û����޸�
     * @Date 2020��12��12��  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020��12��12��  22:12:50
     * @Modify Content:
     * UPDATE USER SET USERNAME=?,USERPHONE=?,PASSWORD=?,LASTLOGINTIME=? WHERE ID=?"
     */
    @Override
    public boolean update(int id, User newUser) throws DuplicateUserPhoneException {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            //3.������
            state.setString(1,newUser.getUsername());
            state.setString(2 ,newUser.getUserPhone());
            state.setString(3,newUser.getPassword());
            state.setTimestamp(4,newUser.getLastLoginTime());
            state.setInt(5,newUser.getId());
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
    @Override
    public boolean updateLastLoginTime(User u, Date date) {
        //1.���ӵĻ�ȡ
        Connection conn = DruidUtil.getConnection();
        //2.Ԥ����SQL���
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATELASTLOGINTIME);
            //3.������
            //������ֱ�Ӵ�data�����ǵ�data��util���µģ��˷�����Ҫsql���µ�data���룬�����sql��data���췽��
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,u.getUserPhone());
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
     * @return boolean
     * @Author ������
     * @Description ����id, ɾ���û���Ϣ
     * @Date 2020��12��17��  16:12:00
     * @Param [id]
     * @Date Modify in 2020��12��17��  16:12:00
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
}
