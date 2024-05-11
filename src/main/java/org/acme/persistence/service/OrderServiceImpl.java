package org.acme.persistence.service;

import io.quarkus.panache.common.Parameters;
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

    @Override
    public void updateStatus(Long orderId, String orderStatus) {
            this.orderRepository.update("set orderStatus = :orderStatus where orderId = :orderId",
                    Parameters.with("orderId", orderId).and("orderStatus", orderStatus));
    }

    @Override
    public Order findById(Long orderId) {
        return this.orderRepository.findById(orderId);
    }
}
