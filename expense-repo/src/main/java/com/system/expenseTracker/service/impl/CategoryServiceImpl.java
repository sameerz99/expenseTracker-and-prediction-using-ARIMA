package com.system.expenseTracker.service.impl;

import com.system.expenseTracker.dto.requestDto.CategoryRequestDto;
import com.system.expenseTracker.dto.responseDto.CategoryResponseDto;
import com.system.expenseTracker.model.Category;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.CategoryRepo;
import com.system.expenseTracker.service.CategoryService;
import com.system.expenseTracker.service.other.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final UserLogService userLogService;
    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, UserLogService userLogService) {
        this.categoryRepo = categoryRepo;
        this.userLogService = userLogService;
    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        User loggedInUser = userLogService.getLoggedInUser();
        Category category = new Category();
        category.setCategoryName(categoryRequestDto.getCategoryName());
        category.setUser(loggedInUser);
        Category savedCategory = categoryRepo.save(category);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(savedCategory);
        return categoryResponseDto;
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        User loggedInUser = userLogService.getLoggedInUser();
        List<CategoryResponseDto> returnList = new ArrayList<>();
        List<Category> categoryList = categoryRepo.findByUser(loggedInUser);
        for(Category category: categoryList){
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto(category);
            returnList.add(categoryResponseDto);
        }
        return returnList;
    }

    @Override
    public CategoryResponseDto getCategoryById(Integer id) {
        User loggedInUser = userLogService.getLoggedInUser();
        Category foundCategory = categoryRepo.findByIdAndUser(id,loggedInUser).get();
        return new CategoryResponseDto(foundCategory);
    }
}
