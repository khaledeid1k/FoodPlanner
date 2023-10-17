package com.example.foodplanner.ui.search;

import android.util.Log;

import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;
import com.example.foodplanner.utils.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchPresenter extends BasePresenter implements SearchPresenterView {
    Repository repository;
    ArrayList<FilteredItem> filtered;
    SearchFragmentView searchFragmentView;

    public SearchPresenter(Repository repository, SearchFragmentView searchFragmentView) {
        this.repository = repository;
        this.searchFragmentView = searchFragmentView;
    }

    void moveToMealScreen(String nameOfMeal) {
        applySchedulersAndPostUIStates(repository.getMealByName(nameOfMeal), this);
    }

    void searchByMeal(String charOfMeal) {
        if (charOfMeal.length() == 1) {
            applySchedulersAndPostUIStates(repository.getMealsByFirstLetter(charOfMeal), this);
        } else {
            if (filtered != null) {
                searchFragmentView.getFilterData(repository.searchInMeals(filtered, charOfMeal));
            }
        }

    }


    void searchOfCategory(String nameOfCategory) {
        applySchedulersAndPostUIStates(repository.filterByCategory(nameOfCategory), this);
    }

    void searchByCountry(String nameOfCountry) {
        applySchedulersAndPostUIStates(repository.filterByArea(nameOfCountry), this);
    }

    void searchByIngredient(String nameOfIngredient) {
        applySchedulersAndPostUIStates(repository.filterByMainIngredient(nameOfIngredient), this);
    }

    @Override
    public void sendChipValueAndSearchValue(String selectedChipText, String wordOfSearch) {
        switch (selectedChipText) {
            case Constants.Category: {
                searchOfCategory(wordOfSearch);
            }
            break;
            case Constants.Country: {
                searchByCountry(wordOfSearch);
            }
            break;
            case Constants.Ingredient: {
                searchByIngredient(wordOfSearch);
            }
            break;
            default: {
                searchByMeal(wordOfSearch);
            }
        }
        if (Objects.equals(wordOfSearch, Constants.Empty)) {
            searchFragmentView.getFilterData(new ArrayList<>());
        }
    }

    @Override
    public void getMealByName(String nameOfMeal) {
        moveToMealScreen(nameOfMeal);
    }

    @Override
    public void showLoading() {
        searchFragmentView.showLoading();
    }

    @Override
    public void showData(Object data) {
        Log.i("WrapResponse", "showData: " + data.toString());
        if (data instanceof FilteredItems) {
            FilteredItems filteredItems = (FilteredItems) data;
            if (filteredItems.getMeals() != null) {
                searchFragmentView.getFilterData(filteredItems.getMeals().stream().distinct().sorted(Comparator.comparing(FilteredItem::getStrMeal)).collect(Collectors.toCollection(ArrayList::new)));
            } else {
                searchFragmentView.getFilterData((new ArrayList<>()));
            }

        } else if (data instanceof Meals) {
            Meals meals = (Meals) data;
            filtered = repository.searchByMeal(meals);
            searchFragmentView.getFilterData(filtered);
        } else if (data instanceof Meal) {
            Meal meal = (Meal) data;
            searchFragmentView.navigateToMeal(meal);
        }

    }

    @Override
    public void showError(String errorMessage) {
        searchFragmentView.showError(errorMessage);

    }
}
