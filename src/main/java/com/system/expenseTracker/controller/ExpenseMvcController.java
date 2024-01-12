package com.system.expenseTracker.controller;


import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.requestDto.ExpenseSearch;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
public class ExpenseMvcController {
    private final ExpenseService expenseService;
    @Autowired
    public ExpenseMvcController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public String home(Model model){return "index";}

    @GetMapping("/save")
    public String saveExpenseInfo(Model model){
        model.addAttribute("expense", new ExpenseRequestDto());
        return "addExpense";}
    @PostMapping("/save")
    public String saveExpenseInfo(@ModelAttribute("expense")ExpenseRequestDto expenseRequestDto){
        expenseService.saveExpenseInfo(expenseRequestDto);
        return "redirect:/";
    }
    @GetMapping("/find-all")
    public String getAllExpenses(Model model){
        model.addAttribute("expenseList", expenseService.getAllExpenses());
        model.addAttribute("expenseSearch", new ExpenseSearch());
        return "expenseList";
    }

//    @GetMapping("/find-all/search")
//    public String searchExpensesByTitle(@RequestParam String expenseName, Model model){
//        List<ExpenseResponseDto> expenses = expenseService.searchByTitle(expenseName);
//        model.addAttribute("expensesByTitle",expenses);
//        return "expenseList";
//    }

    @GetMapping("/find-all/search")
    public String searchExpenses(@ModelAttribute ExpenseSearch expenseSearch, Model model){
        List<ExpenseResponseDto> expenses;
        if(expenseSearch.getExpenseName() != null && !expenseSearch.getExpenseName().trim().isEmpty()){
            expenses = expenseService.searchByTitle(expenseSearch.getExpenseName());
        } else if (expenseSearch.getStartDate()!=null && expenseSearch.getEndDate()!=null) {
            expenses = expenseService.searchByDateInterval(expenseSearch.getStartDate(),expenseSearch.getEndDate());
            
        }else{
            return "redirect:/find-all";
        }
        model.addAttribute("expensesBySearch",expenses);
        return "expenseList";
    }


}
