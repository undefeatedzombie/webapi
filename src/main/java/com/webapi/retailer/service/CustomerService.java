package com.webapi.retailer.service;

import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    public void addPoints(long customerId, long purchaseId);

    public Purchase checkOut(long cid,BigDecimal value);

    public List<Customer> findAllCustomers();

    public Customer findCustomerById(long id);

    public Customer findCustomerByUsernameAndPwd(String username, String pwd);

    public void deleteCustomerById(long id);

    public Customer updateCustomerById(long id,Customer customer);

    public Customer saveCustomer(Customer customer);
}
