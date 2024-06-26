package org.acme.flow.category;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.exception.CategoryAlreadyExistsException;
import mock.MockCategory;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class CreateCategoryFlowTest {


    private CategoryService categoryService;

    private CreateCategoryFlow categoryFlow;

    private Category category;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.categoryService = Mockito.mock(CategoryService.class);
        this.categoryFlow = new CreateCategoryFlow(categoryService);
    }

    @Test
    void createCategoryTest() {
        when(this.categoryService.findByName(any())).thenReturn(null);

        var output = categoryFlow.createCategory(category);

        verify(categoryService).save(any());
        assertEquals(category.getName(), output.getName());

    }

    @Test
    void mustThrowCategoryAlreadyExistsException() {
        when(this.categoryService.findByName(any())).thenReturn(category);

        assertThrows(CategoryAlreadyExistsException.class, ()  -> categoryFlow.createCategory(category));
    }

}