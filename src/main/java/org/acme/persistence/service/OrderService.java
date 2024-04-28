package org.acme.persistence.service;

import org.acme.persistence.model.Order;

public interface OrderService {

    void save(Order order);
}
