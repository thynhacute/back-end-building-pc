package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRespone {
    private int billID;
    private List<ResponeNewInserCart> componentDetail;
    private int status;
    private int total;
    private int amount;
    private String paymentMethod;
    private String payDate;
}
