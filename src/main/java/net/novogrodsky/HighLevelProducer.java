package net.novogrodsky;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by david.j.novogrodsky on 5/5/2014.
 * Simple producer without partitioning
 */
public class HighLevelProducer {
    private static Producer<Integer, String> producer;
    private final Properties props = new Properties();


    public HighLevelProducer() {
        /* this is where I find the brokers, to find the leaders */
        props.put("metadata.broker.list", "localhost:9092");
        /* specifies the seralizer to send message to broker */
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig producerConfig = new ProducerConfig(props);
        producer = new Producer<Integer, String>(producerConfig);
    }

    public static void main(String[] args) {
        HighLevelProducer highLevelProducer = new HighLevelProducer();
        /* making a queue */
        String topic = (String) args[0];
        // make the message
        String messageStr = (String) args[1];
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, messageStr);
        // send message to queue
        producer.send(data);
        producer.close();

    }


}
