package com.webapp.buildPC.dao;

import com.webapp.buildPC.domain.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    Role findRoleName(int roleID);
}
