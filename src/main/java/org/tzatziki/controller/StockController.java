package org.tzatziki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tzatziki.client.StockReferentialClient;
import org.tzatziki.dto.StockValueDTO;
import org.tzatziki.model.StockValue;
import org.tzatziki.repository.StockValueRepository;

import java.util.List;

@RestController
public class StockController {
    @Autowired
    StockValueRepository stockValueRepository;

    @Autowired
    StockReferentialClient referentialClient;

    @GetMapping("/stock")
    public ResponseEntity<List<StockValue>> getAllStockValues() {
        /*
        List<StockValue> stockValues = stockValueRepository.findAll();

        if (stockValues.isEmpty()) {*/
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        /*}

        return new ResponseEntity<>(stockValues, HttpStatus.OK);*/
    }

    @PostMapping("/stock")
    public ResponseEntity<StockValue> postStockValue(@RequestBody StockValue input) {
        StockValue savedEntity = stockValueRepository
                .save(input);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @GetMapping("/stock-with-description")
    public ResponseEntity<List<StockValueDTO>> getAllStockValuesWithDescription() {
        List<StockValue> stockValues = stockValueRepository.findAll();

        if (stockValues.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(stockValues.stream()
                .map(stockValue -> StockValueDTO.fromEntityWithDescription(stockValue, referentialClient.getStockDescription(stockValue.getId()).getDescription()))
                .toList(), HttpStatus.OK);
    }
}
