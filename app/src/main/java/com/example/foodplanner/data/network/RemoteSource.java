package com.example.foodplanner.data.network;

import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;

public interface RemoteSource {
    Single<Response<Meals>> getMealByName(String nameOfMeal);

    Single<Response<Meals>>getMealsByFirstLetter(String firstLetterOfMeal);

    Single<Response<Meals>>getMealDetailsById(String idOfMeal);

    Single<Response<Meals>>getRandomMeal();

    Single<Response<CategoriesWithDetails>>getAllCategoriesWithDetails();

    Single<Response<Categories>>getAllCategories();

    Single<Response<Countries>>getAllCountries();

    Single<Response<Ingredients>>getAllIngredients();

    Single<Response<FilteredItems>>filterByMainIngredient(String nameOfMainIngredient);

    Single<Response<FilteredItems>>filterByCategory(String nameOfCategory);

    Single<Response<FilteredItems>> filterByArea(String nameOfArea);

}
