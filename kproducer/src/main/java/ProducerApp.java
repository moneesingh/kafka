import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class ProducerApp {
    private static final String BOOTSTRAP_SERVERS = "kafka:9092";
    private static final double rangeMin = -100.0;
    private static final double rangeMax = 100.0;
    private static KafkaProducer<String, Double> producer;
    private static Logger logger = LoggerFactory.getLogger(ProducerApp.class);
    private static String topicName = "celcius-readings";

    public static void main(String []args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }

        NewTopic topic = new NewTopic(topicName, 1, (short) 1);
        Map<String, String> m = new HashMap<>();
        m.put("log.message.timestamp.type", "CreateTime");
        topic.configs(m);
        logger.info("Creating topic {}", topicName);
        CreateTopicsResult result = createTopics(Collections.singletonList(topic));

        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

        producer = new KafkaProducer<>(props);

        //Schedule task to be run every sec
        Timer timer = new Timer();
        SenderTask t = new SenderTask();
        timer.schedule(t, 0, 1000);


        for (int i=0; i<100; i++) {
            logger.debug("In Producer main");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.debug("Interrupted.." + e.getMessage());
            }
        }
        // producer.close();
        System.exit(0);
    }

    public static CreateTopicsResult createTopics(Collection<NewTopic> topics) {
        Properties adminProps = new Properties();
        adminProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        AdminClient adminClient = AdminClient.create(adminProps);

        //ToDo: Check error - if topic already exists or could not be created
        return adminClient.createTopics(topics);
    }

    private static class SenderTask extends TimerTask {
        public void run() {
            Random r = new Random(System.nanoTime());
            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            try {
                logger.debug("Sending message to broker " + randomValue);
                ProducerRecord<String, Double> record = new ProducerRecord<>(topicName, "test", randomValue);
                producer.send(record, (RecordMetadata metadata, Exception e) -> { if (e != null) {
                    e.printStackTrace();}
                });
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}