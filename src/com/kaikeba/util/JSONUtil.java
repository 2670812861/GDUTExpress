package com.kaikeba.util;

import com.google.gson.Gson;

/**
 * @Author: ������
 * @Description:
 * @Date Created in 2020-12-08 19:09
 */
public class JSONUtil {

    private static Gson g = new Gson();
    public static String toJSON(Object obj){
      return g.toJson(obj);
    }
}
