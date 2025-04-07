package org.example.kafka;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;

public class KafkaSetup {
    public static void setup() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "kafka:9093");

        try(Admin admin = Admin.create(properties)) {
            String topicName = "my-topic";
            int partitions = 1;
            short replicationFactor = 1;

            admin.createTopics(Collections.singletonList(new NewTopic(topicName, partitions, replicationFactor)));
            System.out.println("Topic created: " + topicName);
        } catch (Exception e) {
            System.err.println("Error creating topic: " + e.getMessage());
        }
    }


}
