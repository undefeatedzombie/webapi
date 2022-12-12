package com.webapi.retailer.controller;

import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.TransactionRecord;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    private CustomerService customerService;
    private TransactionRecordService transactionRecordService;
    @Autowired
    public CustomerController(CustomerService customerService, TransactionRecordService transactionRecordService){
        super();
        this.transactionRecordService = transactionRecordService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.findAllCustomers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id){
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id") long id){
        return new ResponseEntity<>("success!",HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") long id,@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.updateCustomerById(id,customer),HttpStatus.OK);
    }

    @GetMapping("points")
    public HashMap<Customer, BigDecimal> calculatePoints(@RequestBody List<TransactionRecord> records){

        return transactionRecordService.calculatePoints(records);
    }

}
