package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAmount {
    private String name;
    private int amount;
    private int price;
    private int totally;
    private String image;
}
