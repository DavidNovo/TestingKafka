package net.novogrodsky;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import net.novogrodsky.Constants;

import kafka.javaapi.producer.Producer;
import kafka.producer.DefaultPartitioner;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * Created by david.j.novogrodsky on 5/2/2014.
 */
public class ProducerExample {
    public static void main(String[] args) {
        long events = 10;
        Random rnd = new Random();

        Properties props = new Properties();
        //props.put("metadata.broker.list", "localhost:9092,localhost:9093");
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("partitioner.class", DefaultPartitioner.class.getName());
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ", www.example.com, " + ip;
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(Constants.TOPIC, ip, msg);
            producer.send(data);
        }
        producer.close();
    }
}
