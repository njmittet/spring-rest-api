spring-rest-api
==============

Simple [Spring Boot](http://projects.spring.io/spring-boot/) REST application. Use the Dockerfile to create a [Docker](https://www.docker.com/) container for the applicaton.

Build
-----

Build Java application:

    mvn clean install

Build Docker image: 

    docker build -t spring-rest-api .

Run
---

Run in Docker:

    docker run -it --rm -P --name spring-rest-api spring-rest-api

Run standalone:
    
    java -jar target/spring-rest-api.jar

Run behind Varnish reverse proxy
--------------------------------

The docker-compose-varnish.yml file starts the application in a Docker container, and adds another container running a Varnish reverse proxy:

    docker-compose -f docker-compose-varnish.yml up

Test the proxy:

    curl -XGET $DOCKER_HOST:8080/persons

Run behind Java EE reverse proxy
--------------------------------

The docker-compose-javaee.yml file starts the application in a Docker container, and adds another container running a Java EE reverse proxy:

[Download the Java EE reverse proxy](https://github.com/njmittet/javaee-proxy) from and build the Docker image first.

    docker-compose -f docker-compose-varnish.yml up

Test the proxy:

    curl -XGET $DOCKER_HOST:8080/proxy/persons
