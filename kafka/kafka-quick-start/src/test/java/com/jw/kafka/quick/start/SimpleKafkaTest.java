package com.jw.kafka.quick.start;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class SimpleKafkaTest {

    @Test
    void testProducer() throws ExecutionException, InterruptedException {
        Producer<String, String> producer = SimpleProducer.createStringProducer();
        // 传入的三个参数，分别是 Topic ，消息的 key ，消息的 message 。
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstant.TOPIC, "key·", "test message");
        // 同步发送
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata recordMetadata = future.get();
        log.info("message send to {} partition {} offset {}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        Assertions.assertEquals("test-kafka", recordMetadata.topic());
    }

    @Test
    void testConsumer() {
        Assertions.assertTimeout(Duration.ofSeconds(15), () -> {
            Consumer<String, String> consumer = SimpleConsumer.createStringConsumer();
            consumer.subscribe(Collections.singletonList(KafkaConstant.TOPIC));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            records.forEach(record -> log.info("consumer accept key {} value {}", record.key(), record.value()));
        });
    }

}
