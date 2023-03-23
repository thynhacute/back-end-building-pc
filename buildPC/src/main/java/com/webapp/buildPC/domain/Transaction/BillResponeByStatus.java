package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponeByStatus {
    private String userID;
    private int billID;
    private String gmail;
    private String phoneNumber;
    private List<ComponentRespone> component;
    private List<ResponeProductImg> product;
    private double total;
    private int amount;
    private String paymentMethod;
    private String payDate;
}
