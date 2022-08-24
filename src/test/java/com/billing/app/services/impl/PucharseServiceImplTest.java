package com.billing.app.services.impl;

import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.exceptions.RequestException;
import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.model.entities.Bill;
import com.billing.app.model.entities.BillLine;
import com.billing.app.model.entities.Client;
import com.billing.app.model.entities.Product;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.repositories.IClientRepository;
import com.billing.app.repositories.IProductRepository;
import com.billing.app.services.IPucharseService;
import com.billing.app.util.GenerateOrderTrackingNumber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PucharseServiceImplTest {

    @MockBean
    private IProductRepository productRepository;

    @MockBean
    IClientRepository clientRepository;

    @MockBean
    private ValidConstants validConstants;

    @MockBean
    private ErrorsConstants errorsConstants;
    @MockBean
    private IBillRepository billRepository;

    @MockBean
    PucharseResponse pucharseResponse;

    @MockBean
    private GenerateOrderTrackingNumber generateOrderTrackingNumber;

    @Autowired
    private IPucharseService pucharseService;

    private Pucharse pucharse;

    @BeforeEach
    void setUp() {
        pucharse = new Pucharse();
        pucharse.setClientId(1L);
        pucharse.setProductId(1L);
        pucharse.setQuantity(2);
        pucharse.setObservation("test observation");
        pucharse.setDescription("test description");

        PucharseResponse pucharseResponse = new PucharseResponse();
    }

    @Test
    void testPlaceOrderCorrectly() {

        PucharseResponse pucharseResponse = new PucharseResponse();

        Client client = new Client();
        Product product = new Product();

        client.setId(1L);
        client.setEmail("test@email");
        client.setFirstName("user");
        client.setLastName("test");
        client.setDateCreated(new Date());

        product.setId(1L);
        product.setDateCreated(new Date());
        product.setName("product test");
        product.setPrice(BigDecimal.valueOf(999));

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        BillLine billLine = new BillLine();
        Bill bill = new Bill();

        billLine.setProduct(product);
        billLine.setQuantity(pucharse.getQuantity());

        bill.setClient(client);
        bill.addBillLine(billLine);
        bill.setDescription(pucharse.getDescription());
        bill.setObservation(pucharse.getObservation());

        when(billRepository.save(any())).thenReturn(bill);

        assertFalse(pucharseService.placeOrder(pucharse).getOrderTrackingNumber().isEmpty());
        assertEquals(BigDecimal.valueOf(1998), pucharseService.placeOrder(pucharse).getTotal());

        verify(clientRepository, times(2)).findById(anyLong());
        verify(productRepository, times(2)).findById(anyLong());
    }

    @Test
    void testPlaceOrderWhenClientDontExist() {

        Product product = new Product();

        product.setId(1L);
        product.setDateCreated(new Date());
        product.setName("product test");
        product.setPrice(BigDecimal.valueOf(999));

        when(clientRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            pucharseService.placeOrder(pucharse);
        });

        verify(clientRepository, times(1)).findById(anyLong());
        verify(productRepository, times(0)).findById(anyLong());
    }

    @Test
    void testPlaceOrderWhenProductDontExist() {

        Client client = new Client();

        client.setId(1L);
        client.setEmail("test@email");
        client.setFirstName("user");
        client.setLastName("test");
        client.setDateCreated(new Date());

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            pucharseService.placeOrder(pucharse);
        });

        verify(clientRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(billRepository, times(0)).save(any());
    }

}
