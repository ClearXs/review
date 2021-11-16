package com.jw.basics.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * bytebuf的引用计数
 * @author jiangw
 * @date 2020/11/21 13:01
 * @since 1.0
 */
@Slf4j
public class ReferenceCount {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        log.info("init: {}", buffer.refCnt());
        buffer.retain();
        log.info("retain: {}", buffer.refCnt());
        buffer.release();
        log.info("release: {}", buffer.refCnt());
        buffer.release();
        log.info("release2: {}", buffer.refCnt());
        buffer.release();
        log.info("release3: {}", buffer.refCnt());
    }

    private void handleMethod(ByteBuf byteBuf) {
        byteBuf.retain();
        try {
            log.info("业务处理...");
        } finally {
            byteBuf.release();
        }
    }
}
