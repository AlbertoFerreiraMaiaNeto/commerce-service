package org.acme.cache;

import io.quarkus.cache.CompositeCacheKey;
import mock.MockCategory;
import mock.MockProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static mock.MockProduct.PRODUCT_NAME_TEST;
import static org.acme.constants.CacheKeyConstants.PRODUCT_CACHE_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCacheKeyGeneratorTest {

    private ProductCacheKeyGenerator keyGenerator;

    @BeforeEach
    void setUp() {
        keyGenerator = new ProductCacheKeyGenerator();
    }

    @Test
    void testGenerateWithProduct() throws NoSuchMethodException {
        var product = MockProduct.buildProduct();
        var category = MockCategory.buildCategory();

        Method method = CategoryCacheKeyGenerator.class.getDeclaredMethod("generate", Method.class, Object[].class);

        Object result = keyGenerator.generate(method, product, category);

        assertEquals(new CompositeCacheKey(String.format(PRODUCT_CACHE_KEY, product.getName().toUpperCase(), product.getCategory().getName().toUpperCase())).toString(), result.toString());
    }

    @Test
    void testGenerateWithStringParam() throws NoSuchMethodException {
        var category = MockCategory.buildCategory();

        Method method = CategoryCacheKeyGenerator.class.getDeclaredMethod("generate", Method.class, Object[].class);

        Object result = keyGenerator.generate(method, PRODUCT_NAME_TEST, category);

        assertEquals(new CompositeCacheKey(String.format(PRODUCT_CACHE_KEY, PRODUCT_NAME_TEST.toUpperCase(), category.getName().toUpperCase())).toString(), result.toString());
    }
}