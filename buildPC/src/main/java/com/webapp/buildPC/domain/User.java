package com.webapp.buildPC.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userID;
    private String userName;
    private String phone;
    private String email;
    private String facebook;
    private String password;
    private String image;
    private int roleID;
    private int status;
}
