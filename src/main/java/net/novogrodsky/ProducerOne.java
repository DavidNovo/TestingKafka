package net.novogrodsky;

import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Created by david.j.novogrodsky on 5/2/2014.
 */
public class ProducerOne {


    public static void main(String[] args) {
        long events = Long.parseLong(args[0]);
        Random rnd = new Random();

        /*
        how do I find the queues?
        here are the properties I use
        */
        Properties props = new Properties();
        // this is where I find the brokers, to find the leaders
        props.put("metadata.broker.list", "broker1:9092,broker2:9092");
        // then what serializer to use to send message to queue
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // now, which class determines where a message goes?
        // to which partion in the topic?
        props.put("partitioner.class", "example.producer.SimplePartitioner");
        // do we producer to require acknowledgement tha message was received?
        // in this case, yes.
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);

        // defining the producer object itself
        // first param is type of partion key
        // second param is type of message
        Producer<String, String> producer = new Producer<String, String>(config);

        // this for block builds random messages
        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ",www.example.com," + ip;
            //sending message to producer
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("page_visits", ip, msg);
            producer.send(data);
        }
        producer.close();
    }
}

