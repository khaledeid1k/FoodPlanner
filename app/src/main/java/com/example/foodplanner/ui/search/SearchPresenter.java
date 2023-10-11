package com.example.foodplanner.ui.search;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.meals.MealsFragmentDirections;
import com.example.foodplanner.ui.meals.OnClickListener;
import com.example.foodplanner.utils.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchPresenter implements OnClickListener, StateOfResponse,SearchPresenterView{
    Repository repository;
    ArrayList<FilteredItem> filtered;



    void moveToMealScreen(String nameOfMeal,View view) {
        repository.getMealByName(nameOfMeal, new StateOfResponse<>() {
            @Override
            public void succeeded(Meals response) {
                moveToMealScreen(response.getMeals().get(0),view);
            }

            @Override
            public void failure(String message) {

            }
        });
    }


    @Override
    public void succeeded(Object response) {
        if (response instanceof  FilteredItems){
            FilteredItems filteredItems = (FilteredItems) response;
            if(filteredItems.getMeals()!=null) {
                filteredItemsMutableLiveData.setValue(
                        filteredItems.getMeals().stream()
                        .distinct()
                        .sorted(Comparator.comparing(FilteredItem::getStrMeal))
                        .collect(Collectors.toCollection(ArrayList::new)));
            }else {
                filteredItemsMutableLiveData.setValue(new ArrayList<>());}

        }else if(response instanceof  Meals){
            Meals meals = (Meals) response;
            filtered= repository.searchByMeal(meals);
            filteredItemsMutableLiveData.setValue(filtered.stream()
                    .distinct()
                    .sorted(Comparator.comparing(FilteredItem::getStrMeal))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
    }

    @Override
    public void failure(String message) {

    }

    private MutableLiveData<ArrayList<FilteredItem>> filteredItemsMutableLiveData =
            new MutableLiveData<>();

    public LiveData<ArrayList<FilteredItem>> filteredItemsLiveData() {
        return filteredItemsMutableLiveData;
    }
    public SearchPresenter( Repository repository) {
        this.repository = repository;
    }
    void searchOfCategory(String nameOfCategory) {
        repository.filterByCategory(nameOfCategory,this);
    }
    void searchByCountry(String nameOfCountry) {
        repository.filterByArea(nameOfCountry, this);
    }
    void searchByIngredient(String nameOfIngredient) {
        repository.filterByMainIngredient(nameOfIngredient,this);
    }
    void searchByMeal(String charOfMeal) {
        if (charOfMeal.length()==1) {
            repository.getMealsByFirstLetter(charOfMeal, this);
        }else {
            filteredItemsMutableLiveData.setValue(repository.searchInMeals(filtered,charOfMeal));
        }

    }
    void moveToMealScreen(Meal meal,View view){
        SearchFragmentDirections.ActionSearchToMealFragment action =
                SearchFragmentDirections.actionSearchToMealFragment(
                        meal );
        Navigation.findNavController(view).navigate(
                action    );
    }


    @Override
    public void onclickMeal(String nameOfMeal, View view) {
        moveToMealScreen(nameOfMeal,view);
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
            default:{
                searchByMeal(wordOfSearch);
            }
        }
        if(Objects.equals(wordOfSearch, Constants.Empty)){
            filteredItemsMutableLiveData.setValue(new ArrayList<>());
        }
    }
}
