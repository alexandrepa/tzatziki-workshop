package org.tzatziki.kafka;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tzatziki.model.StockValue;
import org.tzatziki.repository.StockValueRepository;

import java.util.Optional;

@Component
@ConditionalOnProperty(
        value = "spring.kafka.consumer.auto-offset-reset",
        havingValue = "earliest")
public class StockListener {
    @Autowired
    StockValueRepository stockValueRepository;

    @KafkaListener(topics = "stock", groupId = "stock-group-id", containerFactory = "defaultFactory")
    public void receivedStock(GenericRecord message) {
        Optional<StockValue> stockValue = stockValueRepository.findById(Long.valueOf(message.get("id").toString()));
        stockValue.ifPresent(value -> {
            value.setQuantity(Integer.valueOf(message.get("quantity").toString()));
            stockValueRepository.save(value);
        });
    }

}
