FROM njmittet/alpine-openjdk:jre8

USER root
ENV JAVA_DIR /opt/java
ENV JAR_FILE spring-rest-api.jar
RUN mkdir -p $JAVA_DIR && adduser -D -h $JAVA_DIR java

USER java
WORKDIR $JAVA_DIR
COPY target/$JAR_FILE $JAVA_DIR/$JAR_FILE
COPY varnish.vcl $JAVA_DIR/varnish.vcl

EXPOSE 9000
CMD java -jar $JAR_FILE
