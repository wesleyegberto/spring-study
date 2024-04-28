package com.github.wesleyegberto.kafkaproducer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class MessageReader {
    public static void main(String[] args) {
        Properties props = new Properties();
        // server to retrieve the metadata about cluster
        props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        // consumer group
        props.put("group.id", "my-group");
        props.put("session.timeout.ms", "30000");

        // autocommit after 1s
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        // to manual commit
        // props.put("enable.auto.commit", "false");

        // custom max interval to poll (after this it will be removed)
        //props.put("max.poll.interval.ms", "2000");
        //props.put("max.poll.records", "5");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        System.out.println("Connecting");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("my-topic"));

        System.out.println("Topics:");
        consumer.listTopics()
            .forEach((name, info) -> System.out.println("Name: " + name));

        try {
            while (true) {
                System.out.println("About to poll");
                ConsumerRecords<String, String> records = consumer.poll(1000);
                System.out.println("About to read");
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    // commits
                    consumer.commitAsync();
                }
            }
        } finally {
            System.out.println("About to close");
            consumer.close();
        }
    }
}
