package com.kaikeba.wx.controller;

import com.kaikeba.bean.BootStrapTableExpress;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.User;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Stream;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-18 0:25
 */
public class WxExpressController {
    Message msg = new Message();

    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest request, HttpServletResponse response){
        User wxUser = UserUtil.getWXUser(request.getSession());
        String userPhone = wxUser.getUserPhone();
        //�����ݿ��в�ѯ�����еĿ����Ϣ
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        //�������Ϣ�е�������ת�����б�
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e:list){
            //�ı����ݸ�ʽ

            String inTime = e.getInTime()==null?"": DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"δ����":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"��ȡ��":"��ȡ��";
            String code = e.getCode()==null?"��ȡ��":e.getCode();
            //ͨ�����θı����ݵĸ�ʽ
            BootStrapTableExpress e2  = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }

        if (list.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
            //������
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express ->{
                if (express.getStatus().equals("��ȡ��")){
                    //�����б���
                    return true;
                }else {
                    return false;
                }
                //����
            }).sorted((o1,o2)->{
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int)(o1Time-o2Time);
            });
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express ->{
                if (express.getStatus().equals("��ȡ��")){
                    //�����б���
                    return true;
                }else {
                    return false;
                }
                //����
            }).sorted((o1,o2)->{
                long o1Time = DateFormatUtil.toTime(o1.getInTime());
                long o2Time = DateFormatUtil.toTime(o2.getInTime());
                return (int)(o1Time-o2Time);
            });
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap<>();
            data.put("status0",s0);
            data.put("status1",s1);
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg.getData());
        return json;

    }

    @ResponseBody("/wx/uerExpressList.do")
    public String expressList(HttpServletRequest request, HttpServletResponse response){
        String usePhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(usePhone,0);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e:list){
            //�ı����ݸ�ʽ
            String inTime = e.getInTime()==null?"": DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"δ����":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"��ȡ��":"��ȡ��";
            String code = e.getCode()==null?"��ȡ��":e.getCode();
            //ͨ�����θı����ݵĸ�ʽ
            BootStrapTableExpress e2  = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        if (list.size()==0){
            msg.setStatus(-1);
            msg.setResult("δ��ѯ�����");
        }else{
            msg.setStatus(0);
            msg.setResult("��ѯ�ɹ�");
            msg.setData(list2);
        }
        return  JSONUtil.toJSON(msg);
    }

    @ResponseBody("/ul/indexQueryExpress.do")
    public String findByExpressNumber(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("expressNum");
        System.out.println(number);
        Express e = ExpressService.findByNumber(number);
        BootStrapTableExpress e2 = null;
        if (e==null){
            msg.setStatus(-1);
            msg.setResult("���Ų�����,����");
        }else{
            //ͨ�����θı����ݵĸ�ʽ
            e2  = new BootStrapTableExpress(e.getId(),number,e.getUsername()
                    ,e.getUserPhone(),e.getCompany(),e.getCode(),
                    DateFormatUtil.format(e.getInTime()),e.getOutTime()==null?"δ����":DateFormatUtil.format(e.getOutTime()),
                    e.getStatus()==0?"δȡ��":"��ȡ��",e.getSysPhone());

            msg.setStatus(0);
            msg.setResult("��ѯ�ɹ���������תҳ��...");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }

    @ResponseBody("/wx/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        Express e = new Express(number,username,userPhone,company,UserUtil.getAdminPhone(request.getSession()));
        Boolean flag = ExpressService.insert(e);
        String sysPhone = (String) request.getSession().getAttribute("userPhone");
        boolean flag2 = CourierService.updateDeliveryNumber(sysPhone);
        //Message msg = new Message();
        if (flag&&flag2){
            //¼��ɹ�
            msg.setStatus(0);
            msg.setResult("΢�Ŷ˿��¼��ɹ������ԱͶ�������³ɹ���");
        }else if (flag&&(!flag2)){
            msg.setStatus(-1);
            msg.setResult("΢�Ŷ˿��¼��ɹ������ԱͶ��������ʧ�ܣ�");
        }
        else{
            //¼��ʧ��
            msg.setStatus(-2);
            msg.setResult("¼��ʧ�ܣ�");
        }
        String json =JSONUtil.toJSON(msg);
        return json;
    }
}
