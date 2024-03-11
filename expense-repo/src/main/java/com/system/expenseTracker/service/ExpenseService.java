package com.system.expenseTracker.service;

import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto saveExpenseInfo(ExpenseRequestDto expenseRequestDto);

    void deleteExpenseInfoById(Integer id);
    List<ExpenseResponseDto> getAllExpenses();
    List<ExpenseResponseDto> searchByTitle(String expenseName);
    List<ExpenseResponseDto> searchByDateInterval(LocalDate startDate, LocalDate endDate);
    List<Double> getDailyExpense();
}
