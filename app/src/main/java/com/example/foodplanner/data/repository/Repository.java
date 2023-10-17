package com.example.foodplanner.data.repository;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.repository.state.StateOfResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface Repository {
    //region remote
    Single<StateOfResponse<Meal>> getMealByName(String nameOfMeal);

    Single<StateOfResponse<Meals>> getMealsByFirstLetter(String firstLetterOfMeal);

    Single<StateOfResponse<Meals>> getMealDetailsById(String idOfMeal);

    Single<StateOfResponse<Meal>> getRandomMeal();

    Single<StateOfResponse<CategoriesWithDetails>> getAllCategoriesWithDetails();

    Single<StateOfResponse<Categories>> getAllCategories();

    Single<StateOfResponse<Countries>> getAllCountries();

    Single<StateOfResponse<Ingredients>> getAllIngredients();

    Single<StateOfResponse<FilteredItems>> filterByMainIngredient(String nameOfMainIngredient);

    Single<StateOfResponse<FilteredItems>> filterByCategory(String nameOfCategory);

    Single<StateOfResponse<FilteredItems>> filterByArea(String nameOfArea);

    // endregion

    //region local

    Single<List<Meal>> getFavoritesMeals(String userId);

    Completable saveMeal(Meal meal);

    Completable deleteMeal(Meal meal);

    Single<Boolean> getFavoriteMealById(String mealId);


    Flowable<PlanedMeal> getPlanedMeals(String day, String timeOfMeal, String userId);

    Completable deletePlanedMeal(PlanedMeal planedMeal);

    Completable savePlanedMeal(PlanedMeal planedMeal);

    ArrayList<IngredientMeasurePair> getIngredientAndMeasure(Meal meal);

    ArrayList<Instructions> getInstructions(Meal meal);

    ArrayList<FilteredItem> searchByMeal(Meals meals);

    ArrayList<FilteredItem> searchInMeals(ArrayList<FilteredItem> meals, String charOfMeal);

    // endregion
}
