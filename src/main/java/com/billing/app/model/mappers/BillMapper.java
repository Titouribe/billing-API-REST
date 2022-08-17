package com.billing.app.model.mappers;

import com.billing.app.model.dtos.BillDTO;
import com.billing.app.model.entities.Bill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mappings({@Mapping(target = "clientDTO", source = "client"),
            @Mapping(target = "billLineDTOS", source = "billLines")})
    BillDTO toDto(Bill bill);
    @InheritInverseConfiguration
    Bill toEntity(BillDTO billDTO);
}
