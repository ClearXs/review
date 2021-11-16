package com.jw.basics.netty.rpc.codec;

import com.jw.basics.netty.rpc.serialization.Serialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ByteDecoder extends ByteToMessageDecoder {

    private final Class<?> targetClass;

    private final Serialize serialize;

    public ByteDecoder(Class<?> targetClass, Serialize serialize) {
        this.targetClass = targetClass;
        this.serialize = serialize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 判断数据包可读的大小，若小于4则丢弃
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int length = in.readInt();
        if (in.readableBytes() < length) {
            // 解决分包问题
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object target = serialize.deserialization(bytes, targetClass);
        out.add(target);
    }
}
