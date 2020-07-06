package com.ckss.project.dada.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author: cks
 */
public class JSONUtil {

    public static String toJson(Object object) {
        return object == null ? "" : JSON.toJSONString(object);
    }

    /**
     * 对result中的json数据进行转换
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass,boolean hasResult){
        if(hasResult){
            JSONObject jsonObject = JSON.parseObject(json);
            return jsonObject.getObject("result", tClass);
        }
        return JSON.parseObject(json, tClass);
    }



    public static <T> T fromJson(String json, Class<T> tClass){
        return JSON.parseObject(json, tClass);
    }
}
