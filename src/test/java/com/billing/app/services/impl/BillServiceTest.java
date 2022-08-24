package com.billing.app.services.impl;

import com.billing.app.constants.ErrorsConstants;
import com.billing.app.exceptions.RequestException;
import com.billing.app.model.entities.Bill;
import com.billing.app.model.entities.Client;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.repositories.IClientRepository;
import com.billing.app.services.IBillService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class BillServiceTest {

    @MockBean
    private IClientRepository clientRepository;

    @MockBean
    private IBillRepository billRepository;

    @MockBean
    private ErrorsConstants errorsConstants;

    @Autowired
    private IBillService billService;

    private Bill bill = new Bill();

    @BeforeEach
    void setUp() {
        bill.setObservation("test observation");
        bill.setDescription("test description");
        bill.setDateCreated(new Date());
        bill.setId(1L);
    }

    @Test
    void testSaveBillWhenClientExist() {
        Client client = new Client();

        client.setFirstName("user");
        client.setLastName("test");
        client.setEmail("test@test");
        client.setId(1L);

        bill.setClient(client);

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(billRepository.save(any(Bill.class))).thenReturn(bill);


        Bill billResponse = billService.saveBill(bill);
        assertEquals("user", billResponse.getClient().getFirstName());
        assertTrue(billResponse.getBillLines().isEmpty());

        verify(clientRepository).findById(anyLong());
        verify(billRepository).save(any());
    }

    @Test
    void testSaveBillWhenClientDontExistThrowRequestException() {
        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        bill.setClient(client);

        assertThrows(RequestException.class, () -> {
            billService.saveBill(bill);
        });

    }

    @Test
    void testDeleteBillCorrectly() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));
        String result = "Bill " + "1" + " deleted";
        assertEquals(result, billService.deleteBill(anyLong()));
        assertFalse(billService.deleteBill(anyLong()).isBlank());
    }

    @Test
    void testDeleteBillWhenBillDontExistThrowRequestException() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            billService.deleteBill(anyLong());
        });
        verify(billRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testFindByIdCallCorrectly() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));
        assertEquals("test observation", billService.findById(anyLong()).getObservation());
        verify(billRepository).findById(anyLong());
    }

    @Test
    void testFindByIdThrowRequestException() {
        when(billRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            billService.findById(anyLong());
        });
    }

}
