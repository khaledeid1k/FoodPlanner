package com.example.foodplanner.ui.meals;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.home.adapter.OnClickItem;

public class MealsPresenter implements OnClickItem {
    Repository repository;

    public MealsPresenter(Repository repository,String nameOfItem) {
        this.repository = repository;
        if(nameOfItem.split(",")[1].equals("ca")){
            Log.i("TAG", "getMealsCategories: " +nameOfItem.split(",")[0]);
            getMealsCategories(nameOfItem.split(",")[0]);
        }else {
            getMealsCountries(nameOfItem.split(",")[0]);
            Log.i("TAG", "getMealsCountries: " +nameOfItem.split(",")[0]);

        }

    }

    private MutableLiveData<FilteredItems> filteredItemsMutableLiveData =
            new MutableLiveData<>();


    public LiveData<FilteredItems> filteredItemsLiveData() {
        return filteredItemsMutableLiveData;
    }


    void getMealsCategories(String categoryName){
        repository.filterByCategory(categoryName, new StateOfResponse<>() {
            @Override
            public void succeeded(FilteredItems response) {
                filteredItemsMutableLiveData.setValue(response);
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
                filteredItemsMutableLiveData.setValue(response);

            }

            @Override
            public void failure(String message) {

            }
        });
    }

    @Override
    public void click(DataItem dataItem, int position, View view) {

    }
}
