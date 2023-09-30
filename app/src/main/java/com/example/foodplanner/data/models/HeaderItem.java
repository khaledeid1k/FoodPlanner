package com.example.foodplanner.data.models;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;

public class HeaderItem extends DataItem<Meal> {
    Meal meal;

    public Meal getMeals() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public HeaderItem(Meal meal) {
        super(new Tag<>("Header",meal));
        this.meal = meal;
    }
}
