package com.system.expenseTracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_id")
    private Integer expenseId;
    @Column(name = "expense_name")
    private String expenseName;
    private double amount;
    private LocalDate date;
    private String description;
}
