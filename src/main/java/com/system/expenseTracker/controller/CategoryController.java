package com.system.expenseTracker.controller;

import com.system.expenseTracker.dto.requestDto.CategoryRequestDto;
import com.system.expenseTracker.dto.responseDto.CategoryResponseDto;
import com.system.expenseTracker.service.CategoryService;
import com.system.expenseTracker.service.other.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final UserLogService userLogService;
    @Autowired
    public CategoryController(CategoryService categoryService, UserLogService userLogService) {
        this.categoryService = categoryService;
        this.userLogService = userLogService;
    }
//    @GetMapping("/save-category")
//    public String getSaveCategoryInfo(Model model){
//        model.addAttribute("category", new CategoryRequestDto());
//        return "addCategory";
//    }
    @PostMapping("/save-category")
    public String postSaveCategoryInfo(@ModelAttribute("category") CategoryRequestDto category){
        categoryService.addCategory(category);
        return "redirect:/find-all-category";
    }

    @GetMapping("/find-all-category")
    public String getAllCategory(Model model){
        String username = userLogService.getLoggedInUser().getUsername();
        model.addAttribute("loggedInUser",username);
        model.addAttribute("category", new CategoryRequestDto());
        model.addAttribute("categoryList", categoryService.getAllCategory());
        return "categoryList";
    }
    @GetMapping("/find-all-category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        return "redirect:/find-all-category";
    }
}
