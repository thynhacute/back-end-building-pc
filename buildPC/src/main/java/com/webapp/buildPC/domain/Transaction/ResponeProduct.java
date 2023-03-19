package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponeProduct {
    private String productID;
    private List<String> productDetail;
    private int amount;
    private double total;
}
