FROM openjdk:8-jre-alpine

ENV DEPLOYDIR=/opt/kproducer

RUN mkdir -p ${DEPLOYDIR}/log

COPY target/dependency-jars ${DEPLOYDIR}/dependency-jars
ADD target/kconsumer.jar ${DEPLOYDIR}/kconsumer.jar

ENTRYPOINT java -jar ${DEPLOYDIR}/kconsumer.jar -D exec.mainClass="ConsumerApp"