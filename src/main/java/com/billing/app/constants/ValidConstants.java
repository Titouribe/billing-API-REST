package com.billing.app.constants;

import org.springframework.stereotype.Component;


@Component
public class ValidConstants {

    private final String DELETE = " found and deleted";
    private final String UPDATE = " found and updated";
    public String foundAndDelete(String reference){
        return reference + DELETE;
    }
    public String foundAndUpdated(String reference){
        return reference + UPDATE;
    }

}
