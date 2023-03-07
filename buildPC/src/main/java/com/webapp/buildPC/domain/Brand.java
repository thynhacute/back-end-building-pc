package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand implements Serializable {

    private int brandID;
    private String brandName;
    private String description;

}
