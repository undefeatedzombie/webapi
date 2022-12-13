package com.webapi.retailer.service.impl;

import com.webapi.retailer.dao.TransactionRecordDao;
import com.webapi.retailer.exception.CustomerNotFoundException;
import com.webapi.retailer.exception.PurchaseNotFoundException;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.pojo.TransactionRecord;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.PurchaseService;
import com.webapi.retailer.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
@Service
public class TransactionRecordServiceImpl implements TransactionRecordService {

    private TransactionRecordDao transactionRecordDao;
    private CustomerService customerService;
    private PurchaseService purchaseService;

    @Autowired
    public TransactionRecordServiceImpl(TransactionRecordDao transactionRecordDao,PurchaseService purchaseService,CustomerService customerService){
        super();
        this.transactionRecordDao = transactionRecordDao;
        this.purchaseService = purchaseService;
        this.customerService = customerService;
    }

    @Override
    public List<TransactionRecord> findAllTransactionRecord() {
        return transactionRecordDao.findAll();
    }

    @Override
    public TransactionRecord findTransactionRecordById(long id) {
        return transactionRecordDao.findById(id).orElseThrow(()->new RuntimeException("No Such Record"));
    }

    @Override
    public void deleteTransactionRecordById(long id) {
        transactionRecordDao.deleteById(id);
    }

    @Override
    public TransactionRecord saveTransactionRecord(TransactionRecord transactionRecord) {
        return transactionRecordDao.save(transactionRecord);
    }

    @Override
    public HashMap<Customer,BigDecimal> calculatePoints(List<TransactionRecord> records) throws PurchaseNotFoundException, CustomerNotFoundException {
        HashMap<Customer, BigDecimal> result = new HashMap<Customer, BigDecimal>();
        //takes a List of records as input and calculate points for each customer
        for(TransactionRecord record : records){
            Purchase purchase = purchaseService.findPurchaseById(record.getPurchaseId());
            Customer customer = customerService.findCustomerById(record.getCustomerId());
            //adding points
            customerService.addPoints(record.getCustomerId(),record.getPurchaseId());
            //put in the result map
            result.put(customer,purchaseService.findPurchaseById(purchase.getId()).getValue());
        }
        return result;
    }

}
