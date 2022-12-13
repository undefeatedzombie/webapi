project tech stack: SpringBoot, SpringData JPA, Validation, Unit Test.


Restful APIs for Customer: [CustomerController](https://github.com/undefeatedzombie/webapi/blob/main/src/main/java/com/webapi/retailer/controller/CustomerController.java)
1. Create Customer: takes in a customer object in the request body and save it using SpringData Jpa.
2. Read Customers: first appraoch is read all customers using jpa findAll() method. Second approach is read customer by primary key which is ID in the request
3. Update Customers: takes in a customer object in the request body and save it using SpringData Jpa after find the correct customer in the database.
4. Delete Customer: delete customer based on the id.
5. Calculate purchase points based on the amount of deal and add it to the customer: above 100 it's 2 points and above 50 it's 1 point.

Restful APIs for Purchase: [PurchaseController](https://github.com/undefeatedzombie/webapi/blob/main/src/main/java/com/webapi/retailer/controller/PurchaseController.java)
1. Create Purchase: takes in a purchase object in the request body and save it using SpringData Jpa.
2. Read Purchase: first appraoch is read all purchase using jpa findAll() method. Second approach is read purchase by primary key which is ID in the request
3. Update Purchase: takes in a Purchase object in the request body and save it using SpringData Jpa after find the correct purchase in the database.
4. Delete Purchase: delete Purchase based on the id.

RestFul APIs for Transaction: method: calculatePoints
[CustomerController](https://github.com/undefeatedzombie/webapi/blob/main/src/main/java/com/webapi/retailer/controller/CustomerController.java)
1. Given a list of transactions, return a HashMap that uses customer as primary key and the points of each customer as value. Do the addpoints method while loop through the list of transactions.

Input validations and customized exceptions to handle invalid use cases[exceptions](https://github.com/undefeatedzombie/webapi/tree/main/src/main/java/com/webapi/retailer/exception)
1. CustomerNotFoundException: throws out this exception in case of invalid use cases regarding to the customer.
2. PurchaseNotFoundException：throws out this exception in case of invalid use cases regarding to the purchase.
3. InvalidInputException: throws out this exception in case of invalid input in the controller, (id does not meet the requirement or null value)
4. Global exception handler[advice](https://github.com/undefeatedzombie/webapi/tree/main/src/main/java/com/webapi/retailer/advice)
5.
Unit tests to make sure the API is runnable[Unit Test](https://github.com/undefeatedzombie/webapi/blob/main/src/test/java/com/webapi/retailer/RetailerApplicationTests.java)
RetailerApplicationTests uses Junit and Mockito to perform unit test to the business logic of the back end.

Performance test is needed in the next step
