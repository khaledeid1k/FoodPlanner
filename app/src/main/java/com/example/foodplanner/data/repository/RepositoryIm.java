package com.example.foodplanner.data.repository;

import com.example.foodplanner.data.models.category.Categories;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.ingredient.Ingredients;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.RemoteSource;
import com.example.foodplanner.data.network.StateOfResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryIm implements Repository {
    private RemoteSource remoteSource;
    private  static RepositoryIm repo=null;

    public  static RepositoryIm getInstance(RemoteSource remoteResource){
        if(repo==null){
            repo= new RepositoryIm(remoteResource);
        }
        return repo;
    }
    public RepositoryIm(RemoteSource remoteResource) {
        this.remoteSource = remoteResource;
    }

    @Override
    public StateOfResponse<Meals> getMealByName(String nameOfMeal, StateOfResponse<Meals> stateOfResponse) {
        return WrapResponse(remoteSource.getMealByName(nameOfMeal), stateOfResponse);
    }

    @Override
    public StateOfResponse<Meals> getMealsByFirstLetter(String firstLetterOfMeal, StateOfResponse<Meals> stateOfResponse) {
        return WrapResponse(remoteSource.getMealsByFirstLetter(firstLetterOfMeal), stateOfResponse);
    }

    @Override
    public StateOfResponse<Meals> getMealDetailsById(String idOfMeal, StateOfResponse<Meals> stateOfResponse) {
        return WrapResponse(remoteSource.getMealDetailsById(idOfMeal), stateOfResponse);

    }

    @Override
    public StateOfResponse<Meals> getRandomMeal(StateOfResponse<Meals> stateOfResponse) {
        return WrapResponse(remoteSource.getRandomMeal(), stateOfResponse);
    }

    @Override
    public StateOfResponse<CategoriesWithDetails> getAllCategoriesWithDetails(StateOfResponse<CategoriesWithDetails> stateOfResponse) {
        return WrapResponse(remoteSource.getAllCategoriesWithDetails(), stateOfResponse);
    }

    @Override
    public StateOfResponse<Categories> getAllCategories(StateOfResponse<Categories> stateOfResponse) {
        return WrapResponse(remoteSource.getAllCategories(), stateOfResponse);
    }

    @Override
    public StateOfResponse<Countries> getAllCountries(StateOfResponse<Countries> stateOfResponse) {
        return WrapResponse(remoteSource.getAllCountries(), stateOfResponse);

    }

    @Override
    public StateOfResponse<Ingredients> getAllIngredients(StateOfResponse<Ingredients> stateOfResponse) {
        return WrapResponse(remoteSource.getAllIngredients(), stateOfResponse);

    }

    @Override
    public StateOfResponse<FilteredItems> FilterByMainIngredient(String nameOfMainIngredient, StateOfResponse<FilteredItems> stateOfResponse) {
        return WrapResponse(remoteSource.filterByMainIngredient(nameOfMainIngredient), stateOfResponse);

    }

    @Override
    public StateOfResponse<FilteredItems> FilterByCategory(String nameOfCategory, StateOfResponse<FilteredItems> stateOfResponse) {
        return WrapResponse(remoteSource.filterByCategory(nameOfCategory), stateOfResponse);

    }

    @Override
    public StateOfResponse<FilteredItems> FilterByArea(String nameOfArea, StateOfResponse<FilteredItems> stateOfResponse) {
        return WrapResponse(remoteSource.filterByArea(nameOfArea), stateOfResponse);

    }


    <T> StateOfResponse<T> WrapResponse(Call<T> call, StateOfResponse<T> stateOfResponse) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                stateOfResponse.succeeded(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                stateOfResponse.failure(t.getMessage());
            }
        });
        return stateOfResponse;
    }

}
