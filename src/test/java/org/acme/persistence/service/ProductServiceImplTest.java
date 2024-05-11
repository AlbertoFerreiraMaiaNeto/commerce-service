package org.acme.persistence.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import mock.MockCategory;
import mock.MockProduct;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;
import org.acme.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static mock.MockProduct.PRODUCT_NAME_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;
    private Product product;
    private Category category;

    @BeforeEach
    public void setup() {
        this.category = MockCategory.buildCategory();
        this.product = MockProduct.buildProduct();
        this.productRepository = Mockito.mock(ProductRepository.class);
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void save() {
        this.productService.save(product);

        Mockito.verify(productRepository, times(1)).persist(product);
    }

    @Test
    void update() {
        this.productService.update(product);

        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Parameters> parametersCaptor = ArgumentCaptor.forClass(Parameters.class);
        Mockito.verify(productRepository).update(queryCaptor.capture(), parametersCaptor.capture());

        assertEquals("set description = :description, unitPrice = :unitPrice, amount = :amount where name = :name and category = :category",
                queryCaptor.getValue());
    }

    @Test
    void delete() {
        this.productService.delete(product);

        Mockito.verify(productRepository, times(1)).delete(product);
    }

    @Test
    void findProductByNameAndCategory() {
        when(this.productRepository.findProductByNameAndCategory(PRODUCT_NAME_TEST, category)).thenReturn(product);

        var output = this.productService.findProductByNameAndCategory(PRODUCT_NAME_TEST, category);

        assertNotNull(output);
        assertEquals(PRODUCT_NAME_TEST, output.getName());
    }

    @Test
    void findProductsByCategory() {
        when(this.productRepository.findProductsByCategory(category)).thenReturn(MockProduct.buildProductList());

        var output = this.productService.findProductsByCategory(category);

        assertNotNull(output);
        assertEquals(PRODUCT_NAME_TEST, output.get(0).getName());
    }

    @Test
    void findAll() {
        when(this.productRepository.findAll()).thenReturn(Mockito.mock(PanacheQuery.class));

        var output = this.productService.findAll();

        assertNotNull(output);
    }

    @Test
    void updateProductsAmountByNameAndCategory() {
        var productList = MockProduct.buildProductList();
        this.productService.updateProductsAmountByNameAndCategory(productList);

        Mockito.verify(productRepository, times(productList.size())).update(anyString(), Mockito.any(Parameters.class));
    }
}