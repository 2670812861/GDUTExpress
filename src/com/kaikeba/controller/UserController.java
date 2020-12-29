package com.kaikeba.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.UserService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-17 18:01
 */
public class UserController {
    Message msg = new Message();
    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        Map<String,Integer> data = UserService.console();
        if (data.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request,HttpServletResponse response){
        //1.获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.或者当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.进行查询
        List<User> list = UserService.findAll(true,offset,pageNumber);
        //BootStrapTableCourier这个是因为前端不能显示TimeStamp类型的数据，需要转化为String的对象
        List<BootStrapTableUser> list2 = new ArrayList<>();

        for (User u:list){
            String RegistrationTime = u.getRegistrationTime()==null?"": DateFormatUtil.format(u.getRegistrationTime());
            String LastLoginTime = u.getLastLoginTime()==null?"":DateFormatUtil.format(u.getLastLoginTime());

            BootStrapTableUser u2 = new BootStrapTableUser(u.getId(),u.getUsername(),u.getUserPhone(),u.getPassword(),RegistrationTime,LastLoginTime);
            list2.add(u2);
        }
        Map<String,Integer> console = UserService.console();
        Integer total = console.get("total_size");
        //4.将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);

        String json = JSONUtil.toJSON(data);
        System.out.println(json);
        return json;
    }

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String password = request.getParameter("password");

        User u  = new User(username,userPhone,password,UserUtil.getLastLoginTime(request.getSession()));
        boolean flag = UserService.insert(u);
        if (flag){
            //录入成功
            msg.setStatus(0);
            msg.setResult("用户信息录入成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("用户信息录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/find.do")
    public  String find(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone_find");
        User u = UserService.findByUserPhone(userPhone);
        if (u==null){
            msg.setStatus(-1);
            msg.setResult("手机号码不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(u);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String password = request.getParameter("password");

        User newUser = new User();
        newUser.setId(id);
        newUser.setUsername(username);
        newUser.setUserPhone(userPhone);
        newUser.setPassword(password);
        newUser.setLastLoginTime(UserUtil.getLastLoginTime(request.getSession()));
        boolean flag = UserService.update(id,newUser);
        if (flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            System.out.println("修改失败");
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = UserService.delete(id);
        if (flag){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
