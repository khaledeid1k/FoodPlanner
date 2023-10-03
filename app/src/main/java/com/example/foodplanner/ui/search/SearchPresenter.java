package com.example.foodplanner.ui.search;

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
import java.util.List;

public class SearchPresenter implements OnClickListener {
    String selectedChip=Constants.Meal;
    Repository repository;
    ArrayList<FilteredItem> filteredItemArrayList=new ArrayList<>();


    private MutableLiveData<ArrayList<FilteredItem>> filteredItemsMutableLiveData =
            new MutableLiveData<>();

    public LiveData<ArrayList<FilteredItem>> filteredItemsLiveData() {
        return filteredItemsMutableLiveData;
    }

    public SearchPresenter( Repository repository) {
        this.repository = repository;
    }



    void searchOfCategory(String nameOfCategory) {
        repository.filterByCategory(nameOfCategory, new StateOfResponse<>() {
            @Override
            public void succeeded(FilteredItems response) {
                filteredItemsMutableLiveData.setValue(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });

    }

    void searchByCountry(String nameOfCountry) {
        repository.filterByArea(nameOfCountry, new StateOfResponse<>() {
            @Override
            public void succeeded(FilteredItems response) {
                filteredItemsMutableLiveData.setValue(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    void searchByIngredient(String nameOfIngredient) {

        repository.filterByMainIngredient(nameOfIngredient, new StateOfResponse<>() {
            @Override
            public void succeeded(FilteredItems response) {
                filteredItemsMutableLiveData.setValue(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    void searchByMeal(String charOfMeal) {
        repository.getMealsByFirstLetter(charOfMeal, new StateOfResponse<Meals>() {
            @Override
            public void succeeded(Meals response) {
                List<Meal> meals = response.getMeals();
                for (Meal meal : meals) {
                    filteredItemArrayList.add(new FilteredItem
                            (meal.getStrMeal(),
                                    meal.getStrMealThumb(),
                                    meal.getIdMeal()));
                }
                filteredItemsMutableLiveData.setValue(filteredItemArrayList);

            }

            @Override
            public void failure(String message) {

            }
        });
    }


    void getTextOfSelectedChip(String selectedChipText) {
        switch (selectedChipText) {
            case Constants.Category: {
                selectedChip = Constants.Category;
            }
            break;

            case Constants.Country: {
                selectedChip = Constants.Country;
            }
            break;

            case Constants.Ingredients: {
                selectedChip = Constants.Ingredients;
            }
            break;
            case Constants.Meal: {
                selectedChip = Constants.Meal;
            }
            break;
        }

    }

    public void getTextOfSearch(String wordOfSearch) {
        switch (selectedChip) {
            case Constants.Category: {
                searchOfCategory(wordOfSearch);
            }
            break;

            case Constants.Country: {
                searchByCountry(wordOfSearch);
            }
            break;

            case Constants.Ingredients: {
                searchByIngredient(wordOfSearch);
            }
            break;
            case Constants.Meal: {
                searchByMeal(wordOfSearch);
            }
            break;
        }

    }



    void moveToMealScreen(String nameOfMeal,View view) {
        repository.getMealByName(nameOfMeal, new StateOfResponse<>() {
            @Override
            public void succeeded(Meals response) {
                SearchFragmentDirections.ActionSearchToMealFragment action =
                        SearchFragmentDirections.actionSearchToMealFragment(
                      response.getMeals().get(0)  );
                Navigation.findNavController(view).navigate(
                        action
                );
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    @Override
    public void onclick(String nameOfMeal, View view) {

        moveToMealScreen(nameOfMeal,view);


    }
}
