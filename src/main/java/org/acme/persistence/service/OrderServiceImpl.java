package org.acme.persistence.service;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.persistence.model.Order;
import org.acme.persistence.repository.OrderRepository;

@Singleton
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        this.orderRepository.persist(order);
    }
}
