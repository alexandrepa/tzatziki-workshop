package org.tzatziki;

import com.decathlon.tzatziki.steps.KafkaSteps;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@Configuration
@ComponentScan("com.decathlon")
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;


/*    @Bean("avroKafkaTemplate")
    public KafkaTemplate<String, GenericRecord> avroKafkaTemplate() {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", KafkaSteps.schemaRegistryUrl());
        props.put("security.protocol", SecurityProtocol.PLAINTEXT.name());
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
    }*/
}
