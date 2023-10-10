package com.example.foodplanner.ui.meal;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealPresenter implements MealPresenterView {

Repository repository;
    MealView mealView;
    Meal meal;

    public MealPresenter(Meal meal,
                         Repository repository,MealView mealView) {
        this.meal = meal;
        this.repository=repository;
        this.mealView=mealView;
        getIngredientAndMeasure();
        addInstructions();
    }

    void getIngredientAndMeasure() {
        mealView.getIngredientAndMeasure(repository.getIngredientAndMeasure(meal));
    }
    void addInstructions() {
        mealView.getInstructions(repository.getInstructions(meal));
    }




    LiveData<Boolean> mealIsFavorite(String mealId){
       return repository.getFavoriteMealById(mealId);
    }

    @Override
    public void savePlanedMeal(PlanedMeal planedMeal) {
        repository.savePlanedMeal(planedMeal);
    }

    @Override
    public void saveToFavorite(Meal meal) {
        repository.saveMeal(meal);
    }

    @Override
    public void deleteFromFavorite(Meal meal) {
        repository.deleteMeal(meal);
    }


}
