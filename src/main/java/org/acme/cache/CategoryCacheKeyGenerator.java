package org.acme.cache;

import io.quarkus.cache.CacheKeyGenerator;
import io.quarkus.cache.CompositeCacheKey;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.persistence.model.Category;

import java.lang.reflect.Method;

import static org.acme.constants.CacheKeyConstants.CATEGORY_CACHE_KEY;

@ApplicationScoped
public class CategoryCacheKeyGenerator implements CacheKeyGenerator {

    @Override
    public Object generate(Method method, Object... methodParams) {

        if (methodParams[0] instanceof Category category) {
            return new CompositeCacheKey(String.format(CATEGORY_CACHE_KEY, category.getName().toUpperCase()));
        }

        return new CompositeCacheKey(String.format(CATEGORY_CACHE_KEY, methodParams[0].toString().toUpperCase()));
    }
}
