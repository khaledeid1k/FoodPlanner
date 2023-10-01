package com.example.foodplanner.ui.home.adapter;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.meal.Meal;

public class HeaderItem extends DataItem<Meal> {
    Meal meal;

    public Meal getMeals() {
        return meal;
    }


    public HeaderItem(Meal meal) {
        super(new Tag<>("Header",meal));
        this.meal = meal;
    }
}
