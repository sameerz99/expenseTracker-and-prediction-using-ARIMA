package com.system.expenseTracker.controller;

import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping("/save")
    public ResponseEntity<ExpenseResponseDto> saveExpenseInfo(@RequestBody ExpenseRequestDto expenseRequestDto){
        return new ResponseEntity<>(expenseService.saveExpenseInfo(expenseRequestDto), HttpStatus.CREATED);
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses(){
        return new ResponseEntity<>(expenseService.getAllExpenses(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpenseInfoById(@PathVariable Integer id){
        expenseService.deleteExpenseInfoById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
