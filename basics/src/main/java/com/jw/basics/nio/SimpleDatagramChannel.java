package com.jw.basics.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SimpleDatagramChannel {

    public static void main(String[] args) throws IOException {
        // 获取datagramChannel数据
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.socket().bind(new InetSocketAddress(8080));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketAddress socketAddress = datagramChannel.receive(buffer);

    }
}
