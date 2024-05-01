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
public class OrderDTO {

    private String orderOwner;

    private String orderOwnerEmail;

    private List<RequestProductDTO> productList;
}

