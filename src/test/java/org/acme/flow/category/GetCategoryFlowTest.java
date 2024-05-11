package org.acme.flow.category;

import mock.MockCategory;
import org.acme.exception.CategoryNotFoundException;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetCategoryFlowTest {

    private CategoryService categoryService;

    private GetCategoryFlow getCategoryFlow;

    private Category category;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.categoryService = Mockito.mock(CategoryService.class);
        this.getCategoryFlow = new GetCategoryFlow(categoryService);
    }

    @Test
    void mustReturnCategoryTest() {
        when(this.categoryService.findByName(any())).thenReturn(category);

        var output = getCategoryFlow.getCategory(category.getName());

        assertEquals(category.getName(), output.getName());

    }

    @Test
    void mustThrowCategoryNotFoundException() {
        when(this.categoryService.findByName(any())).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, ()  -> getCategoryFlow.getCategory(category.getName()));
    }

}