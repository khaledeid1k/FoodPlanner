package com.example.foodplanner.data.local.dp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM favorite_table")
    LiveData<List<Meal>> getAllFavoritesMeals();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);


}
