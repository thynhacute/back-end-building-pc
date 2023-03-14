package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponeByStatus {    private int billID;
    private List<ResponeNewInserCart> componentDetail;
    private String userID;
    private int total;
    private int amount;
    private String paymentMethod;
    private String payDate;
}
