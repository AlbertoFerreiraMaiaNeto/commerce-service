package org.acme.flow.product;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.exception.CategoryNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.mapper.ProductMapper;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.service.ProductService;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class ListAllProductsFlow {

    private final ProductService productService;
    private final FindCategoryFlowItem findCategoryFlowItem;

    public List<ProductDTO> listAllProducts(String xCategory) {

        if(Objects.nonNull(xCategory)) {
            var category = this.findCategoryFlowItem.findCategory(xCategory);

            if(category.isActive()) {
                return ProductMapper.INSTANCE.productsToProductDTOs(productService.findProductsByCategory(category));
            }

            throw new CategoryNotFoundException();
        }

        return ProductMapper.INSTANCE.productsToProductDTOs(productService.findAll().stream().filter(product -> product.getCategory().isActive()).toList());
    }
}
