package com.system.expenseTracker.dto.responseDto;

import com.system.expenseTracker.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private Integer categoryId;
    private String categoryName;

    public CategoryResponseDto(Category category){
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}
