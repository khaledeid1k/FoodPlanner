package com.example.foodplanner.data.network;

import com.example.foodplanner.BuildConfig;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork implements RemoteSource {

    private NetWork(){}
    private  static  NetWork netWork=null;
    public static NetWork getInstance(){
        return netWork!=null ? netWork : new NetWork();
    }

  private  Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
       public Services services = retrofit.create(Services.class);


    @Override
    public Call<Meals> getMealByName(String nameOfMeal) {
      return   services.getMealByName(nameOfMeal);
    }

    @Override
    public Call<Meals> getMealsByFirstLetter(String firstLetterOfMeal) {
        return services.getMealsByFirstLetter(firstLetterOfMeal);
    }

    @Override
    public Call<Meals> getMealDetailsById(String idOfMeal) {
        return services.getMealDetailsById(idOfMeal);
    }

    @Override
    public Call<Meals> getRandomMeal() {
        return services.getRandomMeal();
    }

    @Override
    public Call<CategoriesWithDetails> getAllCategoriesWithDetails() {
        return services.getAllCategoriesWithDetails();
    }

    @Override
    public Call<Categories> getAllCategories() {
        return services.getAllCategories();
    }

    @Override
    public Call<Countries> getAllCountries() {
        return services.getAllCountries();
    }

    @Override
    public Call<Ingredients> getAllIngredients() {
        return services.getAllIngredients();
    }

    @Override
    public Call<FilteredItems> filterByMainIngredient(String nameOfMainIngredient) {
        return services.filterByMainIngredient(nameOfMainIngredient);
    }

    @Override
    public Call<FilteredItems>filterByCategory(String nameOfCategory) {
        return services.filterByCategory(nameOfCategory);
    }

    @Override
    public Call<FilteredItems> filterByArea(String nameOfArea) {
        return services.filterByArea(nameOfArea);
    }
}
