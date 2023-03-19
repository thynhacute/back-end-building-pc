package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentRespone {
    private int componentID;
    private String componentName;
    private int price;
    private int amount;
    private String image;
}
