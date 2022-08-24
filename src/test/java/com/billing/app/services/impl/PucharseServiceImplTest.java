package com.billing.app.services.impl;

import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.model.mappers.ProductMapper;
import com.billing.app.repositories.IBillRepository;
import com.billing.app.services.IPucharseService;
import com.billing.app.util.GenerateOrderTrackingNumber;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

}
