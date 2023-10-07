package com.example.foodplanner.data.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.StateOfResponse;

import java.util.List;

public interface Repository {

    StateOfResponse<Meals> getMealByName(String nameOfMeal, StateOfResponse<Meals> stateOfResponse);

    StateOfResponse<Meals> getMealsByFirstLetter(String firstLetterOfMeal, StateOfResponse<Meals> stateOfResponse);

    StateOfResponse<Meals> getMealDetailsById(String idOfMeal, StateOfResponse<Meals> stateOfResponse);

    StateOfResponse<Meals> getRandomMeal(StateOfResponse<Meals> stateOfResponse);

    StateOfResponse<CategoriesWithDetails> getAllCategoriesWithDetails(StateOfResponse<CategoriesWithDetails> stateOfResponse);

    StateOfResponse<Categories> getAllCategories(StateOfResponse<Categories> stateOfResponse);

    StateOfResponse<Countries> getAllCountries(StateOfResponse<Countries> stateOfResponse);

    StateOfResponse<Ingredients> getAllIngredients(StateOfResponse<Ingredients> stateOfResponse);

    StateOfResponse<FilteredItems> filterByMainIngredient(String nameOfMainIngredient, StateOfResponse<FilteredItems> stateOfResponse);

    StateOfResponse<FilteredItems> filterByCategory(String nameOfCategory, StateOfResponse<FilteredItems> stateOfResponse);

    StateOfResponse<FilteredItems> filterByArea(String nameOfArea, StateOfResponse<FilteredItems> stateOfResponse);

    LiveData<List<Meal>> getFavoritesMeals();

    void saveMeal(Meal meal);

    void deleteMeal(Meal meal);
    LiveData<Boolean> getFavoriteMealById(String mealId);


    LiveData<List<PlanedMeal>> getPlanedMeals(String day, String timeOfMeal, String userId);
    void deletePlanedMeal(PlanedMeal planedMeal);
    void savePlanedMeal(PlanedMeal planedMeal);
}
