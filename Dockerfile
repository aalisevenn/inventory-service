FROM lpicanco/java11-alpine
ADD target/inventory-service-0.0.1-SNAPSHOT.jar app.jar
COPY ./target/inventory-service-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch d-0.0.1-SNAPSHOT.jar'
ARG JAR_FILE=target/inventory-service-0.0.1-SNAPSHOT.jar.jar


ENTRYPOINT ["java", "-jar", "inventory-service-0.0.1-SNAPSHOT.jar"]