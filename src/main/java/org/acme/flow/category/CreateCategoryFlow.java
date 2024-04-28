package org.acme.flow.category;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.exception.CategoryAlreadyExistsException;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class CreateCategoryFlow {

    private final CategoryService categoryService;

    @Transactional
    public Category createCategory (Category category) {
        var currentCategory = categoryService.findByName(category.getName());

        if(Objects.nonNull(currentCategory)) {
            throw new CategoryAlreadyExistsException();
        }

        category.setName(category.getName().toUpperCase());

        this.categoryService.save(category);

        return category;
    }
}
