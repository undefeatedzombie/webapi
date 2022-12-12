package com.webapi.retailer.service.impl;

import com.webapi.retailer.dao.TransactionRecordDao;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.pojo.TransactionRecord;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.PurchaseService;
import com.webapi.retailer.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    public HashMap<Customer,BigDecimal> calculatePoints(List<TransactionRecord> records) {
        HashMap<Customer, BigDecimal> result = new HashMap<Customer, BigDecimal>();
        for(TransactionRecord record : records){
            Purchase purchase = purchaseService.findPurchaseById(record.getPurchaseId());
            Customer customer = customerService.findCustomerById(record.getCustomerId());
            customerService.addPoints(record.getCustomerId(),record.getPurchaseId());
            result.put(customer,purchaseService.findPurchaseById(purchase.getId()).getValue());
        }
        return result;
    }

}
