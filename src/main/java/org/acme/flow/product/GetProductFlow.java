package org.acme.flow.product;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.exception.CategoryNotFoundException;
import org.acme.exception.ProductNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.mapper.ProductMapper;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.service.ProductService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class GetProductFlow {

    private final ProductService productService;
    private final FindCategoryFlowItem findCategoryFlowItem;

    public ProductDTO getProduct(String categoryName, String productName) {
        var category = findCategoryFlowItem.findCategory(categoryName);

        var currentProduct = productService.findProductByNameAndCategory(productName, category);

        if(Objects.isNull(currentProduct)) {
            throw new ProductNotFoundException();
        }

        if(currentProduct.getCategory().isActive()) {
           return ProductMapper.INSTANCE.productToProductDTO(currentProduct);
        }

        throw new CategoryNotFoundException();
    }
}
