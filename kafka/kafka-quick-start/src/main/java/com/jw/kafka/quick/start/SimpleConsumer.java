package com.jw.kafka.quick.start;

import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class SimpleConsumer {

    public static Consumer<String, String> createStringConsumer() {
        // 设置 Producer 的属性
        Properties properties = new Properties();
        // 设置 Broker 的地址
        properties.put("bootstrap.servers", KafkaConstant.ADDRESS);
        // 消费者分组
        properties.put("group.id", "demo-consumer-group");
        // 设置消费者分组最初的消费进度为 earliest 。可参考博客 https://blog.csdn.net/lishuangzhe7047/article/details/74530417 理解
        properties.put("auto.offset.reset", "earliest");
        // 是否自动提交消费进度
        properties.put("enable.auto.commit", true);
        // 自动提交消费进度频率
        properties.put("auto.commit.interval.ms", "1000");
        // 消息的 key 的反序列化方式
        properties.put("key.deserializer", StringDeserializer.class.getName());
        // 消息的 value 的反序列化方式
        properties.put("value.deserializer", StringDeserializer.class.getName());

        return new KafkaConsumer<>(properties);
    }
}
