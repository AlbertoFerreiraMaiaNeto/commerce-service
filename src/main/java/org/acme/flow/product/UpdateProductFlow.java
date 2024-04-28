package org.acme.flow.product;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.exception.ProductNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.mapper.ProductMapper;
import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Product;
import org.acme.persistence.service.ProductService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class UpdateProductFlow {

    private final ProductService productService;
    private final FindCategoryFlowItem findCategoryFlowItem;

    @Transactional
    public ProductDTO updateProduct(ProductDTO product, String categoryName, String productName) {
        var category = this.findCategoryFlowItem.findCategory(categoryName);

        var currentProduct = productService.findProductByNameAndCategory(productName, category);

        if(Objects.isNull(currentProduct)) {
            throw new ProductNotFoundException();
        }

       var updatedProductEntity = buildUpdatedProductEntity(currentProduct, product);

        this.productService.update(updatedProductEntity);

        return ProductMapper.INSTANCE.productToProductDTO(updatedProductEntity);
    }

    private Product buildUpdatedProductEntity(Product currentProduct, ProductDTO productDTO) {
        currentProduct.setAmount(productDTO.getAmount());
        currentProduct.setDescription(productDTO.getDescription());
        currentProduct.setUnitPrice(productDTO.getUnitPrice());
        return currentProduct;
    }
}
