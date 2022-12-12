package com.webapi.retailer.dao;

import com.webapi.retailer.pojo.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRecordDao extends JpaRepository<TransactionRecord,Long> {
}
