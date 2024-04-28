package org.acme.persistence.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;

import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public Product findProductByNameAndCategory(String name, Category category) {
        return find("name = ?1 and category = ?2", name.toUpperCase(), category).firstResult();
    }

    public List<Product> findProductsByCategory(Category category) {
        return list("category", category);
    }
}
