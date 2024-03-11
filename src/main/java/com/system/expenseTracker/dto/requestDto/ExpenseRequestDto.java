package com.system.expenseTracker.dto.requestDto;

import com.system.expenseTracker.model.Category;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExpenseRequestDto {
    private String expenseName;
    private double amount;
    private LocalDate date;
    private Category category;
}
