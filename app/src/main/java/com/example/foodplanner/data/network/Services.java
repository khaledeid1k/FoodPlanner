package com.example.foodplanner.data.network;

import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("search.php")
    Single<Response<Meals>> getMealByName(@Query("s") String nameOfMeal );

    @GET("search.php")
    Single<Response<Meals>>getMealsByFirstLetter(@Query("f") String firstLetterOfMeal);
    @GET("lookup.php")
    Single<Response<Meals>>getMealDetailsById(@Query("i") String idOfMeal);
    @GET("random.php")
    Single<Response<Meals>>getRandomMeal();

    @GET("categories.php")
    Single<Response<CategoriesWithDetails>>getAllCategoriesWithDetails();

    @GET("list.php?c=list")
    Single<Response<Categories>>getAllCategories();
    @GET("list.php?a=list")
    Single<Response<Countries>>getAllCountries();
    @GET("list.php?i=list")
    Single<Response<Ingredients>>getAllIngredients();

    @GET("filter.php")
    Single<Response<FilteredItems>>filterByMainIngredient(@Query("i") String nameOfMainIngredient);
    @GET("filter.php")
    Single<Response<FilteredItems>>filterByCategory(@Query("c") String nameOfCategory);
    @GET("filter.php")
    Single<Response<FilteredItems>>filterByArea(@Query("a") String nameOfArea);

}
