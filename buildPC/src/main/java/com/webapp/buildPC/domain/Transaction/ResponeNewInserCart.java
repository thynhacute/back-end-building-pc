package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponeNewInserCart {
    private int componentID;
    private String componentName;
    private int price;
    private int amount;
    private String description;
    private String brand;
    private String category;
    private String image;
    private int status;
}
