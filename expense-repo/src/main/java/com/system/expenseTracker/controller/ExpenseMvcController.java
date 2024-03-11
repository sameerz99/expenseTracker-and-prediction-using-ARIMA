package com.system.expenseTracker.controller;


import com.system.expenseTracker.dto.requestDto.CategoryRequestDto;
import com.system.expenseTracker.dto.requestDto.ExpenseRequestDto;
import com.system.expenseTracker.dto.requestDto.ExpenseSearch;
import com.system.expenseTracker.dto.responseDto.CategoryResponseDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.service.CategoryService;
import com.system.expenseTracker.service.ExpenseService;
import com.system.expenseTracker.service.other.ReportService;
import com.system.expenseTracker.service.other.UserLogService;
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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ExpenseMvcController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ReportService reportService;
    private final UserLogService userLogService;
    @Autowired
    public ExpenseMvcController(ExpenseService expenseService, CategoryService categoryService, ReportService reportService, UserLogService userLogService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.reportService = reportService;
        this.userLogService = userLogService;
    }

    @GetMapping("/")
    public String index(Model model){return "index";}
    @GetMapping("/home")
    public String home(Model model){return "home";}

//    @GetMapping("/save")
//    public String getSaveExpenseInfo(Model model){
//        model.addAttribute("expense", new ExpenseRequestDto());
//        model.addAttribute("categories", categoryService.getAllCategory());
//        return "addExpense";}
    @PostMapping("/save")
    public String postSaveExpenseInfo(@ModelAttribute("expense") ExpenseRequestDto expenseRequestDto){
        expenseService.saveExpenseInfo(expenseRequestDto);
        return "redirect:/find-all";
    }
    @GetMapping("/find-all")
    public String getAllExpenses(Model model){
        String username = userLogService.getLoggedInUser().getUsername();
        List<ExpenseResponseDto> expenseList = expenseService.getAllExpenses();
        double sum = 0;
        int count = 0;
        Set<LocalDate> uniqueDates = new HashSet<>();
        for(ExpenseResponseDto expense: expenseList){
            LocalDate expenseDate = expense.getDate();
            if(!uniqueDates.contains(expenseDate)){
                uniqueDates.add(expenseDate);
                count +=1;}
            sum +=expense.getAmount();
        }
        model.addAttribute("countExpense",count);
        model.addAttribute("totalAmount",sum);
        model.addAttribute("loggedInUser",username);
        model.addAttribute("expenseList", expenseList);
        model.addAttribute("expenseSearch", new ExpenseSearch());
        model.addAttribute("expense", new ExpenseRequestDto());
        model.addAttribute("categories", categoryService.getAllCategory());


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
        if(expenseSearch.getCategoryName() != null && !expenseSearch.getCategoryName().trim().isEmpty()){
            expenses = expenseService.searchByTitle(expenseSearch.getCategoryName());
        } else if (expenseSearch.getStartDate()!=null && expenseSearch.getEndDate()!=null) {
            expenses = expenseService.searchByDateInterval(expenseSearch.getStartDate(),expenseSearch.getEndDate());
            
        }else{
            return "redirect:/find-all";
        }
        String username = userLogService.getLoggedInUser().getUsername();
        double sum = 0;
        for(ExpenseResponseDto expense: expenses){
            sum +=expense.getAmount();
        }
        model.addAttribute("totalAmount",sum);
        model.addAttribute("loggedInUser",username);
        model.addAttribute("expenseList",expenses);
        model.addAttribute("expense", new ExpenseRequestDto());
        model.addAttribute("categories", categoryService.getAllCategory());
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
