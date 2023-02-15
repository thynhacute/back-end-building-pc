package com.webapp.buildPC.repo;

import com.webapp.buildPC.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role save(Role role);
}
