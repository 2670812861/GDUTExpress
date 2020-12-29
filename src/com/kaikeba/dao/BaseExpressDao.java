package com.kaikeba.dao;

import com.kaikeba.bean.Express;
import exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-10 8:45
 */
public interface BaseExpressDao {
    /**
     * @Author 李梓豪
     * @Description 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     * @Date 2020年12月10日  08:12:53
     * @Param []
     * @return [{size:总数，day:新增},{size:总数，day:新增}]
     * @Date Modify in 2020年12月10日  08:12:53
     * @Modify Content:
     **/
    List<Map<String,Integer>> console();

    /**
     * @Author 李梓豪
     * @Description 用于查询所有快递
     * @Date 2020年12月10日  08:12:17
     * @Param [limit, offset, pageNumber]
     * limit:是否分页的标记,true表示分页，false表示查询所有快递。
     * offset：SQL语句的其实索引
     * pageNumber：页查询的数量
     * @return 快递的集合
     * @Date Modify in 2020年12月10日  08:12:17
     * @Modify Content:
     **/
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     * @Author 李梓豪
     * @Description 根据快递单号，查询快递信息
     * @Date 2020年12月10日  08:12:13
     * @Param [number]：单号
     * @return 查询的快递信息，单号不存在时返回null
     * @Date Modify in 2020年12月10日  08:12:13
     * @Modify Content:
     **/
    Express findByNumber(String number);

    /**
     * @Author 李梓豪
     * @Description 根据快递取件码，查询快递信息
     * @Date 2020年12月10日  08:12:31
     * @Param [code] 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     * @Date Modify in 2020年12月10日  08:12:31
     * @Modify Content:
     **/
    Express findByCode(String code);

    /**
     * @Author 李梓豪
     * @Description 根据用户手机号码，查询他所有的快递信息
     * @Date 2020年12月10日  09:12:01
     * @Param [phoneNumber] 手机号码
     * @return 查询的快递信息列表
     * @Date Modify in 2020年12月10日  09:12:01
     * @Modify Content:
     **/
    List<Express> findByUserPhone(String userPhone);

    /**
     * @Author 李梓豪
     * @Description 通过电话号码和取件状态来查询快递，返回已签收或未签收的快递列表
     * @Date 2020年12月19日  13:12:27
     * @Param [userPhone]
     * @return 返回已签收或未签收的快递列表
     * @Date Modify in 2020年12月19日  13:12:27
     * @Modify Content:
     **/
    List<Express> findByUserPhoneAndStatus(String userPhone,int status);
    /**
     * @Author 李梓豪
     * @Description 根据录入人手机号码，查询录入的所有记录
     * @Date 2020年12月10日  09:12:57
     * @Param [SysPhone]：手机号码
     * @return 查询的快递信息列表
     * @Date Modify in 2020年12月10日  09:12:57
     * @Modify Content:
     **/
    List<Express> findBySysPhone(String sysPhone);

    /**
     * @Author 李梓豪
     * @Description 要录入的快递对象
     * @Date 2020年12月10日  09:12:13
     * @Param [e]:要录入的快递对象
     * @return boolean ：录入的结果，true表示成功，false表示失败
     * @Date Modify in 2020年12月10日  09:12:13
     * @Modify Content:
     **/
    boolean insert(Express e) throws DuplicateCodeException;

    /**
     * @Author 李梓豪
     * @Description 快递的修改
     * @Date 2020年12月10日  09:12:48
     * @Param [id, newExpress] id:要修改的快递id  newExpress:新的快递对象(number,company,username,userPhone)
     * @return boolean 修改的结果，true表示成功，false表示失败
     * @Date Modify in 2020年12月10日  09:12:48
     * @Modify Content:
     **/
    boolean update(int id,Express newExpress);

   /**
    * @Author 李梓豪
    * @Description 更改快递的状态为1,表示取件完成
    * @Date 2020年12月10日  09:12:45
    * @Param [code]:要修改的快递取件码
    * @return boolean  修改的结果，true表示成功，false表示失败
    * @Date Modify in 2020年12月10日  09:12:45
    * @Modify Content:ng
    **/
    boolean updateStatus(String code);

    /**
     * @Author 李梓豪
     * @Description 根据id,删除单个快递信息
     * @Date 2020年12月10日  09:12:53
     * @Param [id] 要删除的快递id
     * @return boolean 删除的结果，true表示成功，false表示失败
     * @Date Modify in 2020年12月10日  09:12:53
     * @Modify Content:
     **/
    boolean delete(int id);


    boolean updateStatusByExpressNum(String number);
}
