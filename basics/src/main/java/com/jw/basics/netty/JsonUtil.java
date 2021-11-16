package com.jw.basics.netty;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    private static GsonBuilder builder = new GsonBuilder();

    static {
        builder.disableHtmlEscaping();
    }

    /**
     * pojo序列化
     * @param obj
     * @return
     */
    public static String pojoToJson(Object obj) {
        return builder.create().toJson(obj);
    }

    /**
     * json反序列化
     */
    public static <T> T jsonToPojo(String json, Class<T> pojo) {
        return JSONObject.parseObject(json, pojo);
    }

}
