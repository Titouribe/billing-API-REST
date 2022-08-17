package com.billing.app.model.mappers;

import com.billing.app.model.dtos.ClientDTO;
import com.billing.app.model.entities.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "billsDTO", source = "bills")
    ClientDTO toDTO(Client client);
    @InheritInverseConfiguration
    Client toEntity(ClientDTO clientDTO);
}
