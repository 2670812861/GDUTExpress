package com.kaikeba.dao.implement;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.util.DruidUtil;
import exception.DuplicateUserPhoneException;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-12 22:53
 */

public class CourierDaoMysql implements BaseCourierDao {
    //用于查询数据库中的全部快递员人数，以及每日注册量
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(编号) total_size," +
            "COUNT(TO_DAYS(注册时间)=TO_DAYS(NOW()) OR NULL) increase_day" +
            " FROM COURIER";
    //用于查询数据库中的所有快递员信息
    public static final  String SQL_FIND_ALL = "SELECT * FROM COURIER";
    //用于分页查询数据库中的快递员信息
    public static final String SQL_FIND_LIMIT ="SELECT * FROM COURIER LIMIT ?,?";
    //通过快递员编号查询快递员信息
    public static final String SQL_FIND_BY_ID ="SELECT * FROM COURIER WHERE 编号=?";
    //通过快递员手机号码查询快递员信息
    public static final String SQL_FIND_BY_USERPHONE ="SELECT * FROM COURIER WHERE 手机号码=?";
    //通过快递员身份证查询快递员信息
    public static final String SQL_FIND_BY_IDENTITYCARD ="SELECT * FROM COURIER WHERE 身份证=?";
    //录入快递员信息
    public static final String SQL_INSERT ="INSERT INTO COURIER(姓名,手机号码,身份证,密码,派件数,注册时间,上次登录时间) VALUES(?,?,?,?,?,NOW(),?)";
    //快递员信息修改
    public static final String SQL_UPDATE ="UPDATE COURIER SET 姓名=?,手机号码=?,身份证=?,密码=?,派件数=?,上次登录时间=? WHERE 编号=?";
    //快递员上次登录时间改变
    public static final String SQL_UPDATELASTLOGINTIME = "UPDATE COURIER 上次登录时间=? WHERE 手机号码=?";
    //快递员的删除
    public static final String SQL_DELETE ="DELETE FROM COURIER WHERE 编号=?";
    //查询投递数
    public static final String SQL_GETDELIVERYNUMBER = "SELECT  COUNT(ID) DeliveryNumber FROM EXPRESS WHERE SYSPHONE=?";
    //更新投递数
    public static final String SQL_UPDATE_DELIVERYNUMBER = "UPDATE COURIER SET 派件数=((SELECT  COUNT(ID) FROM EXPRESS WHERE SYSPHONE=?)+1) WHERE 手机号码=?";

    /**
     * @return {total_size:快递员总数，increase_day:当日快递员注册的数量}
     * @Author 李梓豪
     * @Description 用于查询数据库中所有快递员，当日快递员注册的数量
     * @Date 2020年12月12日  21:12:03
     * @Param []
     * @Date Modify in 2020年12月12日  21:12:03
     * @Modify Content:
     **/
    @Override
    public Map<String,Integer> console() {
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
     * @return 快递员的集合
     * @Author 李梓豪
     * @Description 用于查询所有快递员
     * @Date 2020年12月12日  21:12:24
     * @Param [limit, offset, pageNumber]
     * limit:是否分页的标记,true表示分页，false表示查询所有快递。
     * offset：SQL语句的其实索引
     * pageNumber：页查询的数量
     * @Date Modify in 2020年12月12日  21:12:24
     * @Modify Content:
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data =new ArrayList<>();
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
                int id = result.getInt("编号");
                String username = result.getString("姓名");
                String userPhone = result.getString("手机号码");
                String IdentityCard = result.getString("身份证");
                String password = result.getString("密码");
                int DeliveryNumber = result.getInt("派件数");
                Timestamp RegistrationTime = result.getTimestamp("注册时间");
                Timestamp LastLoginTime = result.getTimestamp("上次登录时间");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                data.add(c);
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
     * @Description 根据快递员编号，查询快递员信息
     * @Date 2020年12月12日  22:12:27
     * @Param [number]
     * @Date Modify in 2020年12月12日  22:12:27
     * @Modify Content:
     */
    @Override
    public Courier findById(int id) {
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
                String username = result.getString("姓名");
                String userPhone = result.getString("手机号码");
                String IdentityCard = result.getString("身份证");
                String password = result.getString("密码");
                int DeliveryNumber = result.getInt("派件数");
                Timestamp RegistrationTime = result.getTimestamp("注册时间");
                Timestamp LastLoginTime = result.getTimestamp("上次登录时间");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                return c;
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
     * @Description 根据快递员的手机号码，查找快递员信息
     * @Date 2020年12月12日  22:12:34
     * @Param [userPhone]
     * @Date Modify in 2020年12月12日  22:12:34
     * @Modify Content:
     */
    @Override
    public Courier findByUserPhone(String userPhone) {
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
                int id = result.getInt("编号");
                String username = result.getString("姓名");
                String IdentityCard = result.getString("身份证");
                String password = result.getString("密码");
                int DeliveryNumber = result.getInt("派件数");
                Timestamp RegistrationTime = result.getTimestamp("注册时间");
                Timestamp LastLoginTime = result.getTimestamp("上次登录时间");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                return c;
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
     * @param IdentityCard
     * @return com.kaikeba.bean.Courier
     * @Author 李梓豪
     * @Description 根据快递员的身份证，查找快递员信息
     * @Date 2020年12月12日  22:12:34
     * @Param [IdentityCard]
     * @Date Modify in 2020年12月12日  22:12:34
     * @Modify Content:
     */
    @Override
    public Courier findByIdentityCard(String IdentityCard) {
        //1.获取数据库的连接
        Connection conn =  DruidUtil.getConnection();
        PreparedStatement state=null;
        ResultSet result = null;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_IDENTITYCARD);
            //3.填充参数(可选)
            state.setString(1,IdentityCard);
            //4.执行SQL语句
            result =  state.executeQuery();
            //5.获取执行的结果
            while (result.next()){
                int id = result.getInt("编号");
                String username = result.getString("姓名");
                String userPhone = result.getString("手机号码");
                String password = result.getString("密码");
                int DeliveryNumber = result.getInt("派件数");
                Timestamp RegistrationTime = result.getTimestamp("注册时间");
                Timestamp LastLoginTime = result.getTimestamp("上次登录时间");
                Courier c = new Courier(id,username,userPhone,IdentityCard,password,DeliveryNumber,RegistrationTime,LastLoginTime);
                return c;
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
     * @Author 李梓豪
     * @Description 获取投递数
     * @Date 2020年12月27日  11:12:59
     * @Param [sysPhone]
     * @return int
     * @Date Modify in 2020年12月27日  11:12:59
     * @Modify Content:
     **/
    @Override
    public int getDeliveryNumber(String sysPhone) {
        //1.获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        int deliveryNumber=0;
        //2.预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_GETDELIVERYNUMBER);
            //3.填充参数(可选)
            state.setString(1, sysPhone);
            //4.执行SQL语句
            result = state.executeQuery();
            //5.获取执行的结果
            if (result.next()) {
                deliveryNumber =result.getInt("DeliveryNumber");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.资源的释放
            DruidUtil.close(conn,state,result);
        }
        return deliveryNumber;
    }


        /**
     * @param c
     * @return boolean
     * @Author 李梓豪
     * @Description 要录入的快递员信息
     * @Date 2020年12月12日  22:12:26
     * @Param [c]
     * @Date Modify in 2020年12月12日  22:12:26
     * @Modify Content:
     */
    @Override
    public boolean insert(Courier c) throws DuplicateUserPhoneException {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.填充参数
            state.setString(1,c.getUsername());
            state.setString(2,c.getUserPhone());
            state.setString(3,c.getIdentityCard());
            state.setString(4,c.getPassword());
            state.setInt(5,c.getDeliveryNumber());
            state.setTimestamp(6,c.getLastLoginTime());
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
     * @param newCourier
     * @return boolean
     * @Author 李梓豪
     * @Description 快递员的修改
     * @Date 2020年12月12日  22:12:50
     * @Param [userPhone, newCourier]
     * @Date Modify in 2020年12月12日  22:12:50
     * @Modify Content:
     * UPDATE COURIER 姓名=?,手机号码=?,身份证=?,密码=? WHERE USERPHONE=?
     */
    @Override
    public boolean update(int id, Courier newCourier) throws DuplicateUserPhoneException {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            //3.填充参数
            state.setString(1,newCourier.getUsername());
            state.setString(2 ,newCourier.getUserPhone());
            state.setString(3,newCourier.getIdentityCard());
            state.setString(4,newCourier.getPassword());
            state.setInt(5,newCourier.getDeliveryNumber());
            state.setTimestamp(6,newCourier.getLastLoginTime());
            state.setInt(7,newCourier.getId());
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
     * @Author 李梓豪
     * @Description 更新投递数
     * @Date 2020年12月27日  09:12:03
     * @Param [sysPhone]
     * @return boolean
     * @Date Modify in 2020年12月27日  09:12:03
     * @Modify Content:
     **/
    @Override
    public boolean updateDeliveryNumber(String sysPhone){
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        ResultSet result = null;
        int count=0;
        try {
            state = conn.prepareStatement(SQL_UPDATE_DELIVERYNUMBER);
            //3.填充参数
            state.setString(1,sysPhone);
            state.setString(2,sysPhone);
            //4.执行SQL语句，并获取执行结果
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
     * @param c
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
    public boolean updateLastLoginTime(Courier c, Date date) {
        //1.连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATELASTLOGINTIME);
            //3.填充参数
            //不可以直接传data，我们的data是util包下的，此方法需要sql包下的data传入，需调用sql包data构造方法
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,c.getUserPhone());
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
