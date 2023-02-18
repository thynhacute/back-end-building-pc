package com.webapp.buildPC.service.interf;

import com.webapp.buildPC.domain.Role;
import com.webapp.buildPC.domain.User;

import java.util.List;

public interface UserService {

    User findUserByID(String userID);

    User findUserByEmail(String email);

    User createUserByNotFound(User user);


}
