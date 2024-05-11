package org.acme.flow.category;


import org.acme.persistence.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import mock.MockCategory;
import java.util.ArrayList;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.mockito.Mockito.when;

class ListAllCategoriesFlowTest {

    private CategoryService categoryService;

    private ListAllCategoriesFlow listAllCategoriesFlow;

    @BeforeEach
    public void setup() {
        this.categoryService = Mockito.mock(CategoryService.class);
        this.listAllCategoriesFlow = new ListAllCategoriesFlow(categoryService);
    }

    @Test
    void mustReturn_OneCategoryActivated() {
        when(this.categoryService.findAll()).thenReturn(MockCategory.buildCategoryListWithActivatedAndDeactivatedCategories());

        var output = listAllCategoriesFlow.listAllCategories();

        assertTrue(output.size() == 1);
        assertTrue(output.get(0).isActive());

    }

    @Test
    void mustReturn_TwoCategoriesActivated() {
        when(this.categoryService.findAll()).thenReturn(MockCategory.buildCategoryListWithActivatedCategories());

        var output = listAllCategoriesFlow.listAllCategories();

        assertTrue(output.size() == 2);
        assertTrue(output.get(0).isActive());
        assertTrue(output.get(1).isActive());
    }

    @Test
    void mustReturn_EmptyList() {
        when(this.categoryService.findAll()).thenReturn(new ArrayList<>());

        var output = listAllCategoriesFlow.listAllCategories();

        assertTrue(output.isEmpty());
    }
}