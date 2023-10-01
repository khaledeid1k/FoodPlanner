package com.example.foodplanner.ui.home.adapter;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.country.Countries;
import com.example.foodplanner.data.models.country.Country;

import java.util.List;

public class CountriesItem extends DataItem<List<Country>> {
    Tag<List<Country>> tag;

    public Tag<List<Country>> getTag() {

        return tag;
    }

    public CountriesItem(Tag<List<Country>> tag) {
        super(tag);
        this.tag = tag;
    }
}