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
 * @Author: ������
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
        //1.��ȡ��ѯ���ݵ���ʼ����ֵ
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.���ߵ�ǰҳҪ��ѯ��������
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.���в�ѯ
        List<User> list = UserService.findAll(true,offset,pageNumber);
        //BootStrapTableCourier�������Ϊǰ�˲�����ʾTimeStamp���͵����ݣ���Ҫת��ΪString�Ķ���
        List<BootStrapTableUser> list2 = new ArrayList<>();

        for (User u:list){
            String RegistrationTime = u.getRegistrationTime()==null?"": DateFormatUtil.format(u.getRegistrationTime());
            String LastLoginTime = u.getLastLoginTime()==null?"":DateFormatUtil.format(u.getLastLoginTime());

            BootStrapTableUser u2 = new BootStrapTableUser(u.getId(),u.getUsername(),u.getUserPhone(),u.getPassword(),RegistrationTime,LastLoginTime);
            list2.add(u2);
        }
        Map<String,Integer> console = UserService.console();
        Integer total = console.get("total_size");
        //4.�����Ϸ�װΪ bootstrap-tableʶ��ĸ�ʽ
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
            //¼��ɹ�
            msg.setStatus(0);
            msg.setResult("�û���Ϣ¼��ɹ�");
        }else{
            msg.setStatus(-1);
            msg.setResult("�û���Ϣ¼��ʧ��");
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
            msg.setResult("�ֻ����벻����");
        }else{
            msg.setStatus(0);
            msg.setResult("��ѯ�ɹ�");
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
            msg.setResult("�޸ĳɹ�");
        }else{
            System.out.println("�޸�ʧ��");
            msg.setStatus(-1);
            msg.setResult("�޸�ʧ��");
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
            msg.setResult("ɾ���ɹ�");
        }else{
            msg.setStatus(-1);
            msg.setResult("ɾ��ʧ��");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
