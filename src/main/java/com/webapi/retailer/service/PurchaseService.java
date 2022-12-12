package com.webapi.retailer.service;

import com.webapi.retailer.pojo.Purchase;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseService {
    public List<Purchase> findAllPurchase();

    public Purchase findPurchaseById(long id);

    public void deletePurchaseById(long id);

    public Purchase updatePurchaseById(long id,Purchase purchase);

    public Purchase savePurchase(Purchase purchase);

    public Purchase savePurchase(BigDecimal value);
}
