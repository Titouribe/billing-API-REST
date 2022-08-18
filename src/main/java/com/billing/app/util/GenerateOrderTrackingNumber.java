package com.billing.app.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateOrderTrackingNumber {

    public static String generateRandomUUID(){
        return UUID.randomUUID().toString();
    }
}
