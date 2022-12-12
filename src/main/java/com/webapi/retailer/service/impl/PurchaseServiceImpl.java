package com.webapi.retailer.service.impl;

import com.webapi.retailer.dao.PurchaseDao;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseDao purchaseDao;

    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao){
        this.purchaseDao = purchaseDao;
    }

    @Override
    public List<Purchase> findAllPurchase() {
        return purchaseDao.findAll();
    }

    @Override
    public Purchase findPurchaseById(long id) {
        return purchaseDao.findById(id).orElseThrow(()->new RuntimeException("No Record of Purchase"));
    }

    @Override
    public void deletePurchaseById(long id) {
        purchaseDao.deleteById(id);
    }

    @Override
    public Purchase updatePurchaseById(long id, Purchase purchase) {
        Optional<Purchase> target = purchaseDao.findById(id);
        if(!target.isPresent()){
            throw new RuntimeException("No Such Record");
        }
        purchase.setId(id);
        return savePurchase(purchase);
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        return purchaseDao.save(purchase);
    }

    @Override
    public Purchase savePurchase(BigDecimal value) {
        return purchaseDao.savePurchase(value);
    }
}
