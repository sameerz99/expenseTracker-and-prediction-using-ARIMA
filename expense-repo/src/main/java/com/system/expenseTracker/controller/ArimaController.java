package com.system.expenseTracker.controller;

import com.system.expenseTracker.dto.requestDto.ArimaRequestDto;
import com.system.expenseTracker.dto.responseDto.ArimaResponseDto;
import com.system.expenseTracker.dto.responseDto.ExpenseResponseDto;
import com.system.expenseTracker.service.ExpenseService;
import com.system.expenseTracker.service.other.ArimaApiService;
import com.system.expenseTracker.service.other.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArimaController {
    private final ArimaApiService arimaApiService;
    private final ExpenseService expenseService;
    private final UserLogService userLogService;
    @Autowired
    public ArimaController(ArimaApiService arimaApiService, ExpenseService expenseService, UserLogService userLogService) {
        this.arimaApiService = arimaApiService;
        this.expenseService = expenseService;
        this.userLogService = userLogService;
    }
    @GetMapping("/arima")
    public String arimaPage(Model model){
        String username = userLogService.getLoggedInUser().getUsername();
        model.addAttribute("loggedInUser",username);
        model.addAttribute("arimaRequest", new ArimaRequestDto());
        model.addAttribute("arimaResponse", new ArimaResponseDto());
        return "arima";
    }
    @PostMapping("/arima/predict")
    public String arimaPredict(@ModelAttribute ArimaRequestDto arimaRequestDto, Model model){
        List<Double> testData = expenseService.getDailyExpense();
        arimaRequestDto.setTsData(testData);
        ArimaResponseDto arimaResponseDto = arimaApiService.getArimaPrediction(arimaRequestDto);
        String username = userLogService.getLoggedInUser().getUsername();
        model.addAttribute("loggedInUser",username);
        model.addAttribute("arimaResponse", arimaResponseDto);
        return "arima-result";
    }
}
