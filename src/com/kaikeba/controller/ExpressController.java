package com.kaikeba.controller;

import com.google.gson.JsonNull;
import com.kaikeba.bean.BootStrapTableExpress;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.ResultData;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.DateFormatUtil;
import com.kaikeba.util.JSONUtil;
import com.kaikeba.util.UserUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-11 0:49
 */
public class ExpressController {
    Message msg = new Message();
    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Integer>> data = ExpressService.console();
        if (data.size()==0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        //1.��ȡ��ѯ���ݵ���ʼ����ֵ
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.���ߵ�ǰҳҪ��ѯ��������
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.���в�ѯ
        List<Express> list = ExpressService.findAll(true,offset,pageNumber);
        //BootStrapTableExpress�������Ϊǰ�˲�����ʾTimeStamp���͵����ݣ���Ҫת��ΪString�Ķ���
        List<BootStrapTableExpress> list2 = new ArrayList<>();

        //Message msg = new Message();
        for (Express e:list){
            //�ı����ݸ�ʽ
            String inTime = e.getInTime()==null?"":DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"δ����":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"��ȡ��":"��ȡ��";
            String code = e.getCode()==null?"��ȡ��":e.getCode();
            //ͨ�����θı����ݵĸ�ʽ
            BootStrapTableExpress e2  = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        List<Map<String,Integer>> console = ExpressService.console();
        Integer total = console.get(0).get("data1_size");
        //4.�����Ϸ�װΪ bootstrap-tableʶ��ĸ�ʽ
        ResultData<BootStrapTableExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);

        String json =JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        Express e = new Express(number,username,userPhone,company,UserUtil.getAdminPhone(request.getSession()));
        Boolean flag = ExpressService.insert(e);
       // String sysPhone = (String) request.getSession().getAttribute("sysPhone");
        //oolean flag2 = CourierService.updateDeliveryNumber(UserUtil.getAdminPhone(request.getSession()));
        //Message msg = new Message();
        if (flag){
            //¼��ɹ�
            msg.setStatus(0);
            msg.setResult("��̨���¼��ɹ���");
        }
        else{
            //¼��ʧ��
            msg.setStatus(-1);
            msg.setResult("��̨���¼��ʧ�ܣ�����");
        }
        String json =JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/find.do")
    public  String find(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("number");
        Express e =ExpressService.findByNumber(number);
        //Message msg = new Message();
        if (e == null){
            msg.setStatus(-1);
            msg.setResult("���Ų�����");
        }else{
            msg.setStatus(0);
            msg.setResult("��ѯ�ɹ�");
            msg.setData(e);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        int status = Integer.parseInt(request.getParameter("status"));
        Express newExpress = new Express();
        newExpress.setNumber(number);
        newExpress.setCompany(company);
        newExpress.setUsername(username);
        newExpress.setUserPhone(userPhone);
        newExpress.setStatus(status);
        newExpress.setSysPhone(UserUtil.getAdminPhone(request.getSession()));
        boolean flag = ExpressService.update(id,newExpress);
        //Message msg = new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("�޸ĳɹ�");
        }else{
            msg.setStatus(-1);
            msg.setResult("�޸�ʧ��");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = ExpressService.delete(id);
        //Message msg = new Message();
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
