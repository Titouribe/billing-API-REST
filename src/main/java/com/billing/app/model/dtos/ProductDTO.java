package com.billing.app.model.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDTO {

        private Long id;

        @Size(min = 1, max = 20, message = "Product name must contain a maximum of 30 characters and not be empty")
        private String name;

        @Min(value = 0, message = "Product price should be greater than 0")
        private BigDecimal price;

        private Date dateCreated;

}
