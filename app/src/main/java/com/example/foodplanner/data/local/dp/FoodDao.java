package com.example.foodplanner.data.local.dp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM favorite_table  WHERE userId = :userId ")
    Single<List<Meal>> getAllFavoritesMeals(String userId);

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_table WHERE idMeal = :mealId LIMIT 1)")
    Single<Boolean> getFavoriteMealById(String mealId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);


    @Query("SELECT * FROM plan_table WHERE day = :day AND timeOfMeal = :timeOfMeal AND userId = :userId")
    Flowable<PlanedMeal> getPlanedMeals(String day, String timeOfMeal, String userId);


    @Delete
    Completable deletePlanedMeal(PlanedMeal planedMeal);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable savePlanedMeal(PlanedMeal planedMeal);

}
