FROM openjdk:8-jre-alpine

ENV DEPLOYDIR=/opt/kconsumer

RUN mkdir -p ${DEPLOYDIR}/log

COPY target/dependency-jars ${DEPLOYDIR}/dependency-jars
ADD target/kconsumer.jar ${DEPLOYDIR}/kconsumer.jar

WORKDIR ${DEPLOYDIR}
ENTRYPOINT java -jar ${DEPLOYDIR}/kconsumer.jar -D exec.mainClass="ConsumerApp"