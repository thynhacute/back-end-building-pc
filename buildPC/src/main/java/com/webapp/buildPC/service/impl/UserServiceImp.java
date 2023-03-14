package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.RoleMapper;
import com.webapp.buildPC.dao.UserMapper;
import com.webapp.buildPC.domain.Role;
import com.webapp.buildPC.domain.Transaction.ResponseUser;
import com.webapp.buildPC.domain.User;
import com.webapp.buildPC.service.interf.RoleService;
import com.webapp.buildPC.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserMapper userMapper;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserById(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Role userRole = roleService.findRoleName(user.getRoleID());
        authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
        return new org.springframework.security.core.userdetails.User(user.getUserID(), user.getPassword(), authorities);
    }

    @Override
    public User findUserByID(String userID) {
        return userMapper.findUserById(userID);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public User createUserByNotFound(User user) {
        user.setPassword(passwordEncoder.encode("1"));
        user.setRoleID(2);
        userMapper.createUserByNotFound(user);
        return user;
    }

    @Override
    public ResponseUser responseUserByID(String userID) {
        return userMapper.responseUserByID(userID);
    }
    @Override
    public List<ResponseUser> getListUserWithRoleUser() {
        return userMapper.getListUserWithRoleUser();
    }
}

