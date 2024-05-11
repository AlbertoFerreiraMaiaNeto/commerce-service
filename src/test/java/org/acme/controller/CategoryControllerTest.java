package org.acme.controller;

import mock.MockCategory;
import org.acme.flow.category.CreateCategoryFlow;
import org.acme.flow.category.GetCategoryFlow;
import org.acme.flow.category.ListAllCategoriesFlow;
import org.acme.flow.category.UpdateCategoryFlow;
import org.acme.persistence.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static mock.MockCategory.CATEGORY_NAME_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

    private CreateCategoryFlow createCategoryFlow;

    private UpdateCategoryFlow updateCategoryFlow;

    private GetCategoryFlow getCategoryFlow;

    private ListAllCategoriesFlow listAllCategoriesFlow;

    private CategoryController categoryController;

    private Category category;

    @BeforeEach
    public void setup() {
        this.createCategoryFlow = Mockito.mock(CreateCategoryFlow.class);
        this.updateCategoryFlow = Mockito.mock(UpdateCategoryFlow.class);
        this.getCategoryFlow = Mockito.mock(GetCategoryFlow.class);
        this.listAllCategoriesFlow = Mockito.mock(ListAllCategoriesFlow.class);
        this.category = MockCategory.buildCategory();
        this.categoryController = new CategoryController(createCategoryFlow, updateCategoryFlow, getCategoryFlow, listAllCategoriesFlow);
    }

    @Test
    void createCategory() {
        when(this.createCategoryFlow.createCategory(any())).thenReturn(MockCategory.buildCategory());

        var response = this.categoryController.createCategory(MockCategory.buildCategory());

        assertNotNull(response);
        assertEquals(category.getName(), response.getName());
    }

    @Test
    void updateCategory() {
        when(this.updateCategoryFlow.updateCategory(any(), any())).thenReturn(MockCategory.buildCategory());

        var response = this.categoryController.updateCategory(MockCategory.buildCategory(), CATEGORY_NAME_TEST);

        assertNotNull(response);
        assertEquals(category.getName(), response.getName());
    }

    @Test
    void getCategory() {
        when(this.getCategoryFlow.getCategory(any())).thenReturn(MockCategory.buildCategory());

        var response = this.categoryController.getCategory(any());

        assertNotNull(response);
        assertEquals(category.getName(), response.getName());
    }

    @Test
    void listAllCategories() {
        when(this.listAllCategoriesFlow.listAllCategories()).thenReturn(MockCategory.buildCategoryListWithActivatedAndDeactivatedCategories());

        var response = this.categoryController.listAllCategories();

        assertNotNull(response);
        assertEquals(category.getName(), response.get(0).getName());
    }
}