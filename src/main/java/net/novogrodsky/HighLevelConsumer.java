package net.novogrodsky;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by david.j.novogrodsky on 5/2/2014.
 * Uses the high level consumer, the details are abstracted away
 * for single thread
 * assumes single partion for topic
 */
public class HighLevelConsumer {
    private final ConsumerConnector consumer;
    private final String topic;

    public HighLevelConsumer(String zookeeper, String groupId, String topic) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "1000");
        props.put("zookeeper.sync.time.ms", "1000");
        props.put("auto.commit.interval.ms", "1000");

        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        this.topic = topic;
    }

    public void testConsumer() {
        Map<String, Integer> topicCount = new HashMap<String, Integer>();
        // define single thread for topic
        topicCount.put(topic, new Integer(1));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams =
                consumer.createMessageStreams(topicCount);

        List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);

        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> consumerIterator = stream.iterator();
            while (consumerIterator.hasNext()) {
                System.out.println("Message from Single topic :: " +
                        new String(consumerIterator.next().message()));
            }
        }
        // precaution
        if (consumer != null) {
            consumer.shutdown();
        }
    }

    public static void main(String[] args) {
        String topic = args[0];
        HighLevelConsumer highLevelConsumer =
                new HighLevelConsumer("localhost:2181", "testgroup", topic);
        highLevelConsumer.testConsumer();
    }
}



