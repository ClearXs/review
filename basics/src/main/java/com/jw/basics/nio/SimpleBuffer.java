package com.jw.basics.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.IntBuffer;

@Slf4j
public class SimpleBuffer {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(200);
        logBuffer(buffer);
        for (int i = 0; i < 5; i++) {
            // 向缓冲区写入数据
            buffer.put(i);
        }
        logBuffer(buffer);
        // 转换为读模式
        buffer.flip();
        for (int i = 0; i < 5; i++) {
            // 不能直接读取数据，因为读的时候还是根据position的位置移动来获取byte[]的数据
            int data = buffer.get();
            log.info("get: {}", data);
        }
        logBuffer(buffer);
        // 转换为写模式，调用flip之前调用clear清空或者compact
        buffer.clear();
        buffer.flip();
        logBuffer(buffer);
    }

    public static void logBuffer(Buffer buffer) {
        log.info("------------");
        log.info("capacity: {}", buffer.capacity());
        log.info("position: {}", buffer.position());
        log.info("limit: {}", buffer.limit());
    }
}
