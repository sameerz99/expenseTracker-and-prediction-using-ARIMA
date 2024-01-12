package com.system.expenseTracker.repo;

import com.system.expenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {
    List<Expense> findByExpenseNameContainingIgnoreCase(String expenseName);
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
