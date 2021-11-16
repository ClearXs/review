package com.jw.basics.netty.rpc.serialization;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;

/**
 * TDD
 * @author jiangw
 * @date 2020/11/22 23:55
 * @since 1.0
 */
public class JsonSer implements Serialize {

    private static final GsonBuilder BUILDER = new GsonBuilder();

    static {
        BUILDER.disableHtmlEscaping();
    }

    @Override
    public <T> byte[] serialization(T obj) {
        return BUILDER.create().toJson(obj).getBytes();
    }

    @Override
    public <T> T deserialization(byte[] bytes, Class<T> clazz) {
        return JSONObject.parseObject(new String(bytes), clazz);
    }
}
