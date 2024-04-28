package org.acme.flow.product;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.acme.exception.ProductNotFoundException;
import org.acme.flow.product.items.FindCategoryFlowItem;
import org.acme.persistence.service.ProductService;

import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class DeleteProductFlow {

    private final ProductService productService;
    private final FindCategoryFlowItem findCategoryFlowItem;

    @Transactional
    public void deleteProduct(String categoryName, String productName) {
        var category = this.findCategoryFlowItem.findCategory(categoryName);

        var currentProduct = productService.findProductByNameAndCategory(productName, category);

        if(Objects.isNull(currentProduct)) {
            throw new ProductNotFoundException();
        }

        this.productService.delete(currentProduct);
    }
}
