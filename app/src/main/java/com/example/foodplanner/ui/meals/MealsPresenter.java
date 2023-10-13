package com.example.foodplanner.ui.meals;

import android.util.Log;

import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;

public class MealsPresenter implements MealsPresenterView {
    Repository repository;
    MealsFragmentView mealsFragmentView;

    public MealsPresenter(Repository repository,String nameOfItem,
                          MealsFragmentView mealsFragmentView) {
        this.repository = repository;
        this.mealsFragmentView = mealsFragmentView;
        if(nameOfItem.split(",")[1].equals("ca")){
            getMealsCategories(nameOfItem.split(",")[0]);
        }else {
            getMealsCountries(nameOfItem.split(",")[0]);

        }

    }


    void getMealsCategories(String categoryName){
        repository.filterByCategory(categoryName, new StateOfResponse<>() {
            @Override
            public void succeeded(FilteredItems response) {
                mealsFragmentView.getMeals(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }
    void getMealsCountries(String countryName){
        repository.filterByArea(countryName, new StateOfResponse<>() {
            @Override
            public void succeeded(FilteredItems response) {
                Log.i("TAG", "succeeded: "+response.getMeals().get(0));
                mealsFragmentView.getMeals(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }



    public void onclickMeal(String nameOfMeal) {
        repository.getMealByName(nameOfMeal, new StateOfResponse<>() {
            @Override
            public void succeeded(Meals response) {
                mealsFragmentView.getMealByName(response.getMeals().get(0));
            }

            @Override
            public void failure(String message) {

            }
        });
    }
}

