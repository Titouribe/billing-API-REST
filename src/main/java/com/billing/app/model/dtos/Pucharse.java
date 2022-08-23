package com.billing.app.model.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
public class Pucharse {

    @Min(value = 1, message = "Client id should not be less than 1")
    private Long clientId;

    @Min(value = 1, message = "Product id should not be less than 1")
    private Long productId;


    @Min(value = 1, message = "Quantity should not be less than 1")
    @Max(value = 10, message = "Quantity should not be greater than 10")
    private int quantity;

    private String description;
    private String observation;
}
