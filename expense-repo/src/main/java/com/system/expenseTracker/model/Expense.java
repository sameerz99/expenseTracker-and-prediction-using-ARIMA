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

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id",
    foreignKey = @ForeignKey(name = "FK_EXPENSE_CATEGORY"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
    foreignKey = @ForeignKey(name ="FK_EXPENSE_USER"))
    private User user;

}
