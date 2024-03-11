package com.system.expenseTracker.repo;

import com.system.expenseTracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM role where name=?1"
    )
    List<Role> getUserRole(String userRole);
}
