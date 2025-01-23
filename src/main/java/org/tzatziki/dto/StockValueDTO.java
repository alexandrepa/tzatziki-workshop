package org.tzatziki.dto;

import org.tzatziki.model.StockValue;

public class StockValueDTO {
    private final long id;
    private int quantity;
    private String description;

    public StockValueDTO(int quantity, long id, String description) {
        this.quantity = quantity;
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public static StockValueDTO fromEntityWithDescription(StockValue stockValue, String description) {
        return new StockValueDTO(stockValue.getQuantity(), stockValue.getId(), description);
    }
}
