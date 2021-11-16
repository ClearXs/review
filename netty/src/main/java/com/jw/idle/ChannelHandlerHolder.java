package com.jw.idle;

import io.netty.channel.ChannelHandler;

public interface ChannelHandlerHolder {

    ChannelHandler[] channelHandlers();
}
