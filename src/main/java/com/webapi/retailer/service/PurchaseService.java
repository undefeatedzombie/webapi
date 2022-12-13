package com.webapi.retailer.service;

import com.webapi.retailer.exception.PurchaseNotFoundException;
import com.webapi.retailer.pojo.Purchase;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseService {
    public List<Purchase> findAllPurchase();

    public Purchase findPurchaseById(long id) throws PurchaseNotFoundException;

    public void deletePurchaseById(long id);

    public Purchase updatePurchaseById(long id,Purchase purchase) throws PurchaseNotFoundException;

    public Purchase savePurchase(Purchase purchase);

    public Purchase savePurchase(BigDecimal value);
}
