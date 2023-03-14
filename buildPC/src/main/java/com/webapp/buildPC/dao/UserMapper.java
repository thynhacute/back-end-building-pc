package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Transaction.ResponseUser;
import com.webapp.buildPC.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User findUserById(String userID);

    User findUserByEmail(String email);

    void createUserByNotFound(User user);

    ResponseUser responseUserByID(String userID);

    List<ResponseUser> getListUserWithRoleUser();
}
