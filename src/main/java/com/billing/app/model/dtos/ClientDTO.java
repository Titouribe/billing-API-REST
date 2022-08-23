package com.billing.app.model.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
public class ClientDTO {

    private Long id;

    @Size(min = 1, max = 20, message = "Name must contain a maximum of 20 characters and not be empty")
    private String firstName;

    @Size(min = 1, max = 20, message = "last Name must contain a maximum of 20 characters and not be empty")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid Email, must contain an @")
    private String email;

    private Date dateCreated;
    private Set<BillDTO> billsDTO;

}
