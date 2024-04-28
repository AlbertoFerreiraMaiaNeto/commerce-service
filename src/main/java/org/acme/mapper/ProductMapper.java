package org.acme.mapper;


import org.acme.persistence.dto.ProductDTO;
import org.acme.persistence.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "category", source = "category.name")
    ProductDTO productToProductDTO(Product product);

    @Mapping(target = "category", source = "category.name")
    List<ProductDTO> productsToProductDTOs(List<Product> products);
}