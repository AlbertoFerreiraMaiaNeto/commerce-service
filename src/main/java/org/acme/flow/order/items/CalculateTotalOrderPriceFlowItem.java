package org.acme.flow.order.items;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.acme.persistence.dto.ProductPayloadDTO;

@RequiredArgsConstructor
@Singleton
public class CalculateTotalOrderPriceFlowItem extends AbstractCalculate {

    public KafkaOrderDTO calculateTotalOrderPrice(KafkaOrderDTO kafkaOrderDTO) {
        return calculate(kafkaOrderDTO);
    }


    @Override
    KafkaOrderDTO calculate(KafkaOrderDTO kafkaOrderDTO) {
        double total = 0;

        for (ProductPayloadDTO product : kafkaOrderDTO.getConfirmedProducts()) {
            total = total + calculateTotal(product.getUnitPrice(), product.getAmount());
        }

        kafkaOrderDTO.setTotalOrderPrice(total);

        return kafkaOrderDTO;
    }
}