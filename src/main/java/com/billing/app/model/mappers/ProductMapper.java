package com.billing.app.model.mappers;

import com.billing.app.model.dtos.ProductDTO;
import com.billing.app.model.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);

}
