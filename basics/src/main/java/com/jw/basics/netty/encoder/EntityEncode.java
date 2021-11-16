package com.jw.basics.netty.encoder;

import com.jw.basics.netty.Entity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码某个实体类
 * @author jiangw
 * @date 2020/11/21 21:41
 * @since 1.0
 */
public class EntityEncode {

    static class EntityEncoder extends MessageToByteEncoder<Entity> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Entity msg, ByteBuf out) throws Exception {
        }
    }
}
