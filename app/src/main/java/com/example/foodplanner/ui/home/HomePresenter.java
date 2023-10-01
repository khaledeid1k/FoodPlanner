package com.example.foodplanner.ui.home;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.ui.home.adapter.MealsItem;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.home.adapter.NavigationToShowAll;
import com.example.foodplanner.ui.home.adapter.OnClickItem;

import java.util.List;

public class HomePresenter implements NavigationToShowAll,OnClickItem{
    Repository repository;
    String TAG = "TAG HomePresenter";
    private  MutableLiveData<Meal> randomMealLiveData = new MutableLiveData<>();
    private  MutableLiveData<List<Meal>> mealsByFirstLetter = new MutableLiveData<>();
    private  MutableLiveData<List<CategoryWithDetails>> allCategoriesWithDetails = new MutableLiveData<>();
    private  MutableLiveData<List<Country>> allCountries = new MutableLiveData<>();

    public LiveData<Meal> randomMealLiveData() {
        return randomMealLiveData;
    }
    public LiveData<List<Meal>> mealsByFirstLetter() {
        return mealsByFirstLetter;
    }
    public LiveData<List<CategoryWithDetails>> categoriesWithDetails() {
        return allCategoriesWithDetails;
    }
    public LiveData<List<Country>> allCountries() {
        return allCountries;
    }
    HomePresenter( Repository repository,String firstLetter) {
        this.repository= repository;
        getAllCategoriesWithDetails();

        getRandomMeal();
        getAllCountries();
        getMealsByFirstLetter(firstLetter);
    }

    private  void getRandomMeal() {
        repository.getRandomMeal(new StateOfResponse<>() {
            @Override
            public void succeeded(Meals response) {
                randomMealLiveData.setValue(response.getMeals().get(0));
            }

            @Override
            public void failure(String message) {

            }
        });

    }

    private void getMealsByFirstLetter(String firstLetter) {
        repository.getMealsByFirstLetter(firstLetter, new StateOfResponse<Meals>() {
            @Override
            public void succeeded(Meals response) {
                mealsByFirstLetter.setValue(response.getMeals());
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
                allCategoriesWithDetails.setValue(response.getCategories());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

  private   void getAllCountries() {
        repository.getAllCountries(new StateOfResponse<>() {
            @Override
            public void succeeded(Countries response) {
                allCountries.setValue(response.getMeals());
            }

            @Override
            public void failure(String message) {

            }
        });
    }

    @Override
    public void onNavigate(DataItem dataItem, View view) {
        if(dataItem instanceof MealsItem){
            HomeFragmentDirections.ActionHomeFragmentToShowAllFragment action=
                    HomeFragmentDirections.actionHomeFragmentToShowAllFragment(dataItem);
            Navigation.findNavController(view).navigate(
                    action
            );
        }
    }

    @Override
    public void click(DataItem dataItem,int position) {
        Log.i(TAG, "click: "+((MealsItem)dataItem).getTag().getResourcesData()
                .get(position).getStrArea());

    }
}
