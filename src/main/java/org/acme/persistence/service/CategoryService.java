package org.acme.persistence.service;

import org.acme.persistence.model.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);

    void update(Category category);

    Category findByName(String name);

    List<Category> findAll();
}
