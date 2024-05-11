package org.acme.controller;

import mock.MockOrder;
import org.acme.flow.order.CreateOrderFlow;
import org.acme.flow.order.UpdateOrderStatusAndStockFlow;
import org.acme.persistence.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    private CreateOrderFlow createOrderFlow;
    private UpdateOrderStatusAndStockFlow updateOrderStatusAndStockFlow;

    private OrderController orderController;

    private Order order;

    @BeforeEach
    public void setup() {
        this.order = MockOrder.buildOrder();
        this.createOrderFlow = Mockito.mock(CreateOrderFlow.class);
        this.updateOrderStatusAndStockFlow = Mockito.mock(UpdateOrderStatusAndStockFlow.class);
        this.orderController = new OrderController(createOrderFlow, updateOrderStatusAndStockFlow);
    }

    @Test
    void createOrder() {
        when(this.createOrderFlow.createOrder(any())).thenReturn(MockOrder.buildOrderResponseDTO());

        var response = this.orderController.createOrder(any());

        assertNotNull(response);
        assertEquals(order.getOrderOwner(), response.getOrderOwner());
    }

    @Test
    void updateStatusOrderAndStock() {
        this.orderController.updateStatusOrderAndStock(any(), any());

        Mockito.verify(this.updateOrderStatusAndStockFlow, times(1)).updateOrderStatusAndStock(any(), any());
    }
}