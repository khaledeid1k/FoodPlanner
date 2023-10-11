package com.example.foodplanner.data.repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.local.LocalSource;
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
import com.example.foodplanner.data.network.RemoteSource;
import com.example.foodplanner.data.network.StateOfResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryIm implements Repository {
    private RemoteSource remoteSource;
    private LocalSource localSource;
    private static RepositoryIm repo = null;

    public static RepositoryIm getInstance(RemoteSource remoteResource,
                                           LocalSource localSource) {
        if (repo == null) {
            repo = new RepositoryIm(remoteResource, localSource);
        }
        return repo;
    }

    public RepositoryIm(RemoteSource remoteResource, LocalSource localSource) {
        this.remoteSource = remoteResource;
        this.localSource = localSource;
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
    public StateOfResponse<FilteredItems> filterByMainIngredient(String nameOfMainIngredient, StateOfResponse<FilteredItems> stateOfResponse) {
        return WrapResponse(remoteSource.filterByMainIngredient(nameOfMainIngredient), stateOfResponse);

    }

    @Override
    public StateOfResponse<FilteredItems> filterByCategory(String nameOfCategory, StateOfResponse<FilteredItems> stateOfResponse) {
        return WrapResponse(remoteSource.filterByCategory(nameOfCategory), stateOfResponse);

    }

    @Override
    public StateOfResponse<FilteredItems> filterByArea(String nameOfArea, StateOfResponse<FilteredItems> stateOfResponse) {
        return WrapResponse(remoteSource.filterByArea(nameOfArea), stateOfResponse);

    }

    @Override
    public LiveData<List<Meal>> getFavoritesMeals(String userId) {
        return localSource.getFavoritesMeals(userId);
    }

    @Override
    public void saveMeal(Meal meal) {
        localSource.saveMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        localSource.deleteMeal(meal);
    }

    @Override
    public LiveData<Boolean> getFavoriteMealById(String mealId) {
        return localSource.getFavoriteMealById(mealId);
    }

    @Override
    public LiveData<PlanedMeal> getPlanedMeals(String day, String timeOfMeal, String userId) {
        return localSource.getPlanedMeals(day, timeOfMeal, userId);
    }

    @Override
    public void deletePlanedMeal(PlanedMeal planedMeal) {
        localSource.deletePlanedMeal(planedMeal);
    }

    @Override
    public void savePlanedMeal(PlanedMeal planedMeal) {
        localSource.savePlanedMeal(planedMeal);
    }

    @Override
    public ArrayList<IngredientMeasurePair> getIngredientAndMeasure(Meal meal) {
        return localSource.getIngredientAndMeasure(meal).stream().filter(
                i -> i.getIngredient() != null && !i.getIngredient().trim().isEmpty()
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Instructions> getInstructions(Meal meal) {
        ArrayList<Instructions> instructions = new ArrayList<>();

        String[] split = localSource.getInstructions(meal).split("\\.");
        int stepNumber = 1;
        for (String s : split) {
            String trimmedInstruction = s.trim();
            if (!trimmedInstruction.isEmpty()) {
                instructions.add(new Instructions(stepNumber + "-",
                        trimmedInstruction));
                stepNumber++;
            }
        }
        return instructions;
    }

    @Override
    public ArrayList<FilteredItem> searchByMeal(Meals meals) {
        if ((meals != null ? meals.getMeals() : null) != null) {
            return meals.getMeals().stream().map(meal ->
                            new FilteredItem(
                                    meal.getStrMeal(),
                                    meal.getStrMealThumb(),
                                    meal.getIdMeal()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return new ArrayList<>();
    }

    @Override
    public ArrayList<FilteredItem> searchInMeals(ArrayList<FilteredItem> meals, String charOfMeal) {
        if (meals != null) {
            return meals.stream()
                    .filter(s -> s.getStrMeal().toLowerCase(Locale.ROOT)
                            .contains(charOfMeal))
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            return new ArrayList<>();
        }

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
