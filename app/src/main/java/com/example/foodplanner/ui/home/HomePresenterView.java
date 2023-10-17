package com.example.foodplanner.ui.home;

import com.example.foodplanner.ui.base.BasePresenterView;

public interface HomePresenterView extends BasePresenterView {
    void getAllCategoriesWithDetails();

    void getRandomMeal();

    void getAllCountries();

    void getMealsByFirstLetter();
}
