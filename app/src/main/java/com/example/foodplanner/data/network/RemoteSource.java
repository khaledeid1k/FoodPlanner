package com.example.foodplanner.data.network;

import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;

import retrofit2.Call;

public interface RemoteSource {
    Call<Meals> getMealByName(String nameOfMeal);

    Call<Meals> getMealsByFirstLetter(String firstLetterOfMeal);

    Call<Meals> getMealDetailsById(String idOfMeal);

    Call<Meals> getRandomMeal();

    Call<CategoriesWithDetails> getAllCategoriesWithDetails();

    Call<Categories> getAllCategories();

    Call<Countries> getAllCountries();

    Call<Ingredients> getAllIngredients();

    Call<FilteredItems> filterByMainIngredient(String nameOfMainIngredient);

    Call<FilteredItems> filterByCategory(String nameOfCategory);

    Call<FilteredItems> filterByArea(String nameOfArea);

}
