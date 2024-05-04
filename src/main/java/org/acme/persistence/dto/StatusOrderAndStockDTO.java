package org.acme.persistence.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusOrderAndStockDTO {

    private String orderStatus;

    private List<RequestProductDTO> productList;
}

