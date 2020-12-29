package com.kaikeba.wx.controller;

import com.kaikeba.bean.BootStrapTableExpress;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.User;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.mvc.ResponseView;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-18 15:51
 */
public class QrCodeContoller {
    Message msg = new Message();
    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        //express | user
        String type = request.getParameter("type");
        String userPhone = null;
        String qRCodeContent = null;
        if ("express".equals(type)){
            //快递二维码：被扫后，展示单个快递的信息
            //code
            qRCodeContent = "express_"+code;
        }else{
            //用户二维码：被扫后，快递员(柜子)端展示用户所有快递
            //userPhone
            User wxUser = UserUtil.getWXUser(request.getSession());
            userPhone = wxUser.getUserPhone();
            qRCodeContent = "userPhone_"+userPhone;
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode",qRCodeContent);
        return "/personQRcode.html";
    }

    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String qrcode = (String) session.getAttribute("qrcode");
        if (qrcode==null){
            msg.setStatus(-1);
            msg.setResult("取件码获取出错，请用户重新操作");
        }else{
            msg.setStatus(0);
            msg.setResult(qrcode);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateExpressStatus(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        if (flag){
            msg.setStatus(0);
            msg.setResult("取件成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("取件码不存在，请用户更新二维码");
        }
        return JSONUtil.toJSON(msg);

    }
    @ResponseBody("/wx/updateStatusByExpressNum.do")
    public String updateStatusByExpressNum(HttpServletRequest request, HttpServletResponse response){
        String expressNum = request.getParameter("expressNum");
        boolean flag = ExpressService.updateStatusByExpressNum(expressNum);
        if (flag){
            msg.setStatus(0);
            msg.setResult("取件成功");
        }else {
            msg.setStatus(-1);
            msg.setResult("单号不存在，请用户核查单号");
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2 = null;
        if (e!=null){
            //通过传参改变数据的格式
             e2  = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername()
                    ,e.getUserPhone(),e.getCompany(),code,
                    DateFormatUtil.format(e.getInTime()),e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime()),
                    e.getStatus()==0?"未取件":"已取件",e.getSysPhone());
        }
        if (e==null){
            msg.setStatus(-1);
            msg.setResult("取件码不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }

}
