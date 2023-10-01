package com.example.foodplanner.ui.home.adapter;

import android.view.View;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.ui.base.BaseInteractionListener;

public interface NavigationToShowAll extends BaseInteractionListener {
    void onNavigate(DataItem dataItem, View view);
}