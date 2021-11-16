package com.jw.basics.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

/**
 * ByteBuf三种类型 Heap ByteBuf，Direct ByteBuf，Composite ByteBuf
 * @author jiangw
 * @date 2020/11/21 13:52
 * @since 1.0
 */
@Slf4j
public class ByteBufType {

    public static void heapByteBuf() {
        // Heap ByteBuf
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuf.writeBytes("堆".getBytes());
        // 测试当前缓冲区是否是堆缓冲区
        if (heapBuf.hasArray()) {
            // 获取内部数组
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            log.info(new String(array, offset, length));
        }
    }

    public static void directByteBuf() {
        ByteBuf directBuf = ByteBufAllocator.DEFAULT.directBuffer();
        directBuf.writeBytes("直接".getBytes());
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] bytes = new byte[length];
            // 把数据读取到堆内存
            directBuf.getBytes(directBuf.readerIndex(), bytes);
            log.info(new String(bytes));
        }
    }

    public static void compositeByteBuf() {
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        // 消息头
        ByteBuf head = Unpooled.copiedBuffer("头".getBytes());
        // 消息体
        ByteBuf body = Unpooled.copiedBuffer("体".getBytes());
        compositeByteBuf.addComponents(head, body);
        for (ByteBuf byteBuf : compositeByteBuf) {
            int length = byteBuf.readableBytes();
            byte[] bytes = new byte[length];
            // 复制数据到堆内存
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            System.out.println(new String(bytes));
        }
        // 在发一次消息，此时需要重复用到header，所有需要retain
        head.retain();
        // 释放composite内存
        compositeByteBuf.release();
        compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        body = Unpooled.copiedBuffer("二".getBytes());
        compositeByteBuf.addComponents(head, body);
        for (ByteBuf byteBuf : compositeByteBuf) {
            int length = byteBuf.readableBytes();
            byte[] bytes = new byte[length];
            // 复制数据到堆内存
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            System.out.println(new String(bytes));
        }
    }

    public static void main(String[] args) {
        heapByteBuf();
        directByteBuf();
        compositeByteBuf();
    }
}
