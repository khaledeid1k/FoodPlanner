package com.example.foodplanner.ui.meals;

import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;

public interface MealsFragmentView {
    void getMealByName(Meal meal);
    void getMeals(ArrayList<FilteredItem> filteredItems);
    void showLoading();
    void showError(String errorMessage);
}
