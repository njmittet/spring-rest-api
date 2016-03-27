FROM njmittet/alpine-openjdk:jre8

USER root
RUN mkdir -p /opt/java && adduser -D -h /opt/java java

USER java
WORKDIR /opt/java
COPY target/spring-rest-api.jar /opt/java/spring-rest-api.jar
COPY varnish.vcl /opt/java/varnish.vcl

EXPOSE 9000
CMD ["java", "-jar", "spring-rest-api.jar"]
