package org.acme.flow.order.items;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.acme.persistence.dto.ProductPayloadDTO;

@RequiredArgsConstructor
@Singleton
public class CalculatePricePerOrderProductFlowItem extends AbstractCalculate {

    public KafkaOrderDTO calculatePricePerProduct(KafkaOrderDTO kafkaOrderDTO) {
        return calculate(kafkaOrderDTO);
    }

    @Override
    KafkaOrderDTO calculate(KafkaOrderDTO kafkaOrderDTO) {
        for (ProductPayloadDTO product : kafkaOrderDTO.getConfirmedProducts()) {
            product.setTotalPricePerProduct(calculateTotal(product.getUnitPrice(), product.getAmount()));
        }

        return kafkaOrderDTO;
    }
}
