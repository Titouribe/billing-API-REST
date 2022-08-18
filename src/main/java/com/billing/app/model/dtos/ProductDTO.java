package com.billing.app.model.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDTO {

        private Long id;
        private String name;
        private BigDecimal price;
        private Date dateCreated;

}
