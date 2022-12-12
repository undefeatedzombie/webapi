package com.webapi.retailer.service.impl;

import com.webapi.retailer.dao.CustomerDao;
import com.webapi.retailer.dao.PurchaseDao;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private PurchaseService purchaseService;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, PurchaseService purchaseService){
        super();
        this.purchaseService = purchaseService;
        this.customerDao = customerDao;
    }

    @Override
    public void addPoints(long customerId, long purchaseId) {
        Customer customer = findCustomerById(customerId);
        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        BigDecimal value = purchase.getValue();
        int total = value.intValue();
        int points = 0;
        if(total > 100){
            points += (total - 100) * 2;
            total = 100;
        }
        if(total > 50){
            points += total - 50;
        }
        customer.setPoints(customer.getPoints() + points);
    }

    @Override
    public Purchase checkOut(long cid,BigDecimal value) {
        Purchase purchase = purchaseService.savePurchase(value);
        int total = value.intValue();
        int points = 0;
        if(total > 100){
            points += (total - 100) * 2;
            total = 100;
        }
        if(total > 50){
            points += total - 50;
        }
        Customer customer = customerDao.findById(cid).get();
        customer.setPoints(customer.getPoints() + points);
        updateCustomerById(cid,customer);
        return purchase;
    }


    @Override
    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public Customer findCustomerById(long id) {
        return customerDao.findById(id).orElseThrow(()->new RuntimeException("No Such Customer"));
    }

    @Override
    public Customer findCustomerByUsernameAndPwd(String username, String pwd) {
        return customerDao.findByUsernameAndPwd(username,pwd).orElseThrow(()->new RuntimeException("Wrong Information, please try again"));
    }

    @Override
    public void deleteCustomerById(long id) {
        customerDao.deleteById(id);
    }

    @Override
    public Customer updateCustomerById(long id, Customer customer) {
        Optional<Customer> target = customerDao.findById(id);
        if(!target.isPresent()){
            throw new RuntimeException("No Such Customer");
        }
        customer.setId(id);
        return saveCustomer(customer);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }
}
