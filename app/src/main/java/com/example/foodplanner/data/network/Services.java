package com.example.foodplanner.data.network;

import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("search.php")
    Call<Meals> getMealByName(@Query("s") String nameOfMeal );

    @GET("search.php")
    Call<Meals> getMealsByFirstLetter(@Query("f") String firstLetterOfMeal);
    @GET("lookup.php")
    Call<Meals> getMealDetailsById(@Query("i") String idOfMeal);
    @GET("random.php")
    Call<Meals> getRandomMeal();

    @GET("categories.php")
    Call<CategoriesWithDetails> getAllCategoriesWithDetails();

    @GET("list.php?c=list")
    Call<Categories> getAllCategories();
    @GET("list.php?a=list")
    Call<Countries> getAllCountries();
    @GET("list.php?i=list")
    Call<Ingredients> getAllIngredients();

    @GET("filter.php")
    Call<FilteredItems> filterByMainIngredient(@Query("i") String nameOfMainIngredient);
    @GET("filter.php")
    Call<FilteredItems> filterByCategory(@Query("c") String nameOfCategory);
    @GET("filter.php")
    Call<FilteredItems> filterByArea(@Query("a") String nameOfArea);

}
