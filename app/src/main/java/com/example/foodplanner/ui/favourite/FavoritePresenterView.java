package com.example.foodplanner.ui.favourite;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.ui.base.BasePresenterView;

public interface FavoritePresenterView  {
    void deleteFavorite(Meal meal);
    void  getFavoritesMeals();
}
