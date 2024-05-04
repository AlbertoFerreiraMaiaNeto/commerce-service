package org.acme.persistence.service;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Parameters;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.cache.ProductCacheKeyGenerator;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;
import org.acme.persistence.repository.ProductRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @CacheInvalidate(cacheName = "product-cache", keyGenerator = ProductCacheKeyGenerator.class)
    public void save(Product product) {
        this.productRepository.persist(product);
    }

    @Override
    @CacheInvalidate(cacheName = "product-cache", keyGenerator = ProductCacheKeyGenerator.class)
    public void update(Product product) {
        this.productRepository.update(("set description = :description, unitPrice = :unitPrice, amount = :amount where name = :name and category = :category"),
                Parameters.with("name", product.getName())
                        .and("category", product.getCategory())
                        .and("description", product.getDescription())
                        .and("unitPrice", product.getUnitPrice())
                        .and("amount", product.getAmount()));
    }

    @Override
    @CacheInvalidate(cacheName = "product-cache", keyGenerator = ProductCacheKeyGenerator.class)
    public void delete(Product product) {
        this.productRepository.delete(product);
    }

    @Override
    @CacheResult(cacheName = "product-cache", keyGenerator = ProductCacheKeyGenerator.class)
    public Product findProductByNameAndCategory(String name, Category category) {
        return this.productRepository.findProductByNameAndCategory(name, category);
    }

    @Override
    public List<Product> findProductsByCategory(Category category) {
        return this.productRepository.findProductsByCategory(category);
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll().stream().toList();
    }

    @Override
    public void updateProductsAmountByNameAndCategory(List<Product> products) {
        products.forEach(product -> {
            this.productRepository.update(("set amount = :amount where name = :name and category = :category"),
                    Parameters.with("name", product.getName())
                            .and("category", product.getCategory())
                            .and("amount", product.getAmount()));
            invalidateProductsCache(product);
        });
    }


    @CacheInvalidate(cacheName = "product-cache", keyGenerator = ProductCacheKeyGenerator.class)
    public void invalidateProductsCache(Product product) {}
}
