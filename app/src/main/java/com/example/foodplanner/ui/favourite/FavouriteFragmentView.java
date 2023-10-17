package com.example.foodplanner.ui.favourite;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public interface FavouriteFragmentView {
    void getFavoritesMeals(List<Meal> meals);
    void showLoading();
    void showError(String errorMessage);
}
