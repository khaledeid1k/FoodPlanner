package com.example.foodplanner.ui.home.adapter;

import android.os.Parcel;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;

import java.util.List;

public class CategoriesItem extends DataItem<List<CategoryWithDetails>> {
    Tag<List<CategoryWithDetails>> tag;

    public CategoriesItem(Tag<List<CategoryWithDetails>> tag) {
        super(tag);
        this.tag = tag;
    }


    public Tag<List<CategoryWithDetails>> getTag() {
        return tag;
    }
}
