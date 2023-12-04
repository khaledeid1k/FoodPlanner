package com.example.foodplanner.ui.meal;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;
import com.example.foodplanner.ui.base.BasePresenterView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenter extends BasePresenter implements MealPresenterView
         {

    Repository repository;
    MealView mealView;
    Meal meal;

    public MealPresenter(Meal meal,
                         Repository repository, MealView mealView) {
        this.meal = meal;
        this.repository = repository;
        this.mealView = mealView;
        getIngredientAndMeasure();
        addInstructions();
    }

    void getIngredientAndMeasure() {
        mealView.getIngredientAndMeasure(repository.getIngredientAndMeasure(meal));
    }

    void addInstructions() {
        mealView.getInstructions(repository.getInstructions(meal));
    }

    @Override
    public void savePlanedMeal(PlanedMeal planedMeal) {
        repository.savePlanedMeal(planedMeal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{},
                        error->{}
                );
    }

    @Override
    public void saveToFavorite(Meal meal) {
        repository.saveMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{},
                        error->{}
                );
    }

    @Override
    public void deleteFromFavorite(Meal meal) {
        repository.deleteMeal(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{},
                        error->{}
                );
    }

    @Override
    public void getIdMeal(String mealId) {
    repository.getFavoriteMealById(mealId).subscribeOn(
               Schedulers.io()
       ).observeOn(AndroidSchedulers.mainThread()).subscribe(
            isFavorite-> mealView.getIsFavoriteMealById(isFavorite),
            error->{}
       );
    }


}
