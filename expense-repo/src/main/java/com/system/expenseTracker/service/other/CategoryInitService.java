package com.system.expenseTracker.service.other;

import com.system.expenseTracker.model.Category;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.CategoryRepo;
import com.system.expenseTracker.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryInitService {
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    @Autowired
    public CategoryInitService(UserRepo userRepo, CategoryRepo categoryRepo) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }
    @Transactional
    public void associateDefaultCategoriesWithNewUser(User newUser){
        User globalUser = userRepo.getUserByUsername("globalUser");
        List<Category> defaultCategories = categoryRepo.findByUser(globalUser);
        for (Category defaultCategory : defaultCategories) {
            Category userCategory = new Category();
            userCategory.setCategoryName(defaultCategory.getCategoryName());
            userCategory.setUser(newUser);
            categoryRepo.save(userCategory);
        }
    }

}
