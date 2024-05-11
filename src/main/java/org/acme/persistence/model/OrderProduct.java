package org.acme.persistence.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "order_product")
@IdClass(OrderProductId.class)
public class OrderProduct {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "product_name", referencedColumnName = "name"),
            @JoinColumn(name = "product_category", referencedColumnName = "category")
    })
    private Product product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "total_price_per_product")
    private double totalPricePerProduct;
}
