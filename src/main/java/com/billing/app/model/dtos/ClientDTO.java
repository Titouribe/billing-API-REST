package com.billing.app.model.dtos;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateCreated;
    private Set<BillDTO> billsDTO;

}
