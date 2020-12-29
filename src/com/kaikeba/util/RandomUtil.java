package com.kaikeba.util;

import java.util.Random;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-10 22:56
 */
public class RandomUtil {
    private  static Random r = new Random();
    public static int get(){
        return r.nextInt(900000)+100000;
    }
}
