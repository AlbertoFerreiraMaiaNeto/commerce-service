package org.acme.flow.product;

import mock.MockCategory;
import mock.MockProduct;
import org.acme.exception.ProductNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateProductFlowTest {

    private ProductService productService;

    private FindCategoryFlowItem findCategoryFlowItem;

    private UpdateProductFlow updateProductFlow;
    private Category category;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setup() {
        this.product = MockProduct.buildProduct();
        this.category = MockCategory.buildCategory();
        this.productDTO = MockProduct.buildProductDTO();
        this.findCategoryFlowItem = Mockito.mock(FindCategoryFlowItem.class);
        this.productService = Mockito.mock(ProductService.class);
        this.updateProductFlow = new UpdateProductFlow(productService, findCategoryFlowItem);
    }

    @Test
    void updateProductTest() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(productDTO.getName(), category)).thenReturn(product);

        var output = updateProductFlow.updateProduct(productDTO, category.getName(), product.getName());

        verify(productService).update(any());
        assertEquals(productDTO.getName(), output.getName());

    }

    @Test
    void mustThrowProductNotFoundException_whenCurrentProductIsNull() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(productDTO.getName(), category)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, ()  -> updateProductFlow.updateProduct(productDTO, category.getName(), product.getName()));
    }
}