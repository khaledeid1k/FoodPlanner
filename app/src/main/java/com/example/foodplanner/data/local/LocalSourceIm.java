package com.example.foodplanner.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.local.dp.FoodDao;
import com.example.foodplanner.data.local.dp.FoodDataBase;
import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalSourceIm implements LocalSource {

    FoodDao foodDao;
    Context context;

    private static LocalSourceIm localSourceIm = null;

    public static LocalSourceIm getInstance(Context context) {
        if (localSourceIm == null) {
            localSourceIm = new LocalSourceIm(context);
        }
        return localSourceIm;
    }

    public LocalSourceIm(Context context) {
        this.context = context;
        FoodDataBase dataBase = FoodDataBase.getInstance(context);
        foodDao = dataBase.getFoodDao();
    }

    @Override
    public Single<List<Meal>> getFavoritesMeals(String userId) {
        return foodDao.getAllFavoritesMeals( userId);
    }

    @Override
    public Completable saveMeal(Meal meal) {
        return foodDao.saveMeal(meal);

    }

    @Override
    public Completable deleteMeal(Meal meal) {
      return foodDao.deleteMeal(meal);

    }

    @Override
    public Single<Boolean> getFavoriteMealById(String mealId) {
        return foodDao.getFavoriteMealById(mealId);
    }

    @Override
    public Flowable<PlanedMeal> getPlanedMeals(String day, String timeOfMeal, String userId) {
        return foodDao.getPlanedMeals(day, timeOfMeal, userId);
    }


    @Override
    public Completable deletePlanedMeal(PlanedMeal planedMeal) {
        return foodDao.deletePlanedMeal(planedMeal);
    }

    @Override
    public Completable savePlanedMeal(PlanedMeal planedMeal) {
        return foodDao.savePlanedMeal(planedMeal);
    }

    @Override
    public ArrayList<IngredientMeasurePair> getIngredientAndMeasure(Meal meal) {
        ArrayList<IngredientMeasurePair> ingredientMeasurePairs= new ArrayList<>();
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient1(), meal.getStrMeasure1()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient2(), meal.getStrMeasure2()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient2(), meal.getStrMeasure3()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient4(), meal.getStrMeasure4()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient5(), meal.getStrMeasure5()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient6(), meal.getStrMeasure6()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient7(), meal.getStrMeasure7()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient8(), meal.getStrMeasure8()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient9(), meal.getStrMeasure9()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient10(), meal.getStrMeasure10()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient11(), meal.getStrMeasure11()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient12(), meal.getStrMeasure12()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient13(), meal.getStrMeasure13()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient14(), meal.getStrMeasure14()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient15(), meal.getStrMeasure15()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient16(), meal.getStrMeasure16()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient17(), meal.getStrMeasure17()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient18(), meal.getStrMeasure18()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient19(), meal.getStrMeasure19()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient20(), meal.getStrMeasure20()));
        return  ingredientMeasurePairs;
    }

    @Override
    public String getInstructions(Meal meal) {
        return meal.getStrInstructions();
    }


}
