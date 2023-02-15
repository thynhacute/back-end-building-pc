package com.webapp.buildPC.service;

import com.webapp.buildPC.domain.Role;
import com.webapp.buildPC.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List<User> getUser();
}
