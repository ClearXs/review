package com.jw.kafka.quick.start;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {

    public static Producer<String, String> createStringProducer() {
        // 设置 Producer 的属性
        Properties properties = new Properties();
        // 设置 Broker 的地址
        properties.put("bootstrap.servers", KafkaConstant.ADDRESS);
        // 0-不应答。1-leader 应答。all-所有 leader 和 follower 应答。
        properties.put("acks", "1");
        // 发送失败时，重试发送的次数
        properties.put("retries", 3);
        // 消息的 key 的序列化方式
        properties.put("key.serializer", StringSerializer.class.getName());
        // 消息的 value 的序列化方式
        properties.put("value.serializer", StringSerializer.class.getName());
        // 创建 KafkaProducer 对象
        // 因为我们消息的 key 和 value 都使用 String 类型，所以创建的 Producer 是 <String, String> 的泛型。
        return new KafkaProducer<>(properties);
    }


}
