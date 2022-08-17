package com.billing.app.model.dtos;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class BillDTO {

        private Long id;
        private String description;
        private String observation;
        private Date dateCreated;
        private ClientDTO clientDTO;
        private Set<BillLineDTO> billLineDTOS;

}
