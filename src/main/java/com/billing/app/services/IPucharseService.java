package com.billing.app.services;

import com.billing.app.model.dtos.Pucharse;
import com.billing.app.model.dtos.PucharseResponse;

public interface IPucharseService {

    public PucharseResponse placeOrder(Pucharse pucharse);
}
