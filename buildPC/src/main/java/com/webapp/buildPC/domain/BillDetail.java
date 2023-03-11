package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetail {
    private int billID;
    private String productID;
    private int componentID;
    private int amount;
}
