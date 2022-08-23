package com.billing.app.services.impl;

import com.billing.app.model.dtos.ClientDTO;
import com.billing.app.model.dtos.ProductDTO;
import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.model.entities.Bill;
import com.billing.app.model.entities.BillLine;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.model.mappers.ProductMapper;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.services.IPucharseService;
import com.billing.app.util.GenerateOrderTrackingNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PucharseServiceImplTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private IBillRepository billRepository;

    @Mock
    PucharseResponse pucharseResponse;

    @Mock
    private GenerateOrderTrackingNumber generateOrderTrackingNumber;

    @InjectMocks
    private IPucharseService pucharseService;

    private Pucharse pucharse;

    @BeforeEach
    void setUp() {
        pucharse = new Pucharse();

        ClientDTO clientDTO = new ClientDTO();
        ProductDTO productDTO = new ProductDTO();

        clientDTO.setId(1L);
        clientDTO.setFirstName("test");
        clientDTO.setEmail("user");

        productDTO.setName("product test");
        productDTO.setPrice(BigDecimal.valueOf(10));
        productDTO.setId(1L);

        pucharse.setClientDTO(clientDTO);
        pucharse.setProductDTO(productDTO);
        pucharse.setDescription("test description");
        pucharse.setObservation("test observation");
        pucharse.setQuantity(2);

    }

    @Test
    void placeOrderCallCorrectly(){

        BillLine billLine = new BillLine();
        Bill bill = new Bill();

        billLine.setQuantity(pucharse.getQuantity());
        billLine.setProduct(productMapper.toEntity(pucharse.getProductDTO()));

        bill.setClient(clientMapper.toEntity(pucharse.getClientDTO()));
        bill.setObservation(pucharse.getObservation());
        bill.setDescription(pucharse.getDescription());
        bill.addBillLine(billLine);

        when(billRepository.save(bill)).thenReturn(bill);

        assertNotNull(pucharseService.placeOrder(pucharse));
    }

}
