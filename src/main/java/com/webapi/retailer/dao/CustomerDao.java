package com.webapi.retailer.dao;

import com.webapi.retailer.pojo.Customer;
import com.webapi.retailer.pojo.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Long> {
    public Optional<Customer> findByUsernameAndPwd(String username, String pwd);

    //public Purchase savePurchase(BigDecimal value);
}
