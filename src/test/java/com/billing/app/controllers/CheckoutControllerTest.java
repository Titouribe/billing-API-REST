package com.billing.app.controllers;

import com.billing.app.model.dtos.ClientDTO;
import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.model.entities.Client;
import com.billing.app.services.IPucharseService;
import com.billing.app.util.GenerateOrderTrackingNumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IPucharseService pucharseService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ;
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDoOrderCorrectly() throws Exception {

        Pucharse pucharse = new Pucharse();
        pucharse.setClientId(1L);
        pucharse.setProductId(1L);
        pucharse.setQuantity(2);
        pucharse.setObservation("test observation");
        pucharse.setDescription("test description");

        PucharseResponse pucharseResponse = new PucharseResponse();

        String randomUUID = GenerateOrderTrackingNumber.generateRandomUUID();

        pucharseResponse.setOrderTrackingNumber(randomUUID);
        pucharseResponse.setTotal(BigDecimal.valueOf(999));

        when(pucharseService.placeOrder(any())).thenReturn(pucharseResponse);
        mvc.perform(post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pucharse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.total").value("999"))
                .andExpect(jsonPath("$.orderTrackingNumber").value(randomUUID));
        verify(pucharseService).placeOrder(any());
    }

}
