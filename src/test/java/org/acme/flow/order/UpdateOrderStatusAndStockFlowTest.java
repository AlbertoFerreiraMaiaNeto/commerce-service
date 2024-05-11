package org.acme.flow.order;

import mock.MockOrder;
import mock.MockProduct;
import org.acme.exception.OrderNotFoundException;
import org.acme.exception.OrderStatusInvalidException;
import org.acme.persistence.model.Order;
import org.acme.persistence.service.OrderService;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateOrderStatusAndStockFlowTest {

    private OrderService orderService;

    private ProductService productService;

    private UpdateOrderStatusAndStockFlow updateOrderStatusAndStockFlow;
    private Order order;

    @BeforeEach
    public void setup() {
        this.order = MockOrder.buildOrder();
        this.orderService = Mockito.mock(OrderService.class);
        this.productService = Mockito.mock(ProductService.class);
        this.updateOrderStatusAndStockFlow = new UpdateOrderStatusAndStockFlow(orderService, productService);
    }

    @Test
    void updateOrderStatusAndStockTest() {
        when(this.orderService.findById(any())).thenReturn(order);
        when(this.productService.findAll()).thenReturn(MockProduct.buildProductList());

        this.updateOrderStatusAndStockFlow.updateOrderStatusAndStock(1L, MockOrder.buildStatusOrderAndStockDTO());

        verify(orderService).updateStatus(any(), any());
        verify(productService).updateProductsAmountByNameAndCategory(any());
    }

    @Test
    void mustThrowOrderNotFoundException_whenOrderIsNull() {
        when(this.orderService.findById(any())).thenReturn(null);
        when(this.productService.findAll()).thenReturn(MockProduct.buildProductList());

        assertThrows(OrderNotFoundException.class, ()  ->
                this.updateOrderStatusAndStockFlow.updateOrderStatusAndStock(1L, MockOrder.buildStatusOrderAndStockDTOWithInvalidOrderStatus()));
    }

    @Test
    void mustThrowOrderStatusInvalidException_whenOrderStatusIsInvalid() {
        when(this.orderService.findById(any())).thenReturn(order);
        when(this.productService.findAll()).thenReturn(MockProduct.buildProductList());

        assertThrows(OrderStatusInvalidException.class, ()  ->
                this.updateOrderStatusAndStockFlow.updateOrderStatusAndStock(1L, MockOrder.buildStatusOrderAndStockDTOWithInvalidOrderStatus()));
    }
}