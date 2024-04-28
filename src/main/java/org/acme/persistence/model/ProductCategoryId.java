package org.acme.persistence.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
public class ProductCategoryId implements Serializable {

    private String name;
    private String category;

}
