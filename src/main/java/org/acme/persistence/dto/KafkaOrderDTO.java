package org.acme.persistence.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.acme.enums.OrderStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaOrderDTO {

    private Long orderId;

    private OrderDTO orderDTO;

    private List<ProductPayloadDTO> confirmedProducts;

    private List<ProductPayloadDTO> insufficientAmountProducts;

    private OrderStatus statusOrder;

    private double totalOrderPrice;
}

