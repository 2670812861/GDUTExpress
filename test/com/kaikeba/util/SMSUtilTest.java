package com.kaikeba.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-10 23:52
 */
public class SMSUtilTest {
    @Test
    public  void  sendSms(){
        boolean flag = SMSUtil.send("13751768661","123456");
        System.out.println(flag);
    }

    @Test
    public void loginSMS() {
        boolean flag = SMSUtil.loginSMS("13751768661","111111");
        System.out.println(flag);
    }
}