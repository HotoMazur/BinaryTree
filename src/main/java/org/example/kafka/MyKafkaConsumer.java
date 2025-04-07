package org.example.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer {
    public static void kafkaConsume(String topic) {
        String groupId = "group1";

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka:9093");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(topic));

            Thread mainThread = Thread.currentThread();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Detected shutdown");
                consumer.wakeup();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));

            while (true) {
                System.out.println("Polling");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                if (records.isEmpty()) {
                    System.out.println("No records found");
                }

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Key: " + record.key() + " Value: " + record.value());
                    System.out.println("Partition: " + record.partition() + " Offset: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            System.out.println("WakeupException: " + e);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}