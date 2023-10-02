package com.example.foodplanner.ui.favourite;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;

import java.util.List;

public class FavouritePresenter implements OnClickFavorite {
    Repository repository;
    FavouriteView favouriteView;


    public FavouritePresenter(Repository repository,  FavouriteView favouriteView) {
        this.repository = repository;
        this.favouriteView = favouriteView;
    }
    @Override
    public void onClick(Meal meal, View view) {
        FavouriteFragmentDirections.ActionFavouriteFragmentToMealFragment action =
                FavouriteFragmentDirections.actionFavouriteFragmentToMealFragment(
                        meal);
        Navigation.findNavController(view).navigate(
                action
        );

    }

    @Override
    public void deleteMeal(Meal meal) {
        repository.deleteMeal(meal);
        favouriteView.deleteItem();
    }

    LiveData<List<Meal>> getFavoritesMeals(){
        return repository.getFavoritesMeals();
    }
}
