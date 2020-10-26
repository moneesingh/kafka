FROM openjdk:8-jre-alpine

ENV DEPLOYDIR=/opt/kproducer

RUN mkdir -p ${DEPLOYDIR}/log

COPY target/dependency-jars ${DEPLOYDIR}/dependency-jars
ADD target/kproducer.jar ${DEPLOYDIR}/kproducer.jar

ENTRYPOINT java -jar ${DEPLOYDIR}/kproducer.jar -D exec.mainClass="ProducerApp"