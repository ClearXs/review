package com.jw.basics.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SimpleChannel {

    public static void main(String[] args) throws IOException {
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 获取文件读取通道
        // 1.创建文件输入流
        FileInputStream inputStream = new FileInputStream("D:\\最新激活码.txt");
        // 2.从流中获取通过读取通道
        FileChannel inputStreamChannel = inputStream.getChannel();
        // 3.从通道中读取数据
        int inputLength = -1;
        // 读取过程是
        // 1.内核缓冲区从硬盘获取数据
        // 2.内核缓冲区告诉通道数据已经准备好，内存缓冲区开始读取数据（阻塞的）就是写入缓冲区。
        while ((inputLength = inputStreamChannel.read(buffer)) != -1) {
            System.out.println(inputLength);
        };
        inputStreamChannel.close();
        inputStream.close();
        // 翻转缓冲区模式，开始读取
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        for (int i = 0; i < buffer.limit(); i++) {
            bytes[i] = buffer.get();
        }

        String s = new String(bytes);
        System.out.println(s);
        s = s.concat("133");
        // 获取文件写入通道
        // 1.创建文件输出流
        FileOutputStream outputStream = new FileOutputStream("D:\\test.txt");
        // 2.从流中获取通过写入通道
        FileChannel outputStreamChannel = outputStream.getChannel();
        // 3.从通道写入数据
        // 写入过程时
        // 1.内存缓冲区开始向通道写入数据，通道告诉内核缓冲区数据开始写入
        // 2.写入完成后，内核缓存区等待操作系统的中断，当中断来临时，把数据写入到硬盘上
        int outputLength = 0;
        // 翻转模式
        buffer.flip();
        // 清空缓冲区
        buffer.clear();
        // 写入缓冲区
        buffer.put(s.getBytes());
        // 切换变成读模式，因为channel使用缓冲区本身就是读取缓冲区的数据，如果buffer还在写模式下，那么也就无法读取了
        buffer.flip();
        while ((outputLength = outputStreamChannel.write(buffer)) != 0) {
            System.out.println(outputLength);
        }
        // 强制刷新到硬盘，即强制中断
        outputStreamChannel.force(true);
        outputStreamChannel.close();
        outputStream.close();
    }
}
