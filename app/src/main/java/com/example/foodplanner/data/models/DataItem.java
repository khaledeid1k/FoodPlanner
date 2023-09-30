package com.example.foodplanner.data.models;

import androidx.annotation.Nullable;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.ui.base.BaseInteractionListener;

import java.util.List;

public class DataItem<T> {
   private Tag<T> tag;

    public Tag<T>getTag() {
        return tag;
    }

    public DataItem(Tag<T> tag) {
        this.tag = tag;
    }

}

//class CategoriesItem extends DataItem<CategoriesWithDetails> {
//    Tag<CategoriesWithDetails> tag;
//    CategoriesInteractionListener categoriesInteractionListener;
//
//    public Tag<CategoriesWithDetails> getTag() {
//        return tag;
//    }
//
//    public CategoriesInteractionListener getCategoriesInteractionListener() {
//        return categoriesInteractionListener;
//    }
//
//    public CategoriesItem(Tag<CategoriesWithDetails> tag, CategoriesInteractionListener categoriesInteractionListener) {
//        super(tag);
//        this.tag = tag;
//        this.categoriesInteractionListener = categoriesInteractionListener;
//    }
//}
//
//  class CountriesItem extends DataItem<Countries> {
//    CountriesInteractionListener countriesInteractionListener;
//    Tag<Countries> tag;
//
//    public CountriesInteractionListener getCountriesInteractionListener() {
//        return countriesInteractionListener;
//    }
//
//    public Tag<Countries> getTag() {
//        return tag;
//    }
//
//    public CountriesItem(CountriesInteractionListener countriesInteractionListener, Tag<Countries> tag) {
//        super(tag);
//        this.countriesInteractionListener = countriesInteractionListener;
//        this.tag = tag;
//    }
//}

interface MealsInteractionListener extends BaseInteractionListener {
}

interface CategoriesInteractionListener extends BaseInteractionListener {
}

interface CountriesInteractionListener extends BaseInteractionListener {
}



