package com.example.foodplanner.ui.plan.details;

import android.view.View;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

public interface PlannedMealPresenterView {
    void deletePlanedMeal(PlanedMeal plannedMeal);
    void getPlanedMeal(String day, String timeOfMeal);

}
