package org.acme.persistence.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.acme.enums.StatusOrder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {

    private String orderOwner;

    private String orderOwnerEmail;

    private List<ProductPayloadDTO> productList;

    private StatusOrder status;
}

