package com.webapp.buildPC.service.impl;

import com.webapp.buildPC.dao.RoleMapper;
import com.webapp.buildPC.domain.Role;
import com.webapp.buildPC.service.interf.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private  final RoleMapper roleMapper;

    @Override
    public Role findRoleName(int roleID) {
        return roleMapper.findRoleName(roleID);
    }
}
