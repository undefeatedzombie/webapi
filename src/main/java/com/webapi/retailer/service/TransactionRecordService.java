package com.webapi.retailer.service;

import com.webapi.retailer.exception.CustomerNotFoundException;
import com.webapi.retailer.exception.PurchaseNotFoundException;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.pojo.TransactionRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface TransactionRecordService {
    public List<TransactionRecord> findAllTransactionRecord();

    public TransactionRecord findTransactionRecordById(long id);

    public void deleteTransactionRecordById(long id);

    public TransactionRecord saveTransactionRecord(TransactionRecord transactionRecord);

    public HashMap<Customer,BigDecimal> calculatePoints(List<TransactionRecord> records) throws PurchaseNotFoundException, CustomerNotFoundException;



}
