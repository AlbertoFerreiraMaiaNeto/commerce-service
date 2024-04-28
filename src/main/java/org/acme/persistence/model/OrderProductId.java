package org.acme.persistence.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
public class OrderProductId implements Serializable {

    private Long order;
    private ProductCategoryId product;

}
