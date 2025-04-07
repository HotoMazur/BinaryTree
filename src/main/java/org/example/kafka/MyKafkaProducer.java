package org.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyKafkaProducer {

    public static void kafkaProduce(String topic, String message) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka:9093");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(topic, message);

        // Send data
        producer.send(record);

        // Tell producer to send all data and block until complete - synchronous
        producer.flush();

        // Close the producer
        producer.close();
    }
}