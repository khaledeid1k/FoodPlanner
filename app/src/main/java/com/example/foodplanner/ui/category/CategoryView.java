package com.example.foodplanner.ui.category;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;

import java.util.List;

public interface CategoryView {
    void getAllCategoriesWithDetails(List<CategoryWithDetails> categoriesWithDetails);
}
