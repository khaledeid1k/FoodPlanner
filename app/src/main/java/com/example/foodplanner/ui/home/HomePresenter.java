package com.example.foodplanner.ui.home;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.data.repository.RepositoryIm;

import java.util.List;

public class HomePresenter {
    HomeView homeView;
    Repository repository;

    HomePresenter(HomeView homeView, Repository repository) {
        this.homeView = homeView;
        this.repository= repository;
    }

    void getRandomMeal() {
        repository.getRandomMeal(new StateOfResponse<Meals>() {
            @Override
            public void succeeded(Meals response) {
                homeView.getRandomMeal(response.getMeals().get(0));
            }

            @Override
            public void failure(String message) {

            }
        });

    }

    void getMealsByFirstLetter(String firstLetter) {
        repository.getMealsByFirstLetter(firstLetter, new StateOfResponse<Meals>() {
            @Override
            public void succeeded(Meals response) {
                homeView.getMealsByFirstLetter(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    void getAllCategoriesWithDetails() {
        repository.getAllCategoriesWithDetails(new StateOfResponse<CategoriesWithDetails>() {
            @Override
            public void succeeded(CategoriesWithDetails response) {
                homeView.getAllCategoriesWithDetails(response.getCategories());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    void getAllCountries() {
        repository.getAllCountries(new StateOfResponse<Countries>() {
            @Override
            public void succeeded(Countries response) {
                homeView.getAllCountries(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

}
