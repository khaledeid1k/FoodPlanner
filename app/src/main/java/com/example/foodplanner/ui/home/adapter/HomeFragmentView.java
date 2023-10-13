package com.example.foodplanner.ui.home.adapter;

import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public interface HomeFragmentView {
    void getRandomMealLiveData(Meal meal);
    void getMealsByFirstLetter(List<Meal> meals);
    void getCategoriesWithDetails(List<CategoryWithDetails> categoriesWithDetailsList);
    void getAllCountries(List<Country> countries);
}
