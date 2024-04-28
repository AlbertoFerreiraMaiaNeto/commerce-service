package org.acme.persistence.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String name;

    private String category;

    private String description;

    private BigDecimal unitPrice;

    private int amount;
}