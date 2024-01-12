package com.system.expenseTracker.dto.responseDto;

import com.system.expenseTracker.model.Expense;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponseDto {
    private Integer expenseId;
    private String expenseName;
    private double amount;
    private LocalDate date;
    private String description;

    public ExpenseResponseDto(Expense expense){
        this.expenseId = expense.getExpenseId();
        this.expenseName=expense.getExpenseName();
        this.amount=expense.getAmount();
        this.date=expense.getDate();
        this.description=expense.getDescription();

    }
}
