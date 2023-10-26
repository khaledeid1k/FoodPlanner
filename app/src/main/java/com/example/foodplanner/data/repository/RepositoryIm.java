package com.example.foodplanner.data.repository;

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
import com.example.foodplanner.data.repository.state.Failed;
import com.example.foodplanner.data.repository.state.StateOfResponse;
import com.example.foodplanner.data.repository.state.Success;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
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


    //region remote
    @Override
    public Single<StateOfResponse<Meal>> getMealByName(String nameOfMeal
    ) {
        return wrapResponseAndMakeOperation(remoteSource.getMealByName(nameOfMeal),
                meals-> meals.getMeals().get(0));
    }

    @Override
    public Single<StateOfResponse<Meals>> getMealsByFirstLetter(String firstLetterOfMeal
    ) {
        return wrapResponse(remoteSource.getMealsByFirstLetter(firstLetterOfMeal));
    }

    @Override
    public Single<StateOfResponse<Meals>> getMealDetailsById(String idOfMeal) {
        return null;
    }

    @Override
    public Single<StateOfResponse<Meal>> getRandomMeal() {
        return wrapResponseAndMakeOperation(remoteSource.getRandomMeal(),
               meals->meals.getMeals().get(0) );
    }

    @Override
    public Single<StateOfResponse<CategoriesWithDetails>> getAllCategoriesWithDetails() {
        return wrapResponse(remoteSource.getAllCategoriesWithDetails());
    }


    @Override
    public Single<StateOfResponse<Categories>> getAllCategories() {
        return wrapResponse(remoteSource.getAllCategories());
    }

    @Override
    public Single<StateOfResponse<Countries>> getAllCountries() {
        return wrapResponse(remoteSource.getAllCountries());
    }

    @Override
    public Single<StateOfResponse<Ingredients>> getAllIngredients() {
        return null;
    }

    @Override
    public Single<StateOfResponse<FilteredItems>> filterByMainIngredient(String nameOfMainIngredient) {
        return wrapResponse(remoteSource.filterByMainIngredient(nameOfMainIngredient));
    }

    @Override
    public Single<StateOfResponse<FilteredItems>> filterByCategory(String nameOfCategory) {
        return wrapResponse(remoteSource.filterByCategory(nameOfCategory));
    }

    @Override
    public Single<StateOfResponse<FilteredItems>> filterByArea(String nameOfArea) {
        return wrapResponse(remoteSource.filterByArea(nameOfArea));

    }
    // endregion

    //region local

    @Override
    public Observable<List<Meal>> getFavoritesMeals(String userId) {
        return localSource.getFavoritesMeals(userId);
    }

    @Override
    public Completable saveMeal(Meal meal) {
        return localSource.saveMeal(meal);
    }

    @Override
    public Completable deleteMeal(Meal meal) {
        return localSource.deleteMeal(meal);
    }

    @Override
    public Single<Boolean> getFavoriteMealById(String mealId) {
        return localSource.getFavoriteMealById(mealId);
    }

    @Override
    public Flowable<PlanedMeal> getPlanedMeal(String day, String timeOfMeal, String userId) {
        return localSource.getPlanedMeal(day, timeOfMeal, userId);
    }

    @Override
    public Completable deletePlanedMeal(PlanedMeal planedMeal) {
        return localSource.deletePlanedMeal(planedMeal);
    }

    @Override
    public Completable savePlanedMeal(PlanedMeal planedMeal) {
        return localSource.savePlanedMeal(planedMeal);
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
                    .distinct().sorted(Comparator.comparing(FilteredItem::getStrMeal))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return new ArrayList<>();
    }

    @Override
    public ArrayList<FilteredItem> searchInMeals(ArrayList<FilteredItem> meals,
                                                 String charOfMeal) {
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
    // endregion

    @NonNull <T> Single<StateOfResponse<T>> wrapResponse(Single<Response<T>> response) {
        return response.map(responseBody -> {
            if (responseBody.isSuccessful()) {
               return new Success<>(responseBody.body());
            } else {
                return  new Failed<T>(responseBody.message());
            }
        }

        ).onErrorReturn(throwable -> new Failed<>(throwable.getMessage()));



    }

    @NonNull <T,R> Single<StateOfResponse<R>> wrapResponseAndMakeOperation(
            Single<Response<T>> response, Function<T, R> mapper) {
        return response.map(responseBody -> {
                    if (responseBody.isSuccessful()) {
                        R mappedData = mapper.apply(responseBody.body());
                        return new Success<>(mappedData);
                    } else {
                        return  new Failed<R>(responseBody.message());
                    }
                }

        ).onErrorReturn(throwable -> new Failed<>(throwable.getMessage()));



    }

}
