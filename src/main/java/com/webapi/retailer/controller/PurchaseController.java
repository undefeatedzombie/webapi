package com.webapi.retailer.controller;

import com.webapi.retailer.exception.InvalidInputException;
import com.webapi.retailer.exception.PurchaseNotFoundException;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;
    @Autowired
    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> getAllPurchase(){
        return purchaseService.findAllPurchase();
    }

    @GetMapping("{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("id") long id) throws Exception {
        if(id <= 0){
            throw new InvalidInputException("id can not be zero or less than zero");
        }
        return new ResponseEntity<>(purchaseService.findPurchaseById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchase) throws InvalidInputException {
        if(purchase == null){
            throw new InvalidInputException("Empty Request Body");
        }
        return new ResponseEntity<>(purchaseService.savePurchase(purchase), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePurchaseById(@PathVariable("id") long id) throws InvalidInputException {
        if(id <= 0){
            throw new InvalidInputException("id can not be zero or less than zero");
        }
        return new ResponseEntity<>("success!",HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Purchase> updatePurchaseById(@PathVariable("id") long id,@RequestBody Purchase purchase) throws Exception {
        if(id <= 0){
            throw new InvalidInputException("id can not be zero or less than zero");
        }
        return new ResponseEntity<>(purchaseService.updatePurchaseById(id,purchase),HttpStatus.OK);
    }
}
