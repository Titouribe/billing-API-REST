package com.billing.app.exceptions;

import lombok.Data;

@Data
public class RequestException extends RuntimeException{
    private String code;
    public RequestException(String errorCode, String message) {
        super(message);
        this.code = errorCode;
    }

}
