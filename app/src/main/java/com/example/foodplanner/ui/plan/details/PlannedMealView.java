package com.example.foodplanner.ui.plan.details;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

public interface PlannedMealView {
    void getPlanedMeal(PlanedMeal planedMeal);
    void showLoading();
    void showError(String errorMessage);
}
