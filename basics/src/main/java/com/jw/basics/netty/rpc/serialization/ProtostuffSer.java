package com.jw.basics.netty.rpc.serialization;

import com.jw.basics.netty.rpc.RpcRequest;
import com.jw.basics.netty.rpc.api.DemoService;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ProtostuffSer implements Serialize {

    private final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    private final Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    @Override
    public <T> byte[] serialization(T obj) {
        Class<T> aClass = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(aClass);
        byte[] data;
        try {
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            buffer.clear();
        }
        return data;
    }

    @Override
    public <T> T deserialization(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }

    private <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
        if (Objects.isNull(schema)) {
            // 这个schema通过RuntimeSchema进行懒创建并缓存
            // 所以可以一直调用RuntimeSchema.getSchema(),这个方法是线程安全的
            schema = RuntimeSchema.getSchema(clazz);
            if (Objects.nonNull(schema)) {
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setInterfaceClass(DemoService.class);
        rpcRequest.setParameters(null);
        rpcRequest.setParameterTypes(null);
        rpcRequest.setRequestId("123");
        rpcRequest.setServiceName(DemoService.class.getName());

        ProtostuffSer protostuffSer = new ProtostuffSer();
        byte[] bytes = protostuffSer.serialization(rpcRequest);

        rpcRequest = protostuffSer.deserialization(bytes, RpcRequest.class);

        String s = rpcRequest.toString();
        System.out.println(s);
    }
}
