package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestComponentByAttribute {

    private String attributeID;
    private String categoryID;

}
