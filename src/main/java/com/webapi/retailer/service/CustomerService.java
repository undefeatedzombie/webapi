package com.webapi.retailer.service;

import com.webapi.retailer.exception.CustomerNotFoundException;
import com.webapi.retailer.exception.PurchaseNotFoundException;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    public void addPoints(long customerId, long purchaseId) throws CustomerNotFoundException, PurchaseNotFoundException;

    public Purchase checkOut(long cid,BigDecimal value) throws CustomerNotFoundException, PurchaseNotFoundException;

    public List<Customer> findAllCustomers();

    public Customer findCustomerById(long id) throws CustomerNotFoundException;

    public Customer findCustomerByUsernameAndPwd(String username, String pwd) throws CustomerNotFoundException;

    public void deleteCustomerById(long id);

    public Customer updateCustomerById(long id,Customer customer) throws CustomerNotFoundException;

    public Customer saveCustomer(Customer customer);
}
