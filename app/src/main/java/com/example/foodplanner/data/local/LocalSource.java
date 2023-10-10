package com.example.foodplanner.data.local;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public interface LocalSource {

    LiveData<List<Meal>> getFavoritesMeals(String userId);
    void saveMeal(Meal meal);
    void deleteMeal(Meal meal);
    LiveData<Boolean> getFavoriteMealById(String mealId);

    LiveData<List<PlanedMeal>> getPlanedMeals(String day, String timeOfMeal, String userId);
    void deletePlanedMeal(PlanedMeal planedMeal);
    void savePlanedMeal(PlanedMeal planedMeal);
    ArrayList<IngredientMeasurePair> getIngredientAndMeasure(Meal meal);
    String getInstructions(Meal meal);

}
