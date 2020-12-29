package com.kaikeba.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;

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
 * @Date Created in 2020-12-14 21:21
 */
public class CourierController {
    Message msg = new Message();
    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        Map<String,Integer> data = CourierService.console();
        if (data.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request,HttpServletResponse response){
        //1.获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.或者当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.进行查询
        List<Courier> list = CourierService.findAll(true,offset,pageNumber);
        //BootStrapTableCourier这个是因为前端不能显示TimeStamp类型的数据，需要转化为String的对象
        List<BootStrapTableCourier> list2 = new ArrayList<>();

        for (Courier c:list){
            String RegistrationTime = c.getRegistrationTime()==null?"":DateFormatUtil.format(c.getRegistrationTime());
            String LastLoginTime = c.getLastLoginTime()==null?"":DateFormatUtil.format(c.getLastLoginTime());

            BootStrapTableCourier c2 = new BootStrapTableCourier(c.getId(),c.getUsername(),c.getUserPhone(),c.getIdentityCard(),c.getPassword(),c.getDeliveryNumber(),RegistrationTime,LastLoginTime);
            list2.add(c2);
        }
        Map<String,Integer> console = CourierService.console();
        Integer total = console.get("total_size");
        //4.将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);

        String json = JSONUtil.toJSON(data);
        System.out.println(json);
        return json;
    }

    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String IdentityCard = request.getParameter("IdentityCard");
        String password = request.getParameter("password");
        Courier c  = new Courier(username,userPhone,IdentityCard,password,UserUtil.getDeliveryNumber(request.getSession()),UserUtil.getLastLoginTime(request.getSession()));
        boolean flag = CourierService.insert(c);
        if (flag){
            //录入成功
            msg.setStatus(0);
            msg.setResult("快递员信息录入成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("快递员信息录入失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/find.do")
    public  String find(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone_find");
        Courier c = CourierService.findByUserPhone(userPhone);
        if (c==null){
            msg.setStatus(-1);
            msg.setResult("手机号码不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(c);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String IdentityCard = request.getParameter("IdentityCard");
        String password = request.getParameter("password");

        Courier newCourier = new Courier();
        newCourier.setId(id);
        newCourier.setUsername(username);
        newCourier.setUserPhone(userPhone);
        newCourier.setIdentityCard(IdentityCard);
        newCourier.setPassword(password);
        newCourier.setDeliveryNumber(UserUtil.getDeliveryNumber(request.getSession()));
        newCourier.setLastLoginTime(UserUtil.getLastLoginTime(request.getSession()));
        boolean flag = CourierService.update(id,newCourier);
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


    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = CourierService.delete(id);
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
