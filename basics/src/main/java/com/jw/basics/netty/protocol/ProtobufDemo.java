//package com.jw.basics.netty.protocol;
//
//import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//@Slf4j
//public class ProtobufDemo {
//
//    public static MsgProtos.Msg buildMsg() {
//        MsgProtos.Msg.Builder builder = MsgProtos.Msg.newBuilder();
//        builder.setId(1100);
//        builder.setContent("测试protobuf");
//        return builder.build();
//    }
//
//    public static void serAndDesr1() throws IOException {
//        MsgProtos.Msg msg = buildMsg();
//        // 将protobuf序列化成二进制数组
//        byte[] bytes = msg.toByteArray();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        outputStream.write(bytes);
//        byte[] data = outputStream.toByteArray();
//        // 将二进制转化为Protobuf对象
//        MsgProtos.Msg from = MsgProtos.Msg.parseFrom(data);
//        log.info("id: {}", from.getId());
//        log.info("content: {}", from.getContent());
//    }
//
//    public static void serAndDesr2() throws IOException {
//        MsgProtos.Msg msg = buildMsg();
//        // 将protobuf序列化成二进制数组
//        byte[] bytes = msg.toByteArray();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        outputStream.write(bytes);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//        // 从二进制流中反序列化成Protobuf对象
//        MsgProtos.Msg from = MsgProtos.Msg.parseFrom(inputStream);
//        log.info("id: {}", from.getId());
//        log.info("content: {}", from.getContent());
//    }
//
//    public static void serAndDesr3() throws IOException {
//        // 解决半包问题
//        MsgProtos.Msg msg = buildMsg();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        msg.writeDelimitedTo(outputStream);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//        MsgProtos.Msg from = MsgProtos.Msg.parseDelimitedFrom(inputStream);
//        log.info("id: {}", from.getId());
//        log.info("content: {}", from.getContent());
//    }
//
//    public static void main(String[] args) throws IOException {
////        serAndDesr1();
////        serAndDesr2();
//        serAndDesr3();
//    }
//}
