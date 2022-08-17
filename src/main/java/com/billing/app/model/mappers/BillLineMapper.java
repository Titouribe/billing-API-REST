package com.billing.app.model.mappers;

import com.billing.app.model.dtos.BillLineDTO;
import com.billing.app.model.entities.BillLine;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillLineMapper {
    @Mapping(target = "productDTO", source = "product")
    BillLineDTO toDto(BillLine billLine);
    @InheritInverseConfiguration
    BillLine toEntity(BillLineDTO billLineDto);
}
