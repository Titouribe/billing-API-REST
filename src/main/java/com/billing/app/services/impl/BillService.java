package com.billing.app.services.impl;

import com.billing.app.constants.Constants;
import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.exceptions.RequestException;
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
    private ErrorsConstants errorsConstants;
    @Autowired
    private ValidConstants validConstants;
    @Autowired
    private IClientRepository clientRepository;

    @Override
    @Transactional
    public Bill saveBill(Bill bill) {
        Client client = clientRepository.findById(bill.getClient().getId())
                .orElseThrow(() -> new RequestException("400", errorsConstants
                        .notFound(Constants.CLIENT, String.valueOf(bill.getClient().getId()))));
        bill.setClient(client);
        return billRepository.save(bill);
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RequestException("400", errorsConstants
                        .notFound(Constants.CLIENT, String.valueOf(id))));
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    @Transactional
    public String deleteBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RequestException("400", errorsConstants
                        .notFound(Constants.BILL, String.valueOf(id))));
        billRepository.delete(bill);
        return "Bill " + bill.getId() + " deleted";
    }
}
