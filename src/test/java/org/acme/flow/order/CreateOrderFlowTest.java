package org.acme.flow.order;

import mock.MockOrder;
import org.acme.flow.order.items.CalculatePricePerOrderProductFlowItem;
import org.acme.flow.order.items.CalculateTotalOrderPriceFlowItem;
import org.acme.flow.order.items.ValidateProductAndAmountFlowItem;
import org.acme.kafka.KafkaOrderProducer;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.acme.persistence.dto.OrderDTO;
import org.acme.persistence.service.OrderService;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateOrderFlowTest {

    private OrderService orderService;

    private ProductService productService;

    private KafkaOrderProducer kafkaOrderProducer;

    private ValidateProductAndAmountFlowItem validateProductAndAmountFlowItem;

    private CalculatePricePerOrderProductFlowItem calculatePricePerOrderProductFlowItem;

    private CalculateTotalOrderPriceFlowItem calculateTotalOrderPriceFlowItem;

    private CreateOrderFlow createOrderFlow;

    private OrderDTO orderDTO;

    private KafkaOrderDTO kafkaOrderDTO;

    @BeforeEach
    public void setup() {
        this.kafkaOrderDTO = MockOrder.buildKafkaOrderPayload();
        this.orderDTO = MockOrder.buildOrderDTO();
        this.kafkaOrderProducer = Mockito.mock(KafkaOrderProducer.class);
        this.validateProductAndAmountFlowItem = Mockito.mock(ValidateProductAndAmountFlowItem.class);
        this.calculatePricePerOrderProductFlowItem = Mockito.mock(CalculatePricePerOrderProductFlowItem.class);
        this.calculateTotalOrderPriceFlowItem = Mockito.mock(CalculateTotalOrderPriceFlowItem.class);
        this.orderService = Mockito.mock(OrderService.class);
        this.productService = Mockito.mock(ProductService.class);
        this.createOrderFlow = new CreateOrderFlow(orderService, productService, kafkaOrderProducer,
                validateProductAndAmountFlowItem, calculatePricePerOrderProductFlowItem, calculateTotalOrderPriceFlowItem);
    }

    @Test
    void createOrderTest() {
        when(this.productService.findAll()).thenReturn(any());
        when(this.validateProductAndAmountFlowItem.validate(orderDTO, any())).thenReturn(kafkaOrderDTO);
        when(this.calculatePricePerOrderProductFlowItem.calculatePricePerProduct(kafkaOrderDTO)).thenReturn(kafkaOrderDTO);
        when(this.calculateTotalOrderPriceFlowItem.calculateTotalOrderPrice(kafkaOrderDTO)).thenReturn(kafkaOrderDTO);

        var output = createOrderFlow.createOrder(orderDTO);

        verify(orderService).save(any());
        verify(kafkaOrderProducer).publish(any());

        assertEquals(orderDTO.getOrderOwner(), output.getOrderOwner());
        assertEquals(orderDTO.getOrderOwnerEmail(), output.getOrderOwnerEmail());

    }
}