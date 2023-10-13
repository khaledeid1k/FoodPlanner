package com.example.foodplanner.ui.search;

import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;

public interface SearchFragmentView {
    void  getFilterData(ArrayList<FilteredItem> filteredItems);
    void  navigateToMeal(Meal meal);
}
