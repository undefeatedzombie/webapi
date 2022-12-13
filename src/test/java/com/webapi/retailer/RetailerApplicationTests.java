package com.webapi.retailer;

import com.webapi.retailer.controller.CustomerController;
import com.webapi.retailer.dao.CustomerDao;
import com.webapi.retailer.dao.PurchaseDao;
import com.webapi.retailer.dao.TransactionRecordDao;
import com.webapi.retailer.exception.CustomerNotFoundException;
import com.webapi.retailer.exception.PurchaseNotFoundException;
import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import com.webapi.retailer.pojo.TransactionRecord;
import com.webapi.retailer.service.CustomerService;
import com.webapi.retailer.service.PurchaseService;
import com.webapi.retailer.service.TransactionRecordService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetailerApplicationTests {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private TransactionRecordService transactionRecordService;
	@MockBean
	private CustomerDao customerDao;
	@MockBean
	private PurchaseDao purchaseDao;
	@MockBean
	private TransactionRecordDao transactionRecordDao;



	private MockMvc mockMvc;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		CustomerController controller = new CustomerController(customerService,transactionRecordService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@org.junit.Test
	public void testGetCustomer(){
		when(customerDao.findAll()).thenReturn(Stream.of(new Customer(1,"test1","test123",null,0),
				new Customer(2,"test2","test123",null,1)).collect(Collectors.toList()));
		assertEquals(2,customerService.findAllCustomers().size());
	}

	@org.junit.Test
	public void testGetPurchase(){
		when(purchaseDao.findAll()).thenReturn(Stream.of(new Purchase(1,new BigDecimal(1.0)),
				new Purchase(2,new BigDecimal(2.0))).collect(Collectors.toList()));
		assertEquals(2,purchaseService.findAllPurchase().size());
	}

	@org.junit.Test
	public void testInvalidGetCustomerById() throws CustomerNotFoundException {
		assertThrows(CustomerNotFoundException.class,()->customerService.findCustomerById(-1));
		assertThrows(CustomerNotFoundException.class,()->customerService.findCustomerById(0));
	}

	@org.junit.Test
	public void testGetCustomerById() throws CustomerNotFoundException {
		Customer s = new Customer(1,"test","test",new ArrayList<>(),0);
		when(customerDao.findById(s.getId())).thenReturn(Optional.of(s));
		assertEquals(s, customerService.findCustomerById(s.getId()));
	}

	@org.junit.Test
	public void testGetPurchaseById() throws PurchaseNotFoundException {
		Purchase p = new Purchase(1,new BigDecimal(10.0));
		when(purchaseDao.findById(p.getId())).thenReturn(Optional.of(p));
		assertEquals(p, purchaseService.findPurchaseById(p.getId()));
	}

	@org.junit.Test
	public void testSaveCustomer(){
		Customer temp = new Customer();
		when(customerDao.save(temp)).thenReturn(temp);
		assertEquals(temp,customerService.saveCustomer(temp));
	}

	@org.junit.Test
	public void testSavePurchase(){
		Purchase temp = new Purchase();
		when(purchaseDao.save(temp)).thenReturn(temp);
		assertEquals(temp,purchaseService.savePurchase(temp));
	}

	@org.junit.Test
	public void testDeleteCustomer() throws Exception {
		Customer temp = new Customer(1,"test","test",new ArrayList<Long>(),0);
		customerService.deleteCustomerById(temp.getId());
		verify(customerDao,times(1)).deleteById(temp.getId());
	}

	@org.junit.Test
	public void testDeletePurchase() throws Exception {
		Purchase temp = new Purchase(1,new BigDecimal(4.4));
		purchaseService.deletePurchaseById(temp.getId());
		verify(purchaseDao,times(1)).deleteById(temp.getId());
	}


}
