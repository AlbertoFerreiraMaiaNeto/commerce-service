package org.acme.flow.order.items;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.acme.persistence.dto.OrderDTO;
import org.acme.persistence.dto.ProductPayloadDTO;
import org.acme.persistence.dto.RequestProductDTO;
import org.acme.persistence.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Singleton
public class ValidateProductAndAmountFlowItem {

    public KafkaOrderDTO validate (OrderDTO orderDTO, List<Product> productList) {

        return getConfirmedAndInsufficientAmountProducts(orderDTO, productList);
    }

    private static KafkaOrderDTO getConfirmedAndInsufficientAmountProducts(OrderDTO orderDTO, List<Product> productList) {
        List<ProductPayloadDTO> insufficientAmountProducts = new ArrayList<>();
        List<ProductPayloadDTO> confirmedProducts = new ArrayList<>();

        orderDTO.getProductList().forEach(requestProduct -> {
            productList.forEach(product -> {
                if(Objects.nonNull(requestProduct.getName()) && Objects.nonNull(requestProduct.getCategory())) {
                    if (product.getName().equalsIgnoreCase(requestProduct.getName()) &&
                            product.getCategory().getName().equalsIgnoreCase(requestProduct.getCategory())) {
                        if(product.getAmount() >= requestProduct.getAmount()) {
                            confirmedProducts.add(getBuildProductPayload(requestProduct, product));
                        } else {
                            insufficientAmountProducts.add(getBuildProductPayload(requestProduct, product));
                        }
                    }
                }
            });
        });

        return KafkaOrderDTO.builder()
                .orderDTO(orderDTO)
                .confirmedProducts(confirmedProducts)
                .insufficientAmountProducts(insufficientAmountProducts)
                .build();
    }

    private static ProductPayloadDTO getBuildProductPayload(RequestProductDTO requestProduct, Product product) {
        return ProductPayloadDTO.builder()
                .name(product.getName())
                .category(product.getCategory().getName())
                .amount(requestProduct.getAmount())
                .build();
    }
}
