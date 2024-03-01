package com.system.expenseTracker.service.impl;

import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.model.Category;
import com.system.expenseTracker.model.Expense;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.CategoryRepo;
import com.system.expenseTracker.repo.ExpenseRepo;
import com.system.expenseTracker.service.ExpenseService;
import com.system.expenseTracker.service.other.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepo expenseRepo;
    private final CategoryRepo categoryRepo;
    private final UserLogService userLogService;
    @Autowired
    public ExpenseServiceImpl(ExpenseRepo expenseRepo, CategoryRepo categoryRepo, UserLogService userLogService) {
        this.expenseRepo = expenseRepo;
        this.categoryRepo = categoryRepo;
        this.userLogService = userLogService;
    }

    @Override
    public ExpenseResponseDto saveExpenseInfo(ExpenseRequestDto expenseRequestDto) {
        User loggedInUser = userLogService.getLoggedInUser();
        Expense expense = new Expense();
        expense.setExpenseName(expenseRequestDto.getExpenseName());
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDate(expenseRequestDto.getDate());
        expense.setDescription(expenseRequestDto.getDescription());
        expense.setCategory(expenseRequestDto.getCategory());
        expense.setUser(loggedInUser);
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
        User loggedInUser = userLogService.getLoggedInUser();
        List<ExpenseResponseDto> returnList = new ArrayList<>();
        List<Expense> expenseList = expenseRepo.findByUser(loggedInUser);
        for(Expense expense: expenseList){
            ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(expense);
            returnList.add(expenseResponseDto);
        }
        return returnList;
    }

    @Override
    public List<ExpenseResponseDto> searchByTitle(String expenseName) {
        User loggedInUser = userLogService.getLoggedInUser();
        List<ExpenseResponseDto> returnList = new ArrayList<>();
        List<Expense> expenseListByTitle = expenseRepo.findByUserAndExpenseNameContainingIgnoreCase(loggedInUser,expenseName);
        for(Expense expense: expenseListByTitle){
            ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(expense);
            returnList.add(expenseResponseDto);
        }
        return returnList;
    }

    @Override
    public List<ExpenseResponseDto> searchByDateInterval(LocalDate startDate, LocalDate endDate) {
        User loggedInUser = userLogService.getLoggedInUser();
        List<ExpenseResponseDto> returnList = new ArrayList<>();
        List<Expense> expenseListByDate = expenseRepo.findByUserAndDateBetween(loggedInUser,startDate,endDate);
        for(Expense expense : expenseListByDate){
            ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(expense);
            returnList.add(expenseResponseDto);
        }
        return returnList;
    }
}
