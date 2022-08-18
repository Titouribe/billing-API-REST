package com.billing.app.model.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PucharseResponse {
    private String orderTrackingNumber;
    private BigDecimal total;
}
