package org.acme.flow.product;

import mock.MockCategory;
import mock.MockProduct;
import org.acme.exception.ProductAlreadyExistsException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Category;
import org.acme.persistence.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateProductFlowTest {

    private ProductService productService;

    private FindCategoryFlowItem findCategoryFlowItem;

    private CreateProductFlow createProductFlow;
    private Category category;
    private ProductDTO productDTO;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.productDTO = MockProduct.buildProductDTO();
        this.findCategoryFlowItem = Mockito.mock(FindCategoryFlowItem.class);
        this.productService = Mockito.mock(ProductService.class);
        this.createProductFlow = new CreateProductFlow(productService, findCategoryFlowItem);
    }

    @Test
    void createProductTest() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(productDTO.getName(), category)).thenReturn(null);

        var output = createProductFlow.createProduct(productDTO);

        verify(productService).save(any());
        assertEquals(productDTO.getName(), output.getName());

    }

    @Test
    void mustThrowProductAlreadyExistsException_whenCurrentProductIsNotNull() {
        when(this.findCategoryFlowItem.findCategory(any())).thenReturn(category);
        when(this.productService.findProductByNameAndCategory(productDTO.getName(), category)).thenReturn(MockProduct.buildProduct());

        assertThrows(ProductAlreadyExistsException.class, ()  -> createProductFlow.createProduct(productDTO));
    }
}