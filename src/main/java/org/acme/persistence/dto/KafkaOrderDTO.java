package org.acme.persistence.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.acme.enums.StatusOrder;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaOrderDTO {

    private OrderDTO orderDTO;

    private List<ProductPayloadDTO> confirmedProducts;

    private List<ProductPayloadDTO> insufficientAmountProducts;

    private List<ProductPayloadDTO> nonExistsProducts;

    private StatusOrder statusOrder;
}

