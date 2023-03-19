package com.webapp.buildPC.domain.Transaction;

import com.webapp.buildPC.domain.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDetail {
    private String productID;
    private List<Component> components;
    private int amount;
    private double total;

}
