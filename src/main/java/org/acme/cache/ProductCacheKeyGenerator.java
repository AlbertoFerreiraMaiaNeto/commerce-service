package org.acme.cache;

import io.quarkus.cache.CacheKeyGenerator;
import io.quarkus.cache.CompositeCacheKey;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;

import java.lang.reflect.Method;

import static org.acme.constants.CacheKeyConstants.PRODUCT_CACHE_KEY;

@ApplicationScoped
public class ProductCacheKeyGenerator implements CacheKeyGenerator {

    @Override
    public Object generate(Method method, Object... methodParams) {

        if (methodParams[0] instanceof Product product) {
            return new CompositeCacheKey(String.format(PRODUCT_CACHE_KEY, product.getName().toUpperCase(), product.getCategory().getName().toUpperCase()));
        }

        var categoryEntity = (Category) methodParams[1];

        return new CompositeCacheKey(String.format(PRODUCT_CACHE_KEY, methodParams[0].toString().toUpperCase(), categoryEntity.getName().toUpperCase()));
    }
}
