package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private int billID;
    private int total;
    private int status;
    private String paymentMethod;
    private Date payDate;
    private String userID;
}
