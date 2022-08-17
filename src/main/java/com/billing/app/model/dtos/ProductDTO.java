package com.billing.app.model.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {

        private Long id;
        private String name;
        private double price;
        private Date dateCreated;

}
