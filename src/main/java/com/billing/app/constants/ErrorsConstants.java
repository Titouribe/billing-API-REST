package com.billing.app.constants;

import org.springframework.stereotype.Component;

@Component
public class ErrorsConstants {

    private String NOT_FOUND = " not found.";
    public String notFound(String entity, String attribute) {
        return entity + " with id: " + attribute + NOT_FOUND;
    }
}
