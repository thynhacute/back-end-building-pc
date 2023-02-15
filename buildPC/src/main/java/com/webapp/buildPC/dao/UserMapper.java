package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findUserById(String userID);

}
