package com.kaikeba.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * @Author: 李梓豪
 * @Description:
 * @Date Created in 2020-12-10 23:49
 */
public class SMSUtil {
    public static boolean send(String phoneNumber,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GKyzUJKWdxYxiqbK7Hb", "MH6yEg4nwDz0frmloyg61pAbsSwpnQ");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "GDUT西区快递驿站");
        request.putQueryParameter("TemplateCode", "SMS_206547953");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if("OK".equals(result.get("Message"))) {
                return true;
            }else{
                System.out.println("短信发送失败，原因："+result.get("Message"));
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean loginSMS(String phoneNumber,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GKyzUJKWdxYxiqbK7Hb", "MH6yEg4nwDz0frmloyg61pAbsSwpnQ");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "GDUT西区快递驿站");
        request.putQueryParameter("TemplateCode", "SMS_206810212");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if("OK".equals(result.get("Message"))) {
                return true;
            }else{
                System.out.println("短信发送失败，原因："+result.get("Message"));
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
