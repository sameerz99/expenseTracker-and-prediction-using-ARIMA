package com.system.expenseTracker.repo;

import com.system.expenseTracker.model.Category;
import com.system.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    List<Category> findByUser(User user);
    @Query("SELECT c FROM Category c JOIN FETCH c.user WHERE c.categoryId = :categoryId AND c.user = :user")
    Optional<Category> findByIdAndUser(@Param("categoryId") Integer categoryId, @Param("user") User user);
}
