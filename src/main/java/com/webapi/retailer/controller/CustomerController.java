package com.webapi.retailer.controller;

import com.webapi.retailer.exception.CustomerNotFoundException;
import com.webapi.retailer.exception.InvalidInputException;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.pojo.TransactionRecord;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.TransactionRecordService;
import org.omg.CORBA.DynAnyPackage.Invalid;
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

    @PutMapping("transaction/{id}")
    public ResponseEntity<Purchase> checkOut(@PathVariable long id, @RequestBody Purchase purchase) throws Exception {
        if(id <= 0){
            throw new InvalidInputException("Invalid Input");
        }
        if(purchase == null){
            throw new InvalidInputException("Empty Request Body");
        }
        return new ResponseEntity<>(customerService.checkOut(id,purchase.getValue()),HttpStatus.OK);
    }


    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.findAllCustomers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) throws Exception {
        if(id <= 0){
            throw new InvalidInputException("Invalid Input");
        }
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) throws InvalidInputException {
        if(customer == null){
            throw new InvalidInputException("Empty Request Body");
        }
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id") long id) throws InvalidInputException {
        if(id <= 0){
            throw new InvalidInputException("Invalid Input");
        }

        return new ResponseEntity<>("success!",HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomerById(@PathVariable("id") long id,@RequestBody Customer customer) throws Exception {
        if(id <= 0){
            throw new InvalidInputException("Invalid Input");
        }
        if(customer == null){
            throw new InvalidInputException("Empty Request Body");
        }
        return new ResponseEntity<>(customerService.updateCustomerById(id,customer),HttpStatus.OK);
    }

    @GetMapping("points")
    public HashMap<Customer, BigDecimal> calculatePoints(@RequestBody List<TransactionRecord> records) throws Exception{
        if(records == null){
            throw new InvalidInputException("Empty Request Body");
        }
        return transactionRecordService.calculatePoints(records);
    }

}
