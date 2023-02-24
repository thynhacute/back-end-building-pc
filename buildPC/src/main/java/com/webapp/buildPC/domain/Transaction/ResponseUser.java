package com.webapp.buildPC.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUser {
    private String userID;
    private String userName;
    private String phone;
    private String email;
    private String facebook;
    private String image;
    private String roleName;
    private int status;


}
