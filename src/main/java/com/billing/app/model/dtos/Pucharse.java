package com.billing.app.model.dtos;

import lombok.Data;


@Data
public class Pucharse {
    private ClientDTO clientDTO;
    private ProductDTO productDTO;
    private int quantity;
    private String description;
    private String observation;
}
