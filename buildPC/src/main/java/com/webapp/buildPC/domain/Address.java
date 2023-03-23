package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private int addressID;
    private String provice;
    private String district;
    private String ward;
    private String apartmentNumber;
    private String codeArea;
}
