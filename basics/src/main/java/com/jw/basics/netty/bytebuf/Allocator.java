package com.jw.basics.netty.bytebuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;

/**
 * nettyByteBuf缓冲区分配器分为两部分，PooledByteBufAllocator与UnpooledByteBuffAllocator
 * @author jiangw
 * @date 2020/11/21 13:26
 * @since 1.0
 */
public class Allocator {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }
}
