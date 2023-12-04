package com.example.foodplanner.data.network;

import com.example.foodplanner.BuildConfig;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteSourceIm implements RemoteSource {

    private RemoteSourceIm(){}
    private  static RemoteSourceIm remoteSourceIm =null;
    public static RemoteSourceIm getInstance(){
        return remoteSourceIm !=null ? remoteSourceIm : new RemoteSourceIm();
    }

  private  Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
       public Services services = retrofit.create(Services.class);


    @Override
    public  Single<Response<Meals>>getMealByName(String nameOfMeal) {
      return   services.getMealByName(nameOfMeal);
    }

    @Override
    public Single<Response<Meals>>getMealsByFirstLetter(String firstLetterOfMeal) {
        return services.getMealsByFirstLetter(firstLetterOfMeal);
    }

    @Override
    public  Single<Response<Meals>>getMealDetailsById(String idOfMeal) {
        return services.getMealDetailsById(idOfMeal);
    }

    @Override
    public  Single<Response<Meals>>getRandomMeal() {
        return services.getRandomMeal();
    }

    @Override
    public Single<Response<CategoriesWithDetails>>getAllCategoriesWithDetails() {
        return services.getAllCategoriesWithDetails();
    }

    @Override
    public  Single<Response<Categories>>getAllCategories() {
        return services.getAllCategories();
    }

    @Override
    public Single<Response<Countries>>getAllCountries() {
        return services.getAllCountries();
    }

    @Override
    public  Single<Response<Ingredients>>getAllIngredients() {
        return services.getAllIngredients();
    }

    @Override
    public  Single<Response<FilteredItems>>filterByMainIngredient(String nameOfMainIngredient) {
        return services.filterByMainIngredient(nameOfMainIngredient);
    }

    @Override
    public  Single<Response<FilteredItems>>filterByCategory(String nameOfCategory) {
        return services.filterByCategory(nameOfCategory);
    }

    @Override
    public  Single<Response<FilteredItems>>filterByArea(String nameOfArea) {
        return services.filterByArea(nameOfArea);
    }
}
