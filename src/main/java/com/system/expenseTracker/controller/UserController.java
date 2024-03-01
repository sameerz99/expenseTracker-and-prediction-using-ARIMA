package com.system.expenseTracker.controller;

import com.system.expenseTracker.dto.requestDto.UserRequestDto;
import com.system.expenseTracker.dto.responseDto.UserResponseDto;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.service.UserService;
import com.system.expenseTracker.service.other.CategoryInitService;
import com.system.expenseTracker.service.other.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    CategoryInitService categoryInitService;

    @GetMapping("/sign-up")
    public String getSignUp(Model model, @ModelAttribute("errorMsg") String errorMsg){
        model.addAttribute("dto", new UserRequestDto());
        model.addAttribute("errorMsg", errorMsg);
        return "signUp";
    }
    @PostMapping("/sign-up")
    public String signUpData(Model model, RedirectAttributes redirectAttributes, @ModelAttribute UserRequestDto userRequestDto){
        try{
            User registeredUser=userService.saveUserToTable(userRequestDto);
            if(registeredUser!=null){
                categoryInitService.associateDefaultCategoriesWithNewUser(registeredUser);

//            Sending the email after successful sign up

            String subject = "Expense Tracking System";

            String message = "Thank you for signing up.";

            emailService.sendEmail(userRequestDto.getEmail(), subject, message);
            }


        }catch (DataIntegrityViolationException e){
            redirectAttributes.addAttribute("errorMsg", "Username Taken.");
            return "redirect:/sign-up";
        }
        return "redirect:/login";
    }
}
