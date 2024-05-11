package org.acme.flow.product.items;

import mock.MockCategory;
import org.acme.exception.CategoryNotFoundException;
import org.acme.persistence.model.Category;
import org.acme.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindCategoryFlowItemTest {

    private CategoryRepository categoryRepository;

    private FindCategoryFlowItem findCategoryFlowItem;

    private Category category;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.categoryRepository = Mockito.mock(CategoryRepository.class);
        this.findCategoryFlowItem = new FindCategoryFlowItem(categoryRepository);
    }

    @Test
    void findCategoryTest() {
        when(this.categoryRepository.findByName(any())).thenReturn(category);

        var output = this.findCategoryFlowItem.findCategory(category.getName());

        assertEquals(category.getName(), output.getName());

    }

    @Test
    void mustThrowCategoryNotFoundException_whenCurrentCategoryIsNull() {
        when(this.categoryRepository.findByName(any())).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, ()  -> this.findCategoryFlowItem.findCategory(category.getName()));
    }

}