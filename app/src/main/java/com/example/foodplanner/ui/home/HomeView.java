package com.example.foodplanner.ui.home;

import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public interface HomeView {
    void getRandomMeal(Meal meal);

    void getMealsByFirstLetter(List<Meal> meals);

    void getAllCategoriesWithDetails(List<CategoryWithDetails> categoryWithDetailsList);

    void getAllCountries(List<Country> countryList);

}
