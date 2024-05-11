package org.acme.persistence.service;

import io.quarkus.panache.common.Parameters;
import mock.MockOrder;
import org.acme.enums.OrderStatus;
import org.acme.persistence.model.Order;
import org.acme.persistence.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static mock.MockOrder.ORDER_OWNER_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private OrderRepository orderRepository;

    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    public void setup() {
        this.order = MockOrder.buildOrder();
        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void save() {
        this.orderService.save(order);

        Mockito.verify(orderRepository, times(1)).persist(order);
    }

    @Test
    void updateStatus() {
        this.orderService.updateStatus(1L, OrderStatus.CONFIRMED.name());

        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Parameters> parametersCaptor = ArgumentCaptor.forClass(Parameters.class);
        Mockito.verify(orderRepository).update(queryCaptor.capture(), parametersCaptor.capture());

        assertEquals("set orderStatus = :orderStatus where orderId = :orderId", queryCaptor.getValue());
    }

    @Test
    void findById() {
        when(this.orderRepository.findById(any())).thenReturn(order);

        var output = this.orderService.findById(any());

        assertNotNull(output);
        assertEquals(ORDER_OWNER_TEST, output.getOrderOwner());
    }
}