package com.webapi.retailer.exception;

public class PurchaseNotFoundException extends Exception{
    public PurchaseNotFoundException(String message){
        super(message);
    }
}
