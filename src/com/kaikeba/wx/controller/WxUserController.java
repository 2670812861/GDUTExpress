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
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-17 20:23
 */
public class WxUserController {
    Message msg =  new Message();

    @ResponseBody("/wx/loginSMS.do")
    public String sendSMS(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        // TODO: 2020/12/18 0018 :为了测试，我们不产生随机验证码，直接给某个验证码
        String code = "123456";
        //String code = RandomUtil.get()+"";
        // TODO: 2020/12/18 0018 :为了测试不浪费短信，现在注释掉发短信的语句。
        //boolean flag = SMSUtil.loginSMS(userPhone,code);
        boolean flag = true;
        if (flag){
            //短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送,请查收！");
        }else {
            //短信发送失败
            msg.setStatus(-1);
            msg.setResult("验证码发送失败,请检查手机号或稍后再试！");
        }
        //把手机号和取件码存到工具类，以便登录验证时使用

        UserUtil.setLoginSMS(request.getSession(),userPhone,code);
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String userCode  =  request.getParameter("code");
        // TODO: 2020/12/27 0027  //用于录入人sysPhone所用
        request.getSession().setAttribute("userPhone",userPhone);
        String sysCode = UserUtil.getLoginSMS(request.getSession(),userPhone);
        if (sysCode==null){
            //这个手机号未获取短信
            msg.setStatus(-1);
            msg.setResult("手机号码未获取短信");
        }else if (sysCode.equals(userCode)){
            //这里手机号码和短信一致，登录成功
            //todo:不知道这样改对不对
            User user = new User();
            Courier c = CourierService.findByUserPhone(userPhone);
            if (c!=null){
                //快递员
                msg.setStatus(1);
                //表示登录的是快递员
                user.setUser(false);
            }else{
                //用户
                msg.setStatus(0);
                user.setUser(true);
            }
            msg.setResult("注册/登录成功");
            user.setUserPhone(userPhone);
            UserUtil.setWXUser(request.getSession(),user);
        }else{
            //这里是验证码不一致，登录失败
            msg.setStatus(-2);
            msg.setResult("验证码不正确，请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getWXUser(request.getSession());
        boolean isUser = user.isUser();
        if (isUser){
            //用户
            msg.setStatus(0);
        }else {
            //快递员
            msg.setStatus(1);
        }
        msg.setResult(user.getUserPhone());
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //1.销毁session
        request.getSession().invalidate();
        //2.给客户端回复消息
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}
