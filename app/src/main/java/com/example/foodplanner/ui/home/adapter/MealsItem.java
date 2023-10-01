package com.example.foodplanner.ui.home.adapter;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public class MealsItem extends DataItem<List<Meal>> {
    Tag<List<Meal>> tag;

    public  Tag<List<Meal>> getTag() {
        return tag;
    }



    public MealsItem(Tag<List<Meal>> tag
    ) {
        super(tag);
        this.tag = tag;

    }
}
