package org.acme.persistence.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.persistence.model.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public Order findById(Long orderId) {
        return find("id", orderId).firstResult();
    }
}
