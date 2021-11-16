package com.jw.basics.netty.rpc.codec;

import com.jw.basics.netty.rpc.serialization.Serialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ByteEncoder extends MessageToByteEncoder<Object> {

    private final Class<?> targetClass;

    private final Serialize serialize;

    public ByteEncoder(Class<?> targetClass, Serialize serialize) {
        this.targetClass = targetClass;
        this.serialize = serialize;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // 过滤，判断是否时当前需要序列化的类型
        if (targetClass.isInstance(msg)) {
            byte[] bytes = serialize.serialization(msg);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
