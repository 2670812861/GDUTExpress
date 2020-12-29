package com.kaikeba.controller;

import com.kaikeba.bean.Message;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.AdminService;
import com.kaikeba.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-09 17:04
 */
public class AdminController {
    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        //1.�Ӳ���
        String username =request.getParameter("username");
        String password= request.getParameter("password");
        //2.����Service������������ȡ���
        boolean result = AdminService.login(username,password);
        //3.���ݽ��,׼����ͬ�ķ�������
        Message msg = null;
        if(result){
            msg = new Message(0,"��¼�ɹ�");//{status:0,result��"��¼�ɹ�"}
            //��¼ʱ���ip�ĸ���
            Date date  = new Date();
            String ip = request.getRemoteAddr();
            AdminService.updateLoginTimeAndIP(username,date,ip);
            //�����û����������Ա�������Ȩ�޹���ʹ��
            request.getSession().setAttribute("adminUsername","username");
        }else {
            msg = new Message(-1,"��¼ʧ��");//{status:-1,result��"��¼ʧ��"}
        }
        //4.������תΪJSON
        String json = JSONUtil.toJSON(msg);
        //5.��JSON�ظ���ajax
        return json;
    }
}
