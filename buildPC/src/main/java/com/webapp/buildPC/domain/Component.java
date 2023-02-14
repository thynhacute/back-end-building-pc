package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Component {

    private int componentID;
    private String componentName;
    private int price;
    private int amount;
    private String image;
    private String description;
    private int brandID;
    private String categoryID;
    private int feedbackID;
    private int status;

}
