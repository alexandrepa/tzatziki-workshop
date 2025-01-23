package org.tzatziki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tzatziki.model.StockValue;

public interface StockValueRepository extends JpaRepository<StockValue, Long> {
}
