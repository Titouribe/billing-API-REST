package com.billing.app.services.impl;

import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.model.entities.Bill;
import com.billing.app.model.entities.BillLine;
import com.billing.app.model.entities.Client;
import com.billing.app.model.entities.Product;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.model.mappers.ProductMapper;
import com.billing.app.repositories.IBillLineRepository;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.repositories.IProductRepository;
import com.billing.app.services.IPucharseService;
import com.billing.app.util.GenerateOrderTrackingNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PucharseServiceImpl implements IPucharseService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ProductMapper productMapper;

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

        Client client = clientMapper.toEntity(pucharse.getClientDTO());
        Product product = productMapper.toEntity(pucharse.getProductDTO());
        BillLine billLine = new BillLine();
        Bill bill = new Bill();

        billLine.setProduct(product);
        billLine.setQuantity(pucharse.getQuantity());

        bill.setClient(client);
        bill.addBillLine(billLine);

        billRepository.save(bill);

        PucharseResponse pucharseResponse = new PucharseResponse();
        pucharseResponse.setOrderTrackingNumber(GenerateOrderTrackingNumber.generateRandomUUID());

        return pucharseResponse;
    }
}
