package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    private int cartID;
    private String productID;
    private int componentID;
    private int amount;

    public CartDetail(String productID, int componentID, int amount) {
        this.productID = productID;
        this.componentID = componentID;
        this.amount = amount;
    }
}
