package com.moneytransfer;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
