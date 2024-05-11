package org.acme.persistence.service;

import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);

    void update(Product product);

    void delete(Product product);

    Product findProductByNameAndCategory(String name, Category category);

    List<Product> findProductsByCategory(Category category);

    List<Product> findAll();

    void updateProductsAmountByNameAndCategory(List<Product> products);
}
