package com.example.foodplanner.ui.favourite;

import android.view.View;

import com.example.foodplanner.data.models.meal.Meal;

public interface OnClickFavorite {
    void onClick(Meal meal, View view);
    void deleteMeal(Meal meal);
}
