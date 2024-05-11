package org.acme.flow.product;

import mock.MockCategory;
import mock.MockProduct;
import org.acme.exception.ProductNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteProductFlowTest {

    private ProductService productService;

    private FindCategoryFlowItem findCategoryFlowItem;

    private DeleteProductFlow deleteProductFlow;
    private Category category;
    private ProductDTO product;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.product = MockProduct.buildProductDTO();
        this.findCategoryFlowItem = Mockito.mock(FindCategoryFlowItem.class);
        this.productService = Mockito.mock(ProductService.class);
        this.deleteProductFlow = new DeleteProductFlow(productService, findCategoryFlowItem);
    }

    @Test
    void deleteProductTest() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(product.getName(), category)).thenReturn(MockProduct.buildProduct());

        this.deleteProductFlow.deleteProduct(category.getName(), product.getName());

        verify(productService).delete(any());
    }

    @Test
    void mustThrowProductNotFoundException_whenCurrentProductIsNull() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(product.getName(), category)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, ()  -> deleteProductFlow.deleteProduct(category.getName(), product.getName()));
    }
}