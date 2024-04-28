package org.acme.flow.product;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.exception.ProductAlreadyExistsException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.mapper.ProductMapper;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Category;
import org.acme.persistence.model.Product;
import org.acme.persistence.service.ProductService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class CreateProductFlow {

    private final ProductService productService;
    private final FindCategoryFlowItem findCategoryFlowItem;

    @Transactional
    public ProductDTO createProduct (ProductDTO product) {
        var category = this.findCategoryFlowItem.findCategory(product.getCategory());

        var currentProduct = productService.findProductByNameAndCategory(product.getName(), category);

        if(Objects.nonNull(currentProduct)) {
            throw new ProductAlreadyExistsException();
        }

       var productEntity = buildProductEntity(product, category);

        this.productService.save(productEntity);

        return ProductMapper.INSTANCE.productToProductDTO(productEntity);
    }

    private Product buildProductEntity(ProductDTO product, Category category) {
        return Product.builder()
                .name(product.getName().toUpperCase())
                        .unitPrice(product.getUnitPrice())
                            .amount(product.getAmount())
                                .description(product.getDescription())
                                        .category(category).build();
    }
}
