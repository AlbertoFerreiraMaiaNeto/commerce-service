package org.acme.persistence.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.persistence.model.Category;


@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    public Category findByName(String name) {
        return find("name", name.toUpperCase()).firstResult();
    }
}
