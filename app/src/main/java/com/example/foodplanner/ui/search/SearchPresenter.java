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
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchPresenter implements OnClickListener {
    Repository repository;
    ArrayList<FilteredItem> filteredItemArrayList=new ArrayList<>();
    ArrayList<FilteredItem> filtered=new ArrayList<>();


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
        if (charOfMeal.length()==1) {
            repository.getMealsByFirstLetter(charOfMeal, new StateOfResponse<>() {
                @Override
                public void succeeded(Meals response) {
                    List<Meal> meals = response != null ? response.getMeals() : null;
                    if (meals != null) {
                        ArrayList<FilteredItem> filtered = new ArrayList<>();
                        for (Meal meal : meals) {
                            filtered.add(new FilteredItem(
                                    meal.getStrMeal(),
                                    meal.getStrMealThumb(),
                                    meal.getIdMeal()
                            ));
                        }
                        filteredItemsMutableLiveData.setValue(filtered);
                    }
                }

                @Override
                public void failure(String message) {

                }
            });
        }else {
            filtered= (ArrayList<FilteredItem>) filtered.stream()

                 .filter(s -> s.getStrMeal().toLowerCase(Locale.ROOT)
                         .contains(charOfMeal))
                    .distinct().collect(Collectors.toList());
            filteredItemsMutableLiveData.setValue(filtered);

           }

    }


    void getTextOfSelectedChip(String selectedChipText,String wordOfSearch) {
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
            case Constants.Meal: {
                searchByMeal(wordOfSearch);
            }
            break;


        }
        if(Objects.equals(wordOfSearch, Constants.Empty)){
            filteredItemArrayList.clear();
            filteredItemsMutableLiveData.setValue(filteredItemArrayList);
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
