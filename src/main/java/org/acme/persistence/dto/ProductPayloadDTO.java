package org.acme.persistence.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPayloadDTO {

    private String name;

    private String category;

    private int amount;

    private BigDecimal unitPrice;

    private double totalPricePerProduct;
}