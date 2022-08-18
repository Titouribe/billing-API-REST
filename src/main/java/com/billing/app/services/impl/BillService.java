package com.billing.app.services.impl;

import com.billing.app.model.entities.Bill;
import com.billing.app.model.entities.Client;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.repositories.IClientRepository;
import com.billing.app.services.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BillService implements IBillService {

    @Autowired
    private IBillRepository billRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Override
    @Transactional
    public Bill saveBill(Bill bill) {
        Optional<Client> clientOptional = clientRepository.findById(bill.getClient().getId());
        if (clientOptional.isPresent()) {
            bill.setClient(clientOptional.get());
            return billRepository.save(bill);
        } else {
            return null;
        }
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    @Transactional
    public String deleteBill(Long id) {
        Optional<Bill> billOptional = billRepository.findById(id);
        if (billOptional.isPresent()) {
            billRepository.delete(billOptional.get());
            return "Bill " + billOptional.get().getDescription() + " deleted";
        } else {
            return "Bill not found";
        }
    }
}
