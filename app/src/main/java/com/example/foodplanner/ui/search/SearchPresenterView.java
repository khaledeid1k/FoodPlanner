package com.example.foodplanner.ui.search;

import com.example.foodplanner.data.models.meal.Meal;

public interface SearchPresenterView {
    void sendChipValueAndSearchValue(String selectedChipText,String wordOfSearch);
    void getMealByName(String nameOfMeal);


}
