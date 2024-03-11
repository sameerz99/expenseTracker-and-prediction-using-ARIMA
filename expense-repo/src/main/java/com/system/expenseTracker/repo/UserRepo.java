package com.system.expenseTracker.repo;

import com.system.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query(
            nativeQuery = true,
            value = "select * from users where user_name=?1"
    )
    User getUserByUsername(String username);
}
