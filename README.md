# TD Unleash Your JAVA Testing Superpowers with Tzatziki

## Pre-requisites

### Check JAVA version is higher or equals to  17.0.0
```shell
   java --version
```

### Check Maven version is higher or equals to  3.6.3
```shell
   mvn --version
```

### Check that Docker is running
```shell
   docker ps
```

### Install Maven dependencies with the plugin or by running
```shell
   mvn clean install
```



## Goal of the workshop

The goal of this workshop is to introduce you to the Tzatziki framework and to show you how to use it to test your Java
applications.
In order to do this, we will use a simple Spring Boot application that manages stock movements and we will write tests
for it.
The workshop is divided into 3 parts:

- The first part will show you how to write tests for a simple REST API. You will insert data into the database and
  check that the data is correctly returned by the API.
- The second part will show you how to mock an external API and check that the data returned by this API is correctly
  integrated into your application.
- The third part will show you how to test a Kafka consumer by sending stock movements to a Kafka topic and checking
  that the data is correctly integrated into your database.

## Architecture of the application

The application is a simple Spring Boot application that manages stock movements.
Most part of the code is already written and you will only have to write tests for it.

A stock movement is represented by the following entity class:

```java
public class StockValue {
    private Long id;
    private Integer quantity;
}
```

Id is the identifier of the article and quantity is the quantities available in stock.

A stock movement with description is represented by the following DTO class:

```java
public class StockValueDTO {
    private Long id;
    private Integer quantity;
    private String description;
}
```

The description field is retrieved by calling the Referential API.

It has 3 endpoints:

- GET /stock that returns all stock movements in the database (StockValue)
- POST /stock that adds a stock movement to the database (StockValue)
- GET /stock-with-description that returns all stock movements in the database with a description field (StockValueDTO)

The application uses a PostgreSQL database to store the stock movements and a Kafka topic to send and receive stock
movements.

## How to add Tzatziki to your project

### Maven dependencies

To add Tzatziki to your project, you need to add the following dependencies to your pom.xml

- `tzatziki-http` if you want to test a REST API
- `tzatziki-spring-jpa` if you want to test a Spring JPA repository
- `tzatziki-spring-kafka` if you want to test a Kafka consumer or producer

Dependencies for the first part of the workshop are already added to the pom.xml file (tzatziki-http and
tzatziki-spring-jpa).

You can just do a `mvn clean install` to install the dependencies.

### Configuration

To run the tests, we need to declare a Cucumber runner class.
You can already find one (StockTestRunner) in the test folder. See the extraGlue parameter in the @CucumberOptions
annotation, it is used to declare the package where the step definitions provided by Tzatziki are located.

As we are going to test database interactions, we need to start a database instance before running the tests and plug it
to the application.
To do this, in TestSteps class we are going to use Testcontainers that will start a PostgreSQL database instance and set
url and credentials in the spring app properties.

We use @ContextConfiguration and declare an Initializer class to start the database before running the tests.

<span style="color:Yellow">**Warning**</span>

All examples in labs are example ! You don't have to use all but you have to adapt steps with our current project !  