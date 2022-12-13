package com.webapi.retailer.dao;

import com.webapi.retailer.pojo.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PurchaseDao extends JpaRepository<Purchase,Long> {
}
