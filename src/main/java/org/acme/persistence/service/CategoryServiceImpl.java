package org.acme.persistence.service;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Parameters;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.cache.CategoryCacheKeyGenerator;
import org.acme.persistence.model.Category;
import org.acme.persistence.repository.CategoryRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @CacheInvalidate(cacheName = "category-cache", keyGenerator = CategoryCacheKeyGenerator.class)
    public void save(Category category) {
        this.categoryRepository.persist(category);
    }

    @Override
    @CacheInvalidate(cacheName = "category-cache", keyGenerator = CategoryCacheKeyGenerator.class)
    public void update(Category category) {
        this.categoryRepository.update("set description = :description, active = :active where name = :name",
                Parameters.with("name", category.getName()).and("description", category.getDescription()).and("active", category.isActive()));
    }

    @Override
    @CacheResult(cacheName = "category-cache", keyGenerator = CategoryCacheKeyGenerator.class)
    public Category findByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll().stream().toList();
    }
}
