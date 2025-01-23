package org.tzatziki.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class StockReferentialClient {
    private final RestClient restClient;

    public StockReferentialClient(@Value("${stock.referential.url:}") String stockReferentialUrl) {
        restClient = RestClient.builder()
                .baseUrl(stockReferentialUrl)
                .build();
    }

    public StockDescriptionDTO getStockDescription(Long id) {

        StockDescriptionDTO stockDescriptionDTO = restClient.get()
                .uri("/description/" + id)
                .retrieve()
                .body(StockDescriptionDTO.class);
        return stockDescriptionDTO;
    }
}
