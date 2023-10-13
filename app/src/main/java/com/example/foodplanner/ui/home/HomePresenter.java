package com.example.foodplanner.ui.home;

import static com.example.foodplanner.utils.Extensions.getRandomChar;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.network.auth.ILogOut;
import com.example.foodplanner.data.network.auth.Logout;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.home.adapter.CategoriesItem;
import com.example.foodplanner.ui.home.adapter.CountriesItem;
import com.example.foodplanner.ui.home.adapter.HeaderItem;
import com.example.foodplanner.ui.home.adapter.HomeFragmentView;
import com.example.foodplanner.ui.home.adapter.MealsItem;
import com.example.foodplanner.ui.home.adapter.HomeInteractionListener;
import com.example.foodplanner.ui.home.adapter.OnClickHomeHorizontalItem;
import com.example.foodplanner.utils.Constants;

import java.util.List;

public class HomePresenter  {
    Repository repository;
    HomeFragmentView homeFragmentView;
    HomePresenter(Repository repository,  HomeFragmentView homeFragmentView) {
        this.repository = repository;
        this.homeFragmentView = homeFragmentView;
        getAllCategoriesWithDetails();
        getRandomMeal();
        getAllCountries();
        getMealsByFirstLetter();

    }




    private void getRandomMeal() {
        repository.getRandomMeal(new StateOfResponse<>() {
            @Override
            public void succeeded(Meals response) {
                homeFragmentView.getRandomMealLiveData(response.getMeals().get(0));
            }

            @Override
            public void failure(String message) {

            }
        });

    }

    private void getMealsByFirstLetter() {

        repository.getMealsByFirstLetter(getRandomChar(), new StateOfResponse<Meals>() {
            @Override
            public void succeeded(Meals response) {
                if(response.getMeals()!=null&&response.getMeals().size()>4) {
                    homeFragmentView.getMealsByFirstLetter(response.getMeals());
                }else {
                    getMealsByFirstLetter();
                }
                }

            @Override
            public void failure(String message) {

            }
        });
    }

    private void getAllCategoriesWithDetails() {
        repository.getAllCategoriesWithDetails(new StateOfResponse<>() {
            @Override
            public void succeeded(CategoriesWithDetails response) {
                homeFragmentView.getCategoriesWithDetails(response.getCategories());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    private void getAllCountries() {
        repository.getAllCountries(new StateOfResponse<>() {
            @Override
            public void succeeded(Countries response) {
                homeFragmentView.getAllCountries(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }




}
