package com.example.foodplanner.ui.meals;

import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;
import com.example.foodplanner.ui.base.BasePresenterView;

public class MealsPresenter extends BasePresenter implements MealsPresenterView,
        BasePresenterView {
    Repository repository;
    MealsFragmentView mealsFragmentView;

    public MealsPresenter(Repository repository,
                          MealsFragmentView mealsFragmentView) {
        this.repository = repository;
        this.mealsFragmentView = mealsFragmentView;

    }



     void getMealsCategories(String categoryName){
        applySchedulersAndPostUIStates( repository.filterByCategory(categoryName),this);

    }
     void getMealsCountries(String countryName){
        applySchedulersAndPostUIStates(  repository.filterByArea(countryName),this);
    }
    @Override
    public void onclickMeal(String nameOfMeal) {
        applySchedulersAndPostUIStates(    repository.getMealByName(nameOfMeal),this);
    }

    @Override
    public void getMeals(String nameOfItem) {
        if(nameOfItem.split(",")[1].equals("ca")){
            getMealsCategories(nameOfItem.split(",")[0]);
        }else {
            getMealsCountries(nameOfItem.split(",")[0]);
        }
    }


    @Override
    public void showLoading() {
        mealsFragmentView.showLoading();

    }

    @Override
    public void showData(Object data) {
        if(data instanceof FilteredItems){
            FilteredItems filteredItems = (FilteredItems) data;
            mealsFragmentView.getMeals(filteredItems.getMeals());
        }
        else if(data instanceof Meal){
            Meal meal = (Meal) data;
            mealsFragmentView.getMealByName(meal);
        }




    }

    @Override
    public void showError(String errorMessage) {
        mealsFragmentView.showError(errorMessage);
    }
}

