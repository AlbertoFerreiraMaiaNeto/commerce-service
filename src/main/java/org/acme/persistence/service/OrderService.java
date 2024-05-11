package org.acme.persistence.service;

import org.acme.persistence.model.Order;

public interface OrderService {

    Order findById(Long orderId);

    void save(Order order);

    void updateStatus(Long orderId, String statusOrder);
}
