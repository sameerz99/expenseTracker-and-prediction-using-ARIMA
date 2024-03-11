package com.system.expenseTracker.repo;

import com.system.expenseTracker.model.Expense;
import com.system.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {
    List<Expense> findByUserAndCategoryCategoryNameContainingIgnoreCase(User user,String categoryName);
    List<Expense> findByUserAndDateBetween(User user,LocalDate startDate, LocalDate endDate);

    List<Expense> findByUser(User user);
}
