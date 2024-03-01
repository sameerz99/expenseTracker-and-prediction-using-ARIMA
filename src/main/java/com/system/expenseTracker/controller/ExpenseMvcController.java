package com.system.expenseTracker.controller;


import com.system.expenseTracker.dto.requestDto.CategoryRequestDto;
import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.requestDto.ExpenseSearch;
import com.system.expenseTracker.dto.responseDto.CategoryResponseDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.service.CategoryService;
import com.system.expenseTracker.service.ExpenseService;
import com.system.expenseTracker.service.other.ReportService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
public class ExpenseMvcController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ReportService reportService;
    @Autowired
    public ExpenseMvcController(ExpenseService expenseService, CategoryService categoryService, ReportService reportService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.reportService = reportService;
    }

    @GetMapping("/")
    public String home(Model model){return "index";}

    @GetMapping("/save")
    public String getSaveExpenseInfo(Model model){
        model.addAttribute("expense", new ExpenseRequestDto());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "addExpense";}
    @PostMapping("/save")
    public String postSaveExpenseInfo(@ModelAttribute("expense") ExpenseRequestDto expenseRequestDto){
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

    @GetMapping("/find-all/delete/{id}")
    public String deleteExpense(@PathVariable Integer id){
        expenseService.deleteExpenseInfoById(id);
        return "redirect:/find-all";
    }

    @GetMapping("/downloadReport")
    public ResponseEntity<byte[]> downloadReport() {
        try {
            byte[] reportContent = reportService.exportReport();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "expense_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportContent);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }
}
