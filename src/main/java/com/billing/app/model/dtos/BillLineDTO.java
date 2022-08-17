package com.billing.app.model.dtos;

import lombok.Data;

@Data
public class BillLineDTO {

    private Long id;
    private int quantity;
    private ProductDTO productDTO;

}
