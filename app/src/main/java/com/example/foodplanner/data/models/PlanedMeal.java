package com.example.foodplanner.data.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.foodplanner.data.models.meal.Meal;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "plan_table", primaryKeys = {"day", "timeOfMeal"})

public class PlanedMeal {
    public String userId;
    @NotNull
    public String day;
    @NotNull
    public String timeOfMeal;
    public  Meal meal;

    public PlanedMeal(String userId, @NonNull String day, @NonNull String timeOfMeal, Meal meal) {
        this.userId = userId;
        this.day = day;
        this.timeOfMeal = timeOfMeal;
        this.meal = meal;
    }


    public String getDay() {
        return day;
    }

    public String getTimeOfMeal() {
        return timeOfMeal;
    }

    public Meal getMeal() {
        return meal;
    }
}