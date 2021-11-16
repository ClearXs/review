package com.jw.basics.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过channel实现文件复制
 * @author jiangw
 * @date 2020/11/14 17:42
 * @since 1.0
 */
@Slf4j
public class CopyFileChannel {

    private static int capacity = 465465;

    public static void main(String[] args) throws IOException {
        copyFile("D:\\自私的基因.pdf", "D:\\test1.txt");
        quickCopyFile("D:\\自私的基因.pdf", "D:\\test2.txt");
    }

    /**
     * 快速复制文件，两个通道直接连接复制
     */
    public static void quickCopyFile(String srcPath, String destPath) throws IOException {
        // 计算文件复制的时间
        long startTime = System.currentTimeMillis();
        File srcFile = new File(srcPath);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileInputStream inputStream = null;
        FileChannel inputStreamChannel = null;
        FileOutputStream outputStream = null;
        FileChannel outputStreamChannel = null;
        try {
            inputStream = new FileInputStream(srcFile);
            inputStreamChannel = inputStream.getChannel();
            outputStream = new FileOutputStream(destFile);
            outputStreamChannel = outputStream.getChannel();
            // 两个通道直接连接
            outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (inputStreamChannel != null) {
                inputStreamChannel.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (outputStreamChannel != null) {
                outputStreamChannel.close();
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("===>文件复制执行时间：{}ms", endTime - startTime);
    }

    public static void copyFile(String srcPath, String desPath) throws IOException {
        // 计算文件复制的时间
        long startTime = System.currentTimeMillis();
        // 读取文件
        String fileContent = readFile(srcPath);
        // 改变使其成为写模式，开始写入文件
        writeBuffer(desPath, fileContent.getBytes());
        long endTime = System.currentTimeMillis();
        log.info("===>文件复制执行时间：{}ms", endTime - startTime);
    }

    public static String readFile(String filePath) throws IOException {
        return readFile(new File(filePath));
    }

    public static String readFile(File file) throws IOException {
        // 判断源文件是否存在
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        // 读取源文件
        FileInputStream inputStream = null;
        FileChannel inputStreamChannel = null;
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        String fileContent = null;
        try {
            inputStream = new FileInputStream(file);
            inputStreamChannel = inputStream.getChannel();
            long size = inputStreamChannel.size();
            if (size > capacity) {
                capacity = Integer.parseInt(String.valueOf(size)) * 10;
            }
            int inputLength = -1;
            while ((inputLength = inputStreamChannel.read(buffer)) != -1) {
                log.info("===>读取的字节数: {}", inputLength);
            }
            buffer.flip();
            // 获取数据
            byte[] bytes = new byte[buffer.limit()];
            for (int i = 0; i < buffer.limit(); i++) {
                bytes[i] = buffer.get();
            }
            fileContent = new String(bytes);
            log.info("===>读取的文件内容：{}", fileContent);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (inputStreamChannel != null) {
                inputStreamChannel.close();
            }
        }
        return fileContent;
    }

    public static void writeBuffer(String filePath, byte[] bytes) throws IOException {
        writeBuffer(new File(filePath), bytes);
    }

    public static void writeBuffer(File file, byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        buffer.put(bytes);
        writeBuffer(file, buffer);
    }

    public static void writeBuffer(File file, ByteBuffer buffer) throws IOException {
        // 此时buffer，默认就是写模式了，并且已经有数据了
        if (!file.exists()) {
            // 文件不存在，创建文件
            file.createNewFile();
        }
        FileOutputStream outputStream = null;
        FileChannel outputStreamChannel = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStreamChannel = outputStream.getChannel();
            // 反转buffer，使其成为读模式
            buffer.flip();
            int outputLength = 0;
            while ((outputLength = outputStreamChannel.write(buffer)) != 0) {
                log.info("===>写入字节数：{}", outputLength);
            }
            // 强制刷新设备
            outputStreamChannel.force(true);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (outputStreamChannel != null) {
                outputStreamChannel.close();
            }
        }
    }
}
