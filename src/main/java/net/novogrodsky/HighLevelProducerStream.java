package net.novogrodsky;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Created by david.j.novogrodsky on 5/5/2014.
 * Simple producer without partitioning
 */
public class HighLevelProducerStream {
    private static Producer<String, String> producer;
    private final Properties props = new Properties();


    public HighLevelProducerStream() {
        /* this is where I find the brokers, to find the leaders */
        props.put("metadata.broker.list", "localhost:9092");

        /* specifies the seralizer to send message to broker */
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        ProducerConfig producerConfig = new ProducerConfig(props);
        producer = new Producer<String, String>(producerConfig);
}

    public static void main(String[] args) {
        long events = 100;
        Random rnd = new Random();

        HighLevelProducerStream highLevelProducer = new HighLevelProducerStream();
        /* taking a param from command line */
        String topic = (String) args[0];

        // make the message
        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ", www.example.com, " + ip;
            KeyedMessage<String, String> data =
                    new KeyedMessage<String, String>(net.novogrodsky.Constants.TOPIC, ip, msg);
            // send message to queue
            producer.send(data);
        }

        producer.close();

    }


}
