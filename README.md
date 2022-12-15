project tech stack: SpringBoot, SpringData JPA, Validation, Unit Test.


Restful APIs for Customer: [CustomerController](https://github.com/undefeatedzombie/webapi/blob/main/src/main/java/com/webapi/retailer/controller/CustomerController.java)
1. Create Customer: takes in a customer object in the request body and save it using SpringData Jpa.
```
@PostMapping
public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) throws InvalidInputException {
    if(customer == null){
        throw new InvalidInputException("Empty Request Body");
    }
    return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
}
```
The correct way to call this api is send a request: "POST http://target.domain.com/api/customer" and include a Json format object in the request body.
```
{
    username: "username",
    pwd: "pwd";
}
```
The List of purchase ids will be empty when first create and points will be zero.

2. Read Customers: first appraoch is read all customers using jpa findAll() method. Second approach is read customer by primary key which is ID in the request
```
@GetMapping("{id}")
public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) throws Exception {
    if(id <= 0){
        throw new InvalidInputException("Invalid Input");
    }
    return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
}
```
The correct way to call this api is send a request: "GET http://target.domain.com/api/customer" for getting all the customers. If you want to get a specific customer you could send request: "GET http://target.domain.com/api/customer/id" or "GET http://target.domain.com/api/username/pwd"

3. Update Customers: takes in a customer object in the request body and save it using SpringData Jpa after find the correct customer in the database.
```
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
```
The correct way to call this api is send a request: "PUT http://target.domain.com/api/customer/id" followed by a request body with a json format object

4. Delete Customer: delete customer based on the id.
```
@DeleteMapping("{id}")
public ResponseEntity<String> deleteCustomerById(@PathVariable("id") long id) throws InvalidInputException {
    if(id <= 0){
        throw new InvalidInputException("Invalid Input");
    }
    return new ResponseEntity<>("success!",HttpStatus.OK);
}
```
The correct way to call this api is send a request: "DELETE http://target.domain.com/api/customer/id"

5. Calculate purchase points based on the amount of deal and add it to the customer: above 100 it's 2 points and above 50 it's 1 point.
```
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
```
This method is called by other functions to add points based on the value of the transaction. It needs a customer id to find the customer and a purchase id to find out the value of the purchase.

Restful APIs for Purchase: [PurchaseController](https://github.com/undefeatedzombie/webapi/blob/main/src/main/java/com/webapi/retailer/controller/PurchaseController.java)
1. Create Purchase: takes in a purchase object in the request body and save it using SpringData Jpa.
```
@PostMapping
public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchase) throws InvalidInputException {
    if(purchase == null){
        throw new InvalidInputException("Empty Request Body");
    }
    return new ResponseEntity<>(purchaseService.savePurchase(purchase), HttpStatus.CREATED);
}
```
The correct way to call this api is send a request: "POST http://target.domain.com/api/purchase" and include a Json format object in the request body.
```
{
    id: id,
    value: value(BigDecimal);
}
```

2. Read Purchase: first appraoch is read all purchase using jpa findAll() method. Second approach is read purchase by primary key which is ID in the request
```
@GetMapping
public List<Purchase> getAllPurchase(){
    return purchaseService.findAllPurchase();
}

@GetMapping("{id}")
public ResponseEntity<Purchase> getPurchaseById(@PathVariable("id") long id) throws Exception {
    if(id <= 0){
        throw new InvalidInputException("id can not be zero or less than zero");
    }
    return new ResponseEntity<>(purchaseService.findPurchaseById(id), HttpStatus.OK);
}
```
The correct way to call this api is send a request: "GET http://target.domain.com/api/purchase" for getting all the purchases. If you want to get a specific purchase you could send request: "GET http://target.domain.com/api/purchase/id"


3. Update Purchase: takes in a Purchase object in the request body and save it using SpringData Jpa after find the correct purchase in the database.
```
@PutMapping("{id}")
public ResponseEntity<Purchase> updatePurchaseById(@PathVariable("id") long id,@RequestBody Purchase purchase) throws Exception {
    if(id <= 0){
        throw new InvalidInputException("id can not be zero or less than zero");
    }
    return new ResponseEntity<>(purchaseService.updatePurchaseById(id,purchase),HttpStatus.OK);
}
```
The correct way to call this api is send a request: "PUT http://target.domain.com/api/purchase/id" followed by a request body with a json format object

4. Delete Purchase: delete Purchase based on the id.
```
@DeleteMapping("{id}")
public ResponseEntity<String> deletePurchaseById(@PathVariable("id") long id) throws InvalidInputException {
    if(id <= 0){
        throw new InvalidInputException("id can not be zero or less than zero");
    }
    return new ResponseEntity<>("success!",HttpStatus.OK);
}
```
The correct way to call this api is send a request: "DELETE http://target.domain.com/api/purchase/id"

RestFul APIs for Transaction: method: calculatePoints
[CustomerController](https://github.com/undefeatedzombie/webapi/blob/main/src/main/java/com/webapi/retailer/controller/CustomerController.java)
1. Given a list of transactions, return a HashMap that uses customer as primary key and the points of each customer as value. Do the addpoints method while loop through the list of transactions.
```
@GetMapping("points")
public HashMap<Customer, BigDecimal> calculatePoints(@RequestBody List<TransactionRecord> records) throws Exception{
    if(records == null){
        throw new InvalidInputException("Empty Request Body");
    }
    return transactionRecordService.calculatePoints(records);
}
```
Call this method when you want to add points to numerous of customers with multiple transaction records, this method will loop through the list and add points to the customer based on the record.

Input validations and customized exceptions to handle invalid use cases [exceptions](https://github.com/undefeatedzombie/webapi/tree/main/src/main/java/com/webapi/retailer/exception)
1. CustomerNotFoundException: throws out this exception in case of invalid use cases regarding to the customer.
Example: there is no customer which has an id of 141 in the database and you send a request for  "GET http://target.domain.com/api/customer/141"

2. PurchaseNotFoundException：throws out this exception in case of invalid use cases regarding to the purchase.
Example: there is no purchase which has an id of 313 in the database and you send a request for  "GET http://target.domain.com/api/purchase/313"

3. InvalidInputException: throws out this exception in case of invalid input in the controller, (id does not meet the requirement or null value)
Example: when the input is invalid, id is negative or id is null value

4. Global exception handler [advice](https://github.com/undefeatedzombie/webapi/tree/main/src/main/java/com/webapi/retailer/advice)

Unit tests to make sure the API is runnable [Unit Test](https://github.com/undefeatedzombie/webapi/blob/main/src/test/java/com/webapi/retailer/RetailerApplicationTests.java)
RetailerApplicationTests uses Junit and Mockito to perform unit test to the business logic of the back end.

Performance test is needed in the next step
