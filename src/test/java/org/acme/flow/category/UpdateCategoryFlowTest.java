package org.acme.flow.category;

import mock.MockCategory;
import org.acme.exception.CategoryNotFoundException;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static mock.MockCategory.CATEGORY_NAME_TEST;
import static mock.MockCategory.CATEGORY_NAME_TEST2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UpdateCategoryFlowTest {


    private CategoryService categoryService;

    private UpdateCategoryFlow updateCategoryFlow;

    private Category category;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.categoryService = Mockito.mock(CategoryService.class);
        this.updateCategoryFlow = new UpdateCategoryFlow(categoryService);
    }

    @Test
    void mustUpdateDescriptionAndStatusCategoryTest() {
        when(this.categoryService.findByName(any())).thenReturn(category);

        var categoryModified =  Category.builder()
                .name(CATEGORY_NAME_TEST)
                .description(CATEGORY_NAME_TEST2)
                .active(false)
                .build();

        var output = this.updateCategoryFlow.updateCategory(categoryModified, category.getName());

        assertEquals(categoryModified.getDescription(), output.getDescription());
        assertEquals(categoryModified.isActive(), output.isActive());

    }

    @Test
    void mustUpdateStatusCategoryTest() {
        when(this.categoryService.findByName(any())).thenReturn(category);

        var categoryModified = Category.builder()
                .name(CATEGORY_NAME_TEST)
                .description(CATEGORY_NAME_TEST)
                .active(false)
                .build();

        var output = this.updateCategoryFlow.updateCategory(categoryModified, category.getName());

        assertEquals(category.getDescription(), output.getDescription());
        assertEquals(categoryModified.isActive(), output.isActive());

    }

    @Test
    void mustUpdateDescriptionCategoryTest() {
        when(this.categoryService.findByName(any())).thenReturn(category);

        var categoryModified = Category.builder()
                .name(CATEGORY_NAME_TEST)
                .description(CATEGORY_NAME_TEST2)
                .active(true)
                .build();

        var output = this.updateCategoryFlow.updateCategory(categoryModified, category.getName());

        assertEquals(category.getDescription(), output.getDescription());
        assertEquals(categoryModified.isActive(), output.isActive());

    }

    @Test
    void mustThrowCategoryNotFoundException() {
        when(this.categoryService.findByName(any())).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, ()  -> updateCategoryFlow.updateCategory(category, category.getName()));
    }


}