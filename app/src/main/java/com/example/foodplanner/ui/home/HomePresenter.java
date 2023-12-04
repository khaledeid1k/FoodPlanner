package com.example.foodplanner.ui.home;

import static com.example.foodplanner.utils.Extensions.getRandomChar;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;

public class HomePresenter extends BasePresenter implements HomePresenterView {
    Repository repository;
    HomeFragmentView homeFragmentView;
    HomePresenter(Repository repository,  HomeFragmentView homeFragmentView) {
        this.repository = repository;
        this.homeFragmentView = homeFragmentView;

    }



    @Override
    public void getRandomMeal() {
        applySchedulersAndPostUIStates(   repository.getRandomMeal(),this);
    }
    @Override
    public void getMealsByFirstLetter() {
        applySchedulersAndPostUIStates(
        repository.getMealsByFirstLetter(getRandomChar()),this);
    }
    @Override
    public void getAllCategoriesWithDetails() {
        applySchedulersAndPostUIStates( repository.getAllCategoriesWithDetails(),this);
    }

    @Override
    public void getAllCountries() {
        applySchedulersAndPostUIStates( repository.getAllCountries(),this);
    }


    @Override
    public void showLoading() {
        homeFragmentView.showLoading();
    }

    @Override
    public void showData(Object data) {
        if(data instanceof Meal){
            homeFragmentView.getRandomMeal((Meal) data);
        }else if(data instanceof Countries){
            Countries countries = (Countries) data;
            homeFragmentView.getAllCountries(countries.getMeals());
        }
        else if(data instanceof CategoriesWithDetails){
            CategoriesWithDetails categoriesWithDetails = (CategoriesWithDetails) data;
            homeFragmentView.getCategoriesWithDetails(categoriesWithDetails.getCategories());
        }
        else if(data instanceof Meals){
            Meals meals = (Meals) data;
            if(meals.getMeals()!=null&&meals.getMeals().size()>4) {
                homeFragmentView.getMealsByFirstLetter(meals.getMeals());
            }else {
                getMealsByFirstLetter();
            }

        }






    }

    @Override
    public void showError(String errorMessage) {
        homeFragmentView.showError(errorMessage);
    }
}
