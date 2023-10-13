package com.example.foodplanner.ui.favourite;

import static com.example.foodplanner.utils.Constants.UserId;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;

import java.util.List;

public class FavouritePresenter implements FavoritePresenterView {
    Repository repository;
    FavouriteFragmentView favouriteFragmentView;
    public FavouritePresenter(Repository repository
            ,  FavouriteFragmentView favouriteFragmentView) {
        this.repository = repository;
        this.favouriteFragmentView = favouriteFragmentView;
        getFavoritesMeals();
    }



    @Override
    public void deleteFavorite(Meal meal) {
        repository.deleteMeal(meal);
    }
   void getFavoritesMeals(){
        favouriteFragmentView.getFavoritesMeals(repository.getFavoritesMeals(UserId));

    }
}
