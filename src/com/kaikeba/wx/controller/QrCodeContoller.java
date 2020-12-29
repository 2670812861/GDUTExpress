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
 * @Author: ������
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
            //��ݶ�ά�룺��ɨ��չʾ������ݵ���Ϣ
            //code
            qRCodeContent = "express_"+code;
        }else{
            //�û���ά�룺��ɨ�󣬿��Ա(����)��չʾ�û����п��
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
            msg.setResult("ȡ�����ȡ�������û����²���");
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
            msg.setResult("ȡ���ɹ�");
        }else {
            msg.setStatus(-1);
            msg.setResult("ȡ���벻���ڣ����û����¶�ά��");
        }
        return JSONUtil.toJSON(msg);

    }
    @ResponseBody("/wx/updateStatusByExpressNum.do")
    public String updateStatusByExpressNum(HttpServletRequest request, HttpServletResponse response){
        String expressNum = request.getParameter("expressNum");
        boolean flag = ExpressService.updateStatusByExpressNum(expressNum);
        if (flag){
            msg.setStatus(0);
            msg.setResult("ȡ���ɹ�");
        }else {
            msg.setStatus(-1);
            msg.setResult("���Ų����ڣ����û��˲鵥��");
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2 = null;
        if (e!=null){
            //ͨ�����θı����ݵĸ�ʽ
             e2  = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername()
                    ,e.getUserPhone(),e.getCompany(),code,
                    DateFormatUtil.format(e.getInTime()),e.getOutTime()==null?"δ����":DateFormatUtil.format(e.getOutTime()),
                    e.getStatus()==0?"δȡ��":"��ȡ��",e.getSysPhone());
        }
        if (e==null){
            msg.setStatus(-1);
            msg.setResult("ȡ���벻����");
        }else{
            msg.setStatus(0);
            msg.setResult("��ѯ�ɹ�");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }

}
