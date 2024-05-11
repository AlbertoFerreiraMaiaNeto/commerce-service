package org.acme.flow.order.items;

import mock.MockOrder;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateTotalOrderPriceFlowItemTest {

    private CalculateTotalOrderPriceFlowItem calculateTotalOrderPriceFlowItem;
    private KafkaOrderDTO kafkaOrderDTO;

    @BeforeEach
    public void setup() {
        this.kafkaOrderDTO = MockOrder.buildKafkaOrderPayload();
        this.calculateTotalOrderPriceFlowItem = new CalculateTotalOrderPriceFlowItem();
    }

    @Test
    void calculateTotalOrderPrice() {

        this.calculateTotalOrderPriceFlowItem.calculateTotalOrderPrice(kafkaOrderDTO);

        assertEquals(200, kafkaOrderDTO.getTotalOrderPrice());
    }
}