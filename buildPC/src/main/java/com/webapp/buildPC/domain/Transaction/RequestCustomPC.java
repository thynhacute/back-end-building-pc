package com.webapp.buildPC.domain.Transaction;

import com.webapp.buildPC.domain.Component;
import com.webapp.buildPC.domain.PCDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCustomPC {
    private String productID;
    private String imageProduct;
    private List<String> listComponent;
    private String userID;
    private int amount;
    private int total;

}
