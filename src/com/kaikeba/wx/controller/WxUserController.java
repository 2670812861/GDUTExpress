package com.kaikeba.wx.controller;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.User;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.RandomUtil;
import com.kaikeba.util.SMSUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-17 20:23
 */
public class WxUserController {
    Message msg =  new Message();

    @ResponseBody("/wx/loginSMS.do")
    public String sendSMS(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        // TODO: 2020/12/18 0018 :Ϊ�˲��ԣ����ǲ����������֤�룬ֱ�Ӹ�ĳ����֤��
        String code = "123456";
        //String code = RandomUtil.get()+"";
        // TODO: 2020/12/18 0018 :Ϊ�˲��Բ��˷Ѷ��ţ�����ע�͵������ŵ���䡣
        //boolean flag = SMSUtil.loginSMS(userPhone,code);
        boolean flag = true;
        if (flag){
            //���ŷ��ͳɹ�
            msg.setStatus(0);
            msg.setResult("��֤���ѷ���,����գ�");
        }else {
            //���ŷ���ʧ��
            msg.setStatus(-1);
            msg.setResult("��֤�뷢��ʧ��,�����ֻ��Ż��Ժ����ԣ�");
        }
        //���ֻ��ź�ȡ����浽�����࣬�Ա��¼��֤ʱʹ��

        UserUtil.setLoginSMS(request.getSession(),userPhone,code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String userCode  =  request.getParameter("code");
        // TODO: 2020/12/27 0027  //����¼����sysPhone����
        request.getSession().setAttribute("userPhone",userPhone);
        String sysCode = UserUtil.getLoginSMS(request.getSession(),userPhone);
        if (sysCode==null){
            //����ֻ���δ��ȡ����
            msg.setStatus(-1);
            msg.setResult("�ֻ�����δ��ȡ����");
        }else if (sysCode.equals(userCode)){
            //�����ֻ�����Ͷ���һ�£���¼�ɹ�
            //todo:��֪�������ĶԲ���
            User user = new User();
            Courier c = CourierService.findByUserPhone(userPhone);
            if (c!=null){
                //���Ա
                msg.setStatus(1);
                //��ʾ��¼���ǿ��Ա
                user.setUser(false);
            }else{
                //�û�
                msg.setStatus(0);
                user.setUser(true);
            }
            msg.setResult("ע��/��¼�ɹ�");
            user.setUserPhone(userPhone);
            UserUtil.setWXUser(request.getSession(),user);
        }else{
            //��������֤�벻һ�£���¼ʧ��
            msg.setStatus(-2);
            msg.setResult("��֤�벻��ȷ������");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getWXUser(request.getSession());
        boolean isUser = user.isUser();
        if (isUser){
            //�û�
            msg.setStatus(0);
        }else {
            //���Ա
            msg.setStatus(1);
        }
        msg.setResult(user.getUserPhone());
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //1.����session
        request.getSession().invalidate();
        //2.���ͻ��˻ظ���Ϣ
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}
