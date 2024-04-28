package com.github.wesleyegberto.kafkaproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessageProducer {
    public static void main(String ... args) {
        Properties props = new Properties();
        // server to retrieve the metadata about cluster
        props.put("bootstrap.servers", "localhost:9092");
        // ack mode (waits replication with followers)
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        System.out.println("Connecting");
        Producer<String, String> producer = new KafkaProducer<>(props);
        System.out.println("About to produce");
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
        }
        System.out.println("About to close");
        producer.close();
    }
}
