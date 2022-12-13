package com.webapi.retailer.service.impl;

import com.webapi.retailer.dao.CustomerDao;
import com.webapi.retailer.dao.PurchaseDao;
import com.webapi.retailer.exception.CustomerNotFoundException;
import com.webapi.retailer.exception.PurchaseNotFoundException;
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
    public void addPoints(long customerId, long purchaseId) throws CustomerNotFoundException, PurchaseNotFoundException {
        Customer customer = findCustomerById(customerId);
        Purchase purchase = purchaseService.findPurchaseById(purchaseId);
        // calculate the points based on the requirement
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
    public Purchase checkOut(long cid,BigDecimal value) throws CustomerNotFoundException, PurchaseNotFoundException {
        Purchase purchase = purchaseService.savePurchase(value);
        //calculate points for the customer by calling add points method
        addPoints(cid,purchase.getId());
        //update customer info in the database
        updateCustomerById(cid,this.findCustomerById(cid));
        return purchase;
    }


    @Override
    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    public Customer findCustomerById(long id) throws CustomerNotFoundException {
        Optional<Customer> byId = customerDao.findById(id);
        if(!byId.isPresent()){
            throw new CustomerNotFoundException("No Such Customer");
        }
        return byId.get();
    }

    @Override
    public Customer findCustomerByUsernameAndPwd(String username, String pwd) throws CustomerNotFoundException {
        Optional<Customer> customer = customerDao.findByUsernameAndPwd(username,pwd);
        if(!customer.isPresent()){
            throw new CustomerNotFoundException("No Such Customer");
        }
        return customer.get();
    }

    @Override
    public void deleteCustomerById(long id) {
        customerDao.deleteById(id);
    }

    @Override
    public Customer updateCustomerById(long id, Customer customer) throws CustomerNotFoundException {
        Optional<Customer> byId = customerDao.findById(id);
        if(!byId.isPresent()){
            throw new CustomerNotFoundException("No Such Customer");
        }
        customer.setId(id);
        return saveCustomer(customer);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }
}
