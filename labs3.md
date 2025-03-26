# TDD Unleash Your JAVA Testing Superpowers with Tzatziki

## Pre-requisites

In order to test kafka we need to start an embedded kafka server. Tzatziki provides a module that can start an embedded
kafka server for you and cucumber steps to interact with it.
Add the following dependency to your `pom.xml` file:

```xml

<dependency>
   <groupId>com.decathlon.tzatziki</groupId>
   <artifactId>tzatziki-spring-kafka</artifactId>
   <version>1.5.3</version>
   <scope>test</scope>
</dependency>
```

In the TestSteps Initialization, you need to add the following code to start the embedded kafka server:

```java
KafkaSteps.start();
```

You can do it right after the postgres start.

You also need to bind the embedded kafka server to you spring app adding this to the TestPropertyValues :

```java
"spring.kafka.bootstrap-servers="+KafkaSteps.bootstrapServers(),
"spring.kafka.properties.schema.registry.url="+KafkaSteps.schemaRegistryUrl(),
"spring.kafka.consumer.auto-offset-reset=earliest"
```

We already configured a KafkaProducer in KafkaProducerConfig for you
that will be used by the steps to send messages to Kafka topics. 

**You just have to uncomment it the class file (KafkaProducerConfig.java).**

That's it, you can now start writing your tests. 

## 3. As a Product Manager, I want to consume a Kafka message and modify our datas in dB

> I want to listen `stock` kafka topic and update information in dB from the message
>
> Modify in dB quantity from the given id given by the message
>
 ```json
{
"id": 1,
"quantity": 100
}
```


### Create a Test which will test the GET endpoint that displays datas from the database
1. For this test we will see 2 notions : the background part and how to consume a new kafka message
   1. At the beginning of the file and before all your scenarios you can add a background part that will be played before each scenario in your file (very usefull when you have the same starter part)
```gherkin
Background: 
  * that the table_name table will contain:
    | column1 | column2 | column3 |
    | daddy   | 666     | true    |
    | mummy   | 987654  | false   |
```
2. In this background, we can define our avro schema:
```gherkin
* this avro schema:
"""yml
      type: record
      name: stockValue
      fields:
        - name: id
          type: long
        - name: quantity
          type: int
      """
```

3. Create the scenario and <span style="color:Yellow">**populate** the stock_value table in the background.</span> Then we will consume a message in a kafka topic like this : 
```gherkin
When this stockValue is consumed from the stock topic:
"""yml
      id: 1
      name: bob
"""
```

4. Check in the dB that values has been changed ! 