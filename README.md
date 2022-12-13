project tech stack: SpringBoot, SpringData JPA, Validation, Unit Test.
Restful APIs for Customer: 
1. Create Customer: takes in a customer object in the request body and save it using SpringData Jpa
2. Read Customers: first appraoch is read all customers using jpa findAll() method. Second approach is read customer by primary key which is ID in the request
Calculate purchase points based on the amount of deal and add it to the customer
3. Update Customers: takes in a customer object in the request body and save it using SpringData Jpa after find the correct customer in the database.

Input validations and customized exceptions to handle invalid use cases

Unit tests to make sure the API is runnable

Performance test is needed in the next step
