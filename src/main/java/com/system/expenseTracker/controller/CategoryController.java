package com.system.expenseTracker.controller;

import com.system.expenseTracker.dto.requestDto.CategoryRequestDto;
import com.system.expenseTracker.dto.responseDto.CategoryResponseDto;
import com.system.expenseTracker.service.CategoryService;
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
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/save-category")
    public String getSaveCategoryInfo(Model model){
        model.addAttribute("category", new CategoryRequestDto());
        return "addCategory";
    }
    @PostMapping("/save-category")
    public String postSaveCategoryInfo(@ModelAttribute("category") CategoryRequestDto category){
        categoryService.addCategory(category);
        return "redirect:/";
    }

    @GetMapping("/find-all-category")
    public String getAllCategory(Model model){
        model.addAttribute("categoryList", categoryService.getAllCategory());
        return "categoryList";
    }
    @GetMapping("/find-all-category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        return "redirect:/find-all-category";
    }
}
