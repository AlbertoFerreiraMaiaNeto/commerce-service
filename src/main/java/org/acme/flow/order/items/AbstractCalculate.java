package org.acme.flow.order.items;

import org.acme.persistence.dto.KafkaOrderDTO;

import java.math.BigDecimal;

public abstract class AbstractCalculate {

    abstract KafkaOrderDTO calculate(KafkaOrderDTO kafkaOrderDTO);

    double calculateTotal(BigDecimal unitPrice, int amount) {
        return unitPrice.doubleValue() * amount;
    }
}
