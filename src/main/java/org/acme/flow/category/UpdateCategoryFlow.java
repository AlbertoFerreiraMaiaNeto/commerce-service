package org.acme.flow.category;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.exception.CategoryNotFoundException;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class UpdateCategoryFlow {

    private final CategoryService categoryService;

    @Transactional
    public Category updateCategory(Category category, String categoryName) {
        var currentCategory = categoryService.findByName(categoryName);

        if(Objects.isNull(currentCategory)) {
            throw new CategoryNotFoundException();
        }

        currentCategory.setDescription(category.getDescription());
        currentCategory.setActive(category.isActive());

        this.categoryService.update(currentCategory);

        return currentCategory;
    }
}
