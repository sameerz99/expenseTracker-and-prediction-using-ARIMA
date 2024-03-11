package com.system.expenseTracker.service;

import com.system.expenseTracker.dto.requestDto.CategoryRequestDto;
import com.system.expenseTracker.dto.responseDto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);
    void deleteCategoryById(Integer id);
    List<CategoryResponseDto> getAllCategory();
    CategoryResponseDto getCategoryById(Integer id);

}
