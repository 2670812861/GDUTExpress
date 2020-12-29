package com.kaikeba.dao.implement;



import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.util.DruidUtil;
import exception.DuplicateUserPhoneException;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-17 16:28
 */
public class UserDaoMysql implements BaseUserDao {
    //用于查询数据库中的全部用户人数，以及每日注册量
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(id) total_size," +
            "COUNT(TO_DAYS(RegistrationTime)=TO_DAYS(NOW()) OR NULL) increase_day" +
            " FROM USER";
    //用于查询数据库中的所有用户信息
    public static final  String SQL_FIND_ALL = "SELECT * FROM USER";
    //用于分页查询数据库中的用户信息
    public static final String SQL_FIND_LIMIT ="SELECT * FROM USER LIMIT ?,?";
    //通过快递员编号查询用户信息
    public static final String SQL_FIND_BY_ID ="SELECT * FROM USER WHERE ID=?";
    //通过快递员手机号码查询快递员信息
    public static final String SQL_FIND_BY_USERPHONE ="SELECT * FROM USER WHERE USERPHONE=?";
    //录入快递员信息
    public static final String SQL_INSERT ="INSERT INTO USER(USERNAME,USERPHONE,PASSWORD,REGISTRATIONTIME,LASTLOGINTIME) VALUES(?,?,?,NOW(),?)";
    //快递员信息修改
    public static final String SQL_UPDATE ="UPDATE USER SET USERNAME=?,USERPHONE=?,PASSWORD=?,LASTLOGINTIME=? WHERE ID=?";
    //快递员上次登录时间改变
    public static final String SQL_UPDATELASTLOGINTIME = "UPDATE USER LASTLOGINTIME=? WHERE USERPHONE=?";
    //快递员的删除
    public static final String SQL_DELETE ="DELETE FROM USER WHERE ID=?";

    /**
     * @return {total_size:快递员总数，increase_day:当日快递员注册的数量}
     * @Author 李梓豪
     * @Description 用于查询数据库中所有用户，当日用户注册的数量
     * @Date 2020年12月13日  10:12:55
     * @Param []
     * @Date Modify in 2020年12月13日  10:12:55
     * @Modify Content:
     **/
    @Override
    public Map<String, Integer> console() {
        Map data = new HashMap();
        //1.获取数据库的连接
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        try {
            //2.预编译SQL语句
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.填充参数(可选)
            //4.执行SQL语句
            result =  state.executeQuery();
            //5.获取执行结果
            if(result.next()){
                int total_size = result.getInt("total_size");
                int increase_day = result.getInt("increase_day");

                data.put("total_size",total_size);
                data.put("increase_day",increase_day);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //6.资源的释放
            DruidUtil.close(conn,state,result);
        }

        return data;
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return 用户的集合
     * @Author 李梓豪
     * @Description 用于查询所有用户
     * @Date 2020年12月13日  10:12:38
     * @Param [limit, offset, pageNumber]
     * limit:是否分页的标记,true表示分页，false表示查询所有快递。
     * offset：SQL语句的其实索引
     * pageNumber：页查询的数量
     * @Date Modify in 2020年12月13日  10:12:38
     * @Modify Content:
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> data =new ArrayList<>();
        //1.获取数据库的连接
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.预编译SQL语句
        try {
            //是否分页
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.填充参数(可选)
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }
            else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }

            //4.执行SQL语句
            result =  state.executeQuery();
            //5.获取执行的结果
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
            //6.资源的释放
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    /**
     * @param id
     * @return com.kaikeba.bean.Courier
     * @Author 李梓豪
     * @Description 根据用户编号，查询用户信息
     * @Date 2020年12月12日  22:12:27
     * @Param [number]
     * @Date Modify in 2020年12月12日  22:12:27
     * @Modify Content:
     */
    @Override
    public User findById(int id) {
        //1.获取数据库的连接
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_ID);
            //3.填充参数(可选)
            state.setInt(1,id);
            //4.执行SQL语句
            result =  state.executeQuery();
            //5.获取执行的结果
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
            //6.资源的释放
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * @param userPhone
     * @return com.kaikeba.bean.Courier
     * @Author 李梓豪
     * @Description 根据用户的手机号码，查找用户信息
     * @Date 2020年12月13日  10:12:21
     * @Param [userPhone]
     * @Date Modify in 2020年12月13日  10:12:21
     * @Modify Content:
     */
    @Override
    public User findByUserPhone(String userPhone) {
        //1.获取数据库的连接
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.填充参数(可选)
            state.setString(1,userPhone);
            //4.执行SQL语句
            result =  state.executeQuery();
            //5.获取执行的结果
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
            //6.资源的释放
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    /**
     * @param u
     * @return boolean
     * @Author 李梓豪
     * @Description 要录入的用户信息
     * @Date 2020年12月12日  22:12:26
     * @Param [c]
     * @Date Modify in 2020年12月12日  22:12:26
     * @Modify Content:
     * INSERT INTO USER(USERNAME,USERPHONE,PASSWORD,REGISTRATIONTIME,LASTLOGINTIME) VALUES(?,?,?,NOW(),?)"
     */
    @Override
    public boolean insert(User u) throws DuplicateUserPhoneException {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.填充参数
            state.setString(1,u.getUsername());
            state.setString(2,u.getUserPhone());
            state.setString(3,u.getPassword());
            state.setTimestamp(4,u.getLastLoginTime());
            //4.执行SQL语句，并获取执行结果
            return  state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            // 处理了手机号码重复，那么其余插入失败的原因就只有身份证号码重复了
            if (e1.getMessage().endsWith("for key '手机号码'")){
                //是因为手机号重复,而出现了异常
                DuplicateUserPhoneException e2 = new DuplicateUserPhoneException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * @param id
     * @param newUser
     * @return boolean
     * @Author 李梓豪
     * @Description 用户的修改
     * @Date 2020年12月12日  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020年12月12日  22:12:50
     * @Modify Content:
     * UPDATE USER SET USERNAME=?,USERPHONE=?,PASSWORD=?,LASTLOGINTIME=? WHERE ID=?"
     */
    @Override
    public boolean update(int id, User newUser) throws DuplicateUserPhoneException {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            //3.填充参数
            state.setString(1,newUser.getUsername());
            state.setString(2 ,newUser.getUserPhone());
            state.setString(3,newUser.getPassword());
            state.setTimestamp(4,newUser.getLastLoginTime());
            state.setInt(5,newUser.getId());
            //4.执行SQL语句，并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            // 处理了手机号码重复，那么其余插入失败的原因就只有身份证号码重复了
            if (e1.getMessage().endsWith("for key '手机号码'")){
                //是因为手机号重复,而出现了异常
                DuplicateUserPhoneException e2 = new DuplicateUserPhoneException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * @param u
     * @param date
     * @return boolean
     * @Author 李梓豪
     * @Description 根据id, 更新上次登录时间
     * @Date 2020年12月12日  22:12:46
     * @Param [userPhone, date]
     * @Date Modify in 2020年12月12日  22:12:46
     * @Modify Content:
     */
    @Override
    public boolean updateLastLoginTime(User u, Date date) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATELASTLOGINTIME);
            //3.填充参数
            //不可以直接传data，我们的data是util包下的，此方法需要sql包下的data传入，需调用sql包data构造方法
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,u.getUserPhone());
            //4.执行SQL语句，并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * @param id
     * @return boolean
     * @Author 李梓豪
     * @Description 根据id, 删除用户信息
     * @Date 2020年12月17日  16:12:00
     * @Param [id]
     * @Date Modify in 2020年12月17日  16:12:00
     * @Modify Content:
     */
    @Override
    public boolean delete(int id) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            //3.填充参数
            state.setInt(1,id);
            //4.执行SQL语句，并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
