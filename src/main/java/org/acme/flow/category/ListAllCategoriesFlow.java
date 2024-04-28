package org.acme.flow.category;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@Singleton
public class ListAllCategoriesFlow {

    private final CategoryService categoryService;
    
    public List<Category> listAllCategories() {

        var categoryList = categoryService.findAll();

        return categoryList.stream().filter(Category::isActive).toList();
    }
}
