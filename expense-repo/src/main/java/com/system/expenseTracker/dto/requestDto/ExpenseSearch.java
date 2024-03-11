package com.system.expenseTracker.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSearch {
    private String categoryName;
    private LocalDate startDate;
    private LocalDate endDate;
}
