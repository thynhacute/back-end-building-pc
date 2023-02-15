package com.webapp.buildPC.repo;

import com.webapp.buildPC.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User save(User user);
    List<User> findAll();
}
