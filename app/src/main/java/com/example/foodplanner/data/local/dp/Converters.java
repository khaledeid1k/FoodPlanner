package com.example.foodplanner.data.local.dp;

import androidx.room.TypeConverter;

import com.example.foodplanner.data.models.meal.Meal;
import com.google.gson.Gson;

public class Converters {
    Gson gson = new Gson();

    @TypeConverter
    public String convertMealToJason(Meal meal) {
        return gson.toJson(meal);
    }

    @TypeConverter
    public Meal jsonToWeatherList(String value) {
        return gson.fromJson(value, Meal.class);

    }
}
