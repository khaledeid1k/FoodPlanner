package com.example.foodplanner.ui.favourite;

import android.view.View;

import com.example.foodplanner.data.models.meal.Meal;

public interface OnClickFavorites {
    void onClickFavorite(Meal meal, View view);
    void deleteFavorite(Meal meal);
}
