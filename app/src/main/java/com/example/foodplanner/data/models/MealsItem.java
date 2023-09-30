package com.example.foodplanner.data.models;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;

import java.util.List;

public class MealsItem extends DataItem<List<Meal>> {
    Tag<List<Meal>> tag;
    MealsInteractionListener mealsInteractionListener;

    public  Tag<List<Meal>> getTag() {
        return tag;
    }

    public MealsInteractionListener getMealsInteractionListener() {
        return mealsInteractionListener;
    }

    public MealsItem(Tag<List<Meal>> tag
                 //    MealsInteractionListener mealsInteractionListener
    ) {
        super(tag);
        this.tag = tag;
       // this.mealsInteractionListener = mealsInteractionListener;
    }
}
