package org.acme.controller;

import mock.MockProduct;
import org.acme.flow.product.*;
import org.acme.persistence.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static mock.MockCategory.CATEGORY_NAME_TEST;
import static mock.MockProduct.PRODUCT_NAME_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    private CreateProductFlow createProductFlow;
    private UpdateProductFlow updateProductFlow;
    private GetProductFlow getProductFlow;
    private ListAllProductsFlow listAllProductsFlow;
    private DeleteProductFlow deleteProductFlow;
    private ProductDTO productDTO;
    private ProductController productController;

    @BeforeEach
    public void setup() {
        this.createProductFlow = Mockito.mock(CreateProductFlow.class);
        this.updateProductFlow = Mockito.mock(UpdateProductFlow.class);
        this.getProductFlow = Mockito.mock(GetProductFlow.class);
        this.listAllProductsFlow = Mockito.mock(ListAllProductsFlow.class);
        this.deleteProductFlow = Mockito.mock(DeleteProductFlow.class);
        this.productDTO = MockProduct.buildProductDTO();
        this.productController = new ProductController(createProductFlow, updateProductFlow,
                getProductFlow, listAllProductsFlow, deleteProductFlow);
    }

    @Test
    void createProduct() {
        when(this.createProductFlow.createProduct(any())).thenReturn(MockProduct.buildProductDTO());

        var response = this.productController.createProduct(MockProduct.buildProductDTO());

        assertNotNull(response);
        assertEquals(productDTO.getName(), response.getName());
    }

    @Test
    void updateProduct() {
        when(this.updateProductFlow.updateProduct(any(), any(), any())).thenReturn(MockProduct.buildProductDTO());

        var response = this.productController.updateProduct(MockProduct.buildProductDTO(), CATEGORY_NAME_TEST, PRODUCT_NAME_TEST);

        assertNotNull(response);
        assertEquals(productDTO.getName(), response.getName());
    }

    @Test
    void getProduct() {
        when(this.getProductFlow.getProduct(any(), any())).thenReturn(MockProduct.buildProductDTO());

        var response = this.productController.getProduct(any(), any());

        assertNotNull(response);
        assertEquals(productDTO.getName(), response.getName());
    }

    @Test
    void listAllProducts() {
        when(this.listAllProductsFlow.listAllProducts(any())).thenReturn(MockProduct.buildProductDTOList());

        var response = this.productController.listAllProducts(any());

        assertNotNull(response);
        assertEquals(productDTO.getName(), response.get(0).getName());
    }

    @Test
    void deleteProduct() {
        this.productController.deleteProduct(any(), any());

        Mockito.verify(this.deleteProductFlow, times(1)).deleteProduct(any(), any());
    }
}