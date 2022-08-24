package com.billing.app.controllers;

import com.billing.app.model.dtos.ClientDTO;
import com.billing.app.model.entities.Client;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.services.impl.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    private Client client;

    private ClientDTO clientDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setEmail("test@email.com");
        client.setFirstName("user");
        client.setLastName("test");
        client.setDateCreated(new Date());

        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveClientCorrectly() throws Exception {

        clientDTO = new ClientDTO();
        clientDTO.setEmail("test@email.com");
        clientDTO.setFirstName("user");
        clientDTO.setLastName("test");

        when(clientService.saveClient(any())).thenReturn(client);
        mvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated());
        verify(clientService).saveClient(any());
    }

    @Test
    void testSaveClientThrowBadRequest() throws Exception {

        clientDTO = new ClientDTO();
        clientDTO.setEmail("test@email.com");
        clientDTO.setFirstName("");
        clientDTO.setLastName("test");

        when(clientService.saveClient(any())).thenReturn(client);
        mvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("Size"));
    }

    @Test
    void testFindAllWhitoutParam() throws Exception {
        when(clientService.findAll()).thenReturn(List.of(client));
        mvc.perform(get("/client").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindAllByName() throws Exception {
        when(clientService.findAllByName(any())).thenReturn(List.of(client));
        mvc.perform(get("/client").param("name", "user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(clientService).findAllByName(any());
    }

    @Test
    void testFindAllByEmail() throws Exception {
        when(clientService.findAllByEmail(anyString())).thenReturn(List.of(client));
        mvc.perform(get("/client").param("email", "test@email.com").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

}
