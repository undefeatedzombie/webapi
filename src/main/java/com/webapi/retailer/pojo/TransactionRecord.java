package com.webapi.retailer.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecord {
    @Id
    private long id;

    @Column
    private long customerId;

    @Column
    private long purchaseId;
}
