package org.acme.flow.order.items;

import mock.MockOrder;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatePricePerOrderProductFlowItemTest {

    private CalculatePricePerOrderProductFlowItem calculatePricePerOrderProductFlowItem;
    private KafkaOrderDTO kafkaOrderDTO;

    @BeforeEach
    public void setup() {
        this.kafkaOrderDTO = MockOrder.buildKafkaOrderPayload();
        this.calculatePricePerOrderProductFlowItem = new CalculatePricePerOrderProductFlowItem();
    }

    @Test
    void calculateTotalPricePerProduct() {

        this.calculatePricePerOrderProductFlowItem.calculatePricePerProduct(kafkaOrderDTO);

        assertEquals(200, kafkaOrderDTO.getConfirmedProducts().get(0).getTotalPricePerProduct());
    }
}