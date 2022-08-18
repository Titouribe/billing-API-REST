package com.billing.app.services;

import com.billing.app.model.entities.Bill;

import java.util.List;

public interface IBillService {
    public Bill saveBill(Bill bill);
    public Bill findById(Long id);
    public List<Bill> findAll();
    public String deleteBill(Long id);
}
