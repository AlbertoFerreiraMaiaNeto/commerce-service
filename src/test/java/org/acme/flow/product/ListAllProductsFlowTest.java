package org.acme.flow.product;

import mock.MockCategory;
import mock.MockProduct;
import org.acme.exception.CategoryNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static mock.MockCategory.CATEGORY_NAME_TEST;
import static mock.MockCategory.CATEGORY_NAME_TEST2;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ListAllProductsFlowTest {


    private ProductService productService;

    private FindCategoryFlowItem findCategoryFlowItem;

    private ListAllProductsFlow listAllProductsFlow;

    @BeforeEach
    public void setup() {
        this.findCategoryFlowItem = Mockito.mock(FindCategoryFlowItem.class);
        this.productService = Mockito.mock(ProductService.class);
        this.listAllProductsFlow = new ListAllProductsFlow(productService, findCategoryFlowItem);
    }

    @Test
    void mustListAllProducts_whenXCategoryIsNull() {
        when(this.productService.findAll()).thenReturn(MockProduct.buildProductList());

        var output = this.listAllProductsFlow.listAllProducts(null);

        assertTrue(output.size() == 2);
    }

    @Test
    void mustListAllCategories_whenXCategoryIsNotNull() {
        var category = MockCategory.buildCategory();
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductsByCategory(category)).thenReturn(MockProduct.buildProductListWithActivatedCategory());

        var output = this.listAllProductsFlow.listAllProducts(CATEGORY_NAME_TEST);

        assertTrue(output.size() == 1);
    }

    @Test
    void mustThrowCategoryNotFoundException_whenCategoryIsDeactivated() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(MockCategory.buildDeactivatedCategory());
        when(this.productService.findAll()).thenReturn(MockProduct.buildProductListWithDeactivatedCategory());

        assertThrows(CategoryNotFoundException.class, ()  -> listAllProductsFlow.listAllProducts(CATEGORY_NAME_TEST2));
    }
}