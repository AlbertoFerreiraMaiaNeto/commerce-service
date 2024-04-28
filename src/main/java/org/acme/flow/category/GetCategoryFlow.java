package org.acme.flow.category;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.exception.CategoryNotFoundException;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class GetCategoryFlow {

    private final CategoryService categoryService;

    public Category getCategory(String categoryName) {
        var currentCategory = categoryService.findByName(categoryName);

        if(Objects.isNull(currentCategory)) {
            throw new CategoryNotFoundException();
        }

        return currentCategory;
    }
}
