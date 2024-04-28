package org.acme.flow.product.items;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.exception.CategoryNotFoundException;
import org.acme.persistence.model.Category;
import org.acme.persistence.repository.CategoryRepository;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class FindCategoryFlowItem {

    private final CategoryRepository categoryRepository;

    public Category findCategory(String category) {
        var currentCategory = categoryRepository.findByName(category.toUpperCase());

        if(Objects.isNull(currentCategory)) {
            throw new CategoryNotFoundException();
        }

        return currentCategory;
    }
}
