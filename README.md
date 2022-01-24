# springboot-inventory-service-app

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) inventory-service app.
Inventory service will be developed as a simple e-commerce platform. It will serve for functions such as registering new customers,
tracking product stock, placing a new order, list all orders of the customer, viewing the order details.

## Requirements

For building and running the application you need:

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Configuration properties

"inventory-service" provides the following configuration properties.  
These can be configure by your  "application.properties".
```properties

#spring-datasource
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
#spring-jpa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
#spring-h2
spring.h2.console.enabled=true

#server
server.port=8090

```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `src/main/java/com/aliseven/inventoryservice/InventoryServiceApplication.java` class from your IDE.

Alternatively you can use the Docker image.Explain the steps below

#### Building The Image
```
mvn clean install
```
Now our **inventory-service Jar** file is created under the target folder with the format: "target/<final_name>.jar" (or .war based on package in .pom file)

To build the image, we will use **docker build** command and tag it. The last parameter will be the directory, by using dot ("."),
we point to the current directory. So you must run this command on the top level directory of this repository.

```
docker build -t inventory-service-docker .
```
After the image is built, you can check it with the ```docker image ls``` command. An example is as below;

```
docker run -p 8090:8082 inventory-service
```
While our container is running, we can get inside the running container for different purposes like checking out the logging, configuration




Ali Seven- [@aliseven](https://getir.com/) - aalisevenn@gmail.com
Project Link: [https://github.com/aalisevenn/inventory-service](https://github.com/aalisevenn/inventory-service)

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
