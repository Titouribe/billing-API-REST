package com.billing.app.services.impl;

import com.billing.app.exceptions.RequestException;
import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.model.entities.Bill;
import com.billing.app.model.entities.BillLine;
import com.billing.app.model.entities.Client;
import com.billing.app.model.entities.Product;
import com.billing.app.repositories.IBillLineRepository;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.repositories.IClientRepository;
import com.billing.app.repositories.IProductRepository;
import com.billing.app.services.IPucharseService;
import com.billing.app.util.GenerateOrderTrackingNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PucharseServiceImpl implements IPucharseService {

    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IBillRepository billRepository;
    @Autowired
    private IBillLineRepository billLineRepository;
    @Autowired
    private GenerateOrderTrackingNumber generateOrderTrackingNumber;

    @Override
    public PucharseResponse placeOrder(Pucharse pucharse) {

        Client client = clientRepository.findById(pucharse.getClientId())
                .orElseThrow(() -> new RequestException("401", "Client with id: " + pucharse.getClientId() + " not found."));


        Product product = productRepository.findById(pucharse.getProductId())
                .orElseThrow(() -> new RequestException("401", "Product with id: " + pucharse.getProductId() + " not found."));

        BillLine billLine = new BillLine();
        Bill bill = new Bill();

        billLine.setProduct(product);
        billLine.setQuantity(pucharse.getQuantity());

        bill.setClient(client);
        bill.addBillLine(billLine);
        bill.setDescription(pucharse.getDescription());
        bill.setObservation(pucharse.getObservation());

        billRepository.save(bill);

        PucharseResponse pucharseResponse = new PucharseResponse();
        pucharseResponse.setOrderTrackingNumber(GenerateOrderTrackingNumber.generateRandomUUID());
        pucharseResponse.setTotal(bill.calculateTotal());

        return pucharseResponse;
    }
}
