package com.example.foodplanner.ui.meal;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

public interface MealPresenterView {
    void savePlanedMeal(PlanedMeal planedMeal);
    void saveToFavorite(Meal meal);
    void deleteFromFavorite(Meal meal);
    void getIdMeal(String mealId);

}
