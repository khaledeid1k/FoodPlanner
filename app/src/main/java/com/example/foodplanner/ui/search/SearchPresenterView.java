package com.example.foodplanner.ui.search;

import com.example.foodplanner.ui.base.BasePresenterView;

public interface SearchPresenterView extends BasePresenterView{
    void sendChipValueAndSearchValue(String selectedChipText,String wordOfSearch);
    void getMealByName(String nameOfMeal);


}
