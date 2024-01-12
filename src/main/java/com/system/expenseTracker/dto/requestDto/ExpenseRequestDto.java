package com.system.expenseTracker.dto.requestDto;

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
    private String description;
}
