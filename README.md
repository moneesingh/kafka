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

######Producer:
Producer create a topic, 'celcius-readings' and configures the topic to log producer timestamp.
Producer schedules a task to send a Double type random celcius reading to the topic every 1 sec asynchronously
and uses a callback to log error if send fails. Main program exits in 4 sec.
Producer uses String key and Double value Serializers to log to the topic.

###### Consumer:
Consumer used String key and Double value Deserializer.
