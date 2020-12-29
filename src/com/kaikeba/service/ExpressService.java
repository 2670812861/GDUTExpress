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
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-10 22:48
 */
public class ExpressService  {
    private static BaseExpressDao dao =  new ExpressDaoMysql();
    /**
     * @return [{size:总数，day:新增},{size:总数，day:新增}]
     * @Author 李梓豪
     * @Description 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     * @Date 2020年12月10日  08:12:53
     * @Param []
     * @Date Modify in 2020年12月10日  08:12:53
     * @Modify Content:
     **/
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * @param limit
     * @param offset
     * @param pageNumber
     * @return 快递的集合
     * @Author 李梓豪
     * @Description 用于查询所有快递
     * @Date 2020年12月10日  08:12:17
     * @Param [limit, offset, pageNumber]
     * limit:是否分页的标记,true表示分页，false表示查询所有快递。
     * offset：SQL语句的其实索引
     * pageNumber：页查询的数量
     * @Date Modify in 2020年12月10日  08:12:17
     * @Modify Content:
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * @param number
     * @return 查询的快递信息，单号不存在时返回null
     * @Author 李梓豪
     * @Description 根据快递番号，查询快递信息
     * @Date 2020年12月10日  08:12:13
     * @Param [number]：单号
     * @Date Modify in 2020年12月10日  08:12:13
     * @Modify Content:
     */

    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * @param code
     * @return 查询的快递信息，取件码不存在时返回null
     * @Author 李梓豪
     * @Description 根据快递取件码，查询快递信息
     * @Date 2020年12月10日  08:12:31
     * @Param [code] 取件码
     * @Date Modify in 2020年12月10日  08:12:31
     * @Modify Content:
     */

    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * @param userPhone
     * @return 查询的快递信息列表
     * @Author 李梓豪
     * @Description 根据用户手机号码，查询他所有的快递信息
     * @Date 2020年12月10日  09:12:01
     * @Param [phoneNumber] 手机号码
     * @Date Modify in 2020年12月10日  09:12:01
     * @Modify Content:
     */
    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * @Author 李梓豪
     * @Description
     * @Date 2020年12月19日  14:12:01
     * @Param [userPhone, status] 手机号,状态码
     * @return 快递列表
     * @Date Modify in 2020年12月19日  14:12:01
     * @Modify Content:
     **/
    public static List<Express> findByUserPhoneAndStatus(String userPhone,int status) {
        return dao.findByUserPhoneAndStatus(userPhone,status);
    }

    /**
     * @param sysPhone
     * @return 查询的快递信息列表
     * @Author 李梓豪
     * @Description 根据录入人手机号码，查询录入的所有记录
     * @Date 2020年12月10日  09:12:57
     * @Param [SysPhone]：手机号码
     * @Date Modify in 2020年12月10日  09:12:57
     * @Modify Content:
     */
    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * @param e
     * @return boolean ：录入的结果，true表示成功，false表示失败
     * @Author 李梓豪
     * @Description 要录入的快递对象
     * @Date 2020年12月10日  09:12:13
     * @Param [e]:要录入的快递对象
     * @Date Modify in 2020年12月10日  09:12:13
     * @Modify Content:
     */

    public static boolean insert(Express e)  {
        //1.生成取件码
         e.setCode(RandomUtil.get()+"");
        try {
            boolean flag =dao.insert(e);
            if (flag){
                //录入成功，发送短信
                SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            //因为取件码重复了，我们使用递归来再次生成随机取件码
            return insert(e);
        }
    }

    /**
     * @param id
     * @param newExpress
     * @return boolean 修改的结果，true表示成功，false表示失败
     * @Author 李梓豪
     * @Description 快递的修改
     * @Date 2020年12月10日  09:12:48
     * @Param [id, newExpress] id:要修改的快递id  newExpress:新的快递对象(number,company,username,userPhone)
     * @Date Modify in 2020年12月10日  09:12:48
     * @Modify Content:
     *
     */
    public static boolean update(int id, Express newExpress) {
        if (newExpress.getUserPhone()!=null){
            //1.非正常修改，即只修改手机号。需要先删除快递，再重新插入新的快递
            // (需注意，因为要生成取件码，因此调用的是本service层的insert，而非dao层的insert)
            dao.delete(id);
            return insert(newExpress);
        }else {
            boolean update = dao.update(id,newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            //因为前端可以更改取件状态，因此如果更改了取件状态就要重新更新取件时间等信息
            if (newExpress.getStatus()==1){
                //未取件状态改为取件状态
                updateStatus(e.getCode());
            }else{
                // TODO: 2020/12/14 0014 丢sysphone的问题  已解决：在controller层的update方法加入newExpress.setSysPhone(UserUtil.getAdminPhone(request.getSession()));
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
     * @return boolean  修改的结果，true表示成功，false表示失败
     * @Author 李梓豪
     * @Description 更改快递的状态为1, 表示取件完成
     * @Date 2020年12月10日  09:12:45
     * @Param [code]:要修改的快递取件码
     * @Date Modify in 2020年12月10日  09:12:45
     * @Modify Content:ng
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * @Author 李梓豪
     * @Description 更新投递数
     * @Date 2020年12月27日  11:12:26
     * @Param [number]
     * @return boolean
     * @Date Modify in 2020年12月27日  11:12:26
     * @Modify Content:
     **/
    public static boolean updateStatusByExpressNum(String number){
        return dao.updateStatusByExpressNum(number);
    }

    /**
     * @param id
     * @return boolean 删除的结果，true表示成功，false表示失败
     * @Author 李梓豪
     * @Description 根据id, 删除单个快递信息
     * @Date 2020年12月10日  09:12:53
     * @Param [id] 要删除的快递id
     * @Date Modify in 2020年12月10日  09:12:53
     * @Modify Content:
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
