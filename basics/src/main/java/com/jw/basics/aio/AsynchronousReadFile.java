package com.jw.basics.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousReadFile {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("D:\\最新激活码.txt"));
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        Future<Integer> read = fileChannel.read(buffer, 0);
        Integer integer = read.get();
        // 缓冲区反转读
        buffer.flip();
        System.out.println(integer);
        System.out.println(new String(buffer.array()));

//        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
//            @Override
//            public void completed(Integer result, ByteBuffer attachment) {
//                System.out.println(result);
//                System.out.println(new String(attachment.array()));
//            }
//
//            @Override
//            public void failed(Throwable exc, ByteBuffer attachment) {
//
//            }
//        });
//
//        Thread.yield();
    }
}
