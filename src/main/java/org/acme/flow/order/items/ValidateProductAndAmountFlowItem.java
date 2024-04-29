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

@RequiredArgsConstructor
@Singleton
public class ValidateProductAndAmountFlowItem {

    public KafkaOrderDTO validate (OrderDTO orderDTO, List<Product> productList) {

        var kafkaPayload = getConfirmedAndInsufficientAmountProducts(orderDTO, productList);

        getNonExistsProducts(kafkaPayload);

        return kafkaPayload;
    }

    private static void getNonExistsProducts(KafkaOrderDTO kafkaPayload) {
        List<ProductPayloadDTO> auxNonExistsProducts = new ArrayList<>(kafkaPayload.getNonExistsProducts());

        auxNonExistsProducts.forEach(nonExistsProduct -> {
            kafkaPayload.getConfirmedProducts().forEach(confirmedProduct -> {
                if(nonExistsProduct.getName().equalsIgnoreCase(confirmedProduct.getName()) &&
                        nonExistsProduct.getCategory().equalsIgnoreCase(confirmedProduct.getCategory())) {
                    kafkaPayload.getNonExistsProducts().remove(confirmedProduct);
                }
            });
        });

        auxNonExistsProducts.forEach(nonExistsProduct -> {
            kafkaPayload.getInsufficientAmountProducts().forEach(insufficientAmountProduct -> {
                if(nonExistsProduct.getName().equalsIgnoreCase(insufficientAmountProduct.getName()) &&
                        nonExistsProduct.getCategory().equalsIgnoreCase(insufficientAmountProduct.getCategory())) {
                    kafkaPayload.getNonExistsProducts().remove(insufficientAmountProduct);
                }
            });
        });
    }

    private static KafkaOrderDTO getConfirmedAndInsufficientAmountProducts(OrderDTO orderDTO, List<Product> productList) {
        List<ProductPayloadDTO> confirmedProducts = new ArrayList<>();
        List<ProductPayloadDTO> insufficientAmountProducts = new ArrayList<>();
        List<ProductPayloadDTO> nonExistsProducts = new ArrayList<>();

        orderDTO.getProductList().forEach(requestProduct -> {
            productList.forEach(product -> {
                if (product.getName().equalsIgnoreCase(requestProduct.getName()) &&
                        product.getCategory().getName().equalsIgnoreCase(requestProduct.getCategory())) {
                    if(product.getAmount() >= requestProduct.getAmount()) {
                        confirmedProducts.add(getBuildProductPayload(requestProduct, product));
                    } else {
                        insufficientAmountProducts.add(getBuildProductPayload(requestProduct, product));
                    }
                } else {
                    nonExistsProducts.add(getBuildProductPayload(requestProduct, product));
                }
            });
        });

        return KafkaOrderDTO.builder()
                .confirmedProducts(confirmedProducts)
                .insufficientAmountProducts(insufficientAmountProducts)
                .nonExistsProducts(nonExistsProducts)
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
