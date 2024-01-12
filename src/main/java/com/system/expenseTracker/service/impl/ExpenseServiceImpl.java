package com.system.expenseTracker.service.impl;

import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.model.Expense;
import com.system.expenseTracker.repo.ExpenseRepo;
import com.system.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepo expenseRepo;
    @Autowired
    public ExpenseServiceImpl(ExpenseRepo expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @Override
    public ExpenseResponseDto saveExpenseInfo(ExpenseRequestDto expenseRequestDto) {
        Expense expense = new Expense();
        expense.setExpenseName(expenseRequestDto.getExpenseName());
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDate(expenseRequestDto.getDate());
        expense.setDescription(expenseRequestDto.getDescription());
        Expense savedExpenses=expenseRepo.save(expense);
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(savedExpenses);

        return expenseResponseDto;
    }

    @Override
    public void deleteExpenseInfoById(Integer id) {
        expenseRepo.deleteById(id);
    }

    @Override
    public List<ExpenseResponseDto> getAllExpenses() {
        List<ExpenseResponseDto> returnList = new ArrayList<>();
        List<Expense> expenseList = expenseRepo.findAll();
        for(Expense expense: expenseList){
            ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(expense);
            returnList.add(expenseResponseDto);
        }
        return returnList;
    }

    @Override
    public List<ExpenseResponseDto> searchByTitle(String expenseName) {
        List<ExpenseResponseDto> returnList = new ArrayList<>();
        List<Expense> expenseListByTitle = expenseRepo.findByExpenseNameContainingIgnoreCase(expenseName);
        for(Expense expense: expenseListByTitle){
            ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(expense);
            returnList.add(expenseResponseDto);
        }
        return returnList;
    }

    @Override
    public List<ExpenseResponseDto> searchByDateInterval(LocalDate startDate, LocalDate endDate) {
        List<ExpenseResponseDto> returnList = new ArrayList<>();
        List<Expense> expenseListByDate = expenseRepo.findByDateBetween(startDate,endDate);
        for(Expense expense : expenseListByDate){
            ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(expense);
            returnList.add(expenseResponseDto);
        }
        return returnList;
    }
}
