This is a kafka application
---------------------------
It builds and runs 3 containers.
1. Kafka Server (bitnami/kafka:latest)
2. Kafka Producer
3. kafka Consumer

#### Requirements:
git, Java8, Maven3, Docker 18.09 or up, Compose 1.23.2

#### Assumptions:
There are no other applications already running on port 9092

#### Behavior

###### Producer:
Producer creates a topic, 'celcius-readings' and configures the topic to log producer timestamp.
Producer schedules a task to send a Double type random celcius reading to the topic every 1 sec asynchronously
and uses a callback to log error if send fails. Main program exits in 4 sec.
Producer uses String key and Double value Serializers to log to the topic.
Applications log in /opt/kproducer/log/producerApp.log

###### Consumer:
Consumer uses String key and Double value Deserializer. It runs indefinitely and polls to consume from
topic 'celcius-readings'. It outputs producer created timestamp and celcius reading on console and also logs to a logfile.
Consumer application log into /opt/kconsumer/log/consumerApp.log

###### Building code
Git clone the respository, then in the root directory
```
make image
docker-compose up -d
```

###### ToDo:
Add testcases.



