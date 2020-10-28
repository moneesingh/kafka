import org.apache.kafka.clients.consumer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

import java.util.ArrayList;
import java.util.Properties;

public class ConsumerApp {
    private static Logger logger = LoggerFactory.getLogger(ConsumerApp.class);
    private static final String BOOTSTRAP_SERVERS = "kafka:9092";
    private static String topicName = "celcius-readings";
    public static void main(String []args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "celcius-reader");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.DoubleDeserializer");

        KafkaConsumer<String, Double> consumer = new KafkaConsumer<>(props);

        ArrayList<String> topics = new ArrayList<>();
        topics.add(topicName);
        consumer.subscribe(topics);
        logger.debug("Subscribed to " + topicName);

        while (true) {
            ConsumerRecords<String, Double> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                continue;
            }
            for (ConsumerRecord<String, Double> record: records) {
                logger.info("timestamp " + record.timestamp() + " Record " + record.value());
                System.out.println("timestamp " + record.timestamp() + " Record " + record.value());
            }
            try {
                consumer.commitAsync();
            } catch (CommitFailedException e) {
                logger.error("commit failed", e);
            }
        }
    }
}
