package com.example.foodplanner.data.local;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface LocalSource {

    Observable<List<Meal>> getFavoritesMeals(String userId);
    Completable saveMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Single<Boolean> getFavoriteMealById(String mealId);

    Flowable<PlanedMeal> getPlanedMeal(String day, String timeOfMeal, String userId);
    Completable deletePlanedMeal(PlanedMeal planedMeal);
    Completable savePlanedMeal(PlanedMeal planedMeal);

    ArrayList<IngredientMeasurePair> getIngredientAndMeasure(Meal meal);
    String getInstructions(Meal meal);

}
