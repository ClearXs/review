package com.jw.basics.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriteRead {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        buffer.writeBytes(new byte[]{1, 2, 3});
        log.info("写入：{}", buffer);
        getRead(buffer);
        log.info("取：{}", buffer);
        read(buffer);
        log.info("读：{}", buffer);
    }

    private static void read(ByteBuf byteBuf) {
        while (byteBuf.isReadable()) {
            log.info("取出: {}", byteBuf.readByte());
        }
    }

    /**
     * 不改变指针的读
     */
    private static void getRead(ByteBuf byteBuf) {
        for (int i = 0; i < byteBuf.readableBytes(); i++) {
            log.info("读取的字节：{}", byteBuf.getByte(i));
        }
    }
}
