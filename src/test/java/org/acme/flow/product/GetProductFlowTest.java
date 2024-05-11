package org.acme.flow.product;

import mock.MockCategory;
import mock.MockProduct;
import org.acme.exception.CategoryNotFoundException;
import org.acme.exception.ProductNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetProductFlowTest {


    private ProductService productService;

    private FindCategoryFlowItem findCategoryFlowItem;

    private GetProductFlow getProductFlow;
    private Category category;
    private Product product;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.product = MockProduct.buildProduct();
        this.findCategoryFlowItem = Mockito.mock(FindCategoryFlowItem.class);
        this.productService = Mockito.mock(ProductService.class);
        this.getProductFlow = new GetProductFlow(productService, findCategoryFlowItem);
    }

    @Test
    void getProductTest() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(product.getName(), category)).thenReturn(MockProduct.buildProduct());

        var output = this.getProductFlow.getProduct(category.getName(), product.getName());

        assertEquals(product.getName(), output.getName());
        assertTrue(product.getCategory().isActive());
    }

    @Test
    void mustThrowProductNotFoundException_whenCurrentProductIsNull() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(product.getName(), category)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, ()  -> getProductFlow.getProduct(category.getName(), product.getName()));
    }

    @Test
    void mustThrowCategoryNotFoundException_whenCategoryIsDeactivated() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(product.getName(), category)).thenReturn(MockProduct.buildProductWithDeactivatedCategory());

        assertThrows(CategoryNotFoundException.class, ()  -> getProductFlow.getProduct(category.getName(), product.getName()));
    }
}