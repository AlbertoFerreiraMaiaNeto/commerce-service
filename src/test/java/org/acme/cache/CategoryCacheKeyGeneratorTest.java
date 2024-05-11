package org.acme.cache;

import io.quarkus.cache.CompositeCacheKey;
import mock.MockCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static mock.MockCategory.CATEGORY_NAME_TEST;
import static org.acme.constants.CacheKeyConstants.CATEGORY_CACHE_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryCacheKeyGeneratorTest {

    private CategoryCacheKeyGenerator keyGenerator;

    @BeforeEach
    void setUp() {
        keyGenerator = new CategoryCacheKeyGenerator();
    }

    @Test
    void testGenerateWithCategory() throws NoSuchMethodException {
        var category = MockCategory.buildCategory();
        Method method = CategoryCacheKeyGenerator.class.getDeclaredMethod("generate", Method.class, Object[].class);

        Object result = keyGenerator.generate(method, category);

        assertEquals(new CompositeCacheKey(String.format(CATEGORY_CACHE_KEY, category.getName().toUpperCase())).toString(), result.toString());
    }

    @Test
    void testGenerateWithStringParam() throws NoSuchMethodException {
        Method method = CategoryCacheKeyGenerator.class.getDeclaredMethod("generate", Method.class, Object[].class);

        Object result = keyGenerator.generate(method, CATEGORY_NAME_TEST);

        assertEquals(new CompositeCacheKey(String.format(CATEGORY_CACHE_KEY, CATEGORY_NAME_TEST.toUpperCase())).toString(), result.toString());
    }

}