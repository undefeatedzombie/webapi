package com.webapi.retailer.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String pwd;

    @ElementCollection(targetClass=Integer.class)
    private List<Long> purchaseId;

    private int points;
}
