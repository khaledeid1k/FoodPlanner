package com.example.foodplanner.data.local;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public interface LocalSource {

    LiveData<List<Meal>> getFavoritesMeals();
    void saveMeal(Meal meal);
    void deleteMeal(Meal meal);
}
