package com.example.foodplanner.ui.favourite;

import static com.example.foodplanner.utils.Constants.UserId;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter extends BasePresenter implements FavoritePresenterView {
    Repository repository;
    FavouriteFragmentView favouriteFragmentView;

    public FavouritePresenter(Repository repository
            , FavouriteFragmentView favouriteFragmentView) {
        this.repository = repository;
        this.favouriteFragmentView = favouriteFragmentView;

    }


    @Override
    public void deleteFavorite(Meal meal) {
        repository.deleteMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {},
                e -> favouriteFragmentView.showError(e.getMessage())
        );
    }

    @Override
    public void getFavoritesMeals() {
        repository.getFavoritesMeals(UserId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                meals -> {
                    favouriteFragmentView.getFavoritesMeals(meals);
                },
                error -> {
                    favouriteFragmentView.showError(error.getMessage());
                }

        );


    }


}

