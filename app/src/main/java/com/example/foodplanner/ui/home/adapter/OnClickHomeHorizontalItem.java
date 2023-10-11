package com.example.foodplanner.ui.home.adapter;

import android.view.View;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.ui.base.BaseInteractionListener;

public  interface OnClickHomeHorizontalItem extends BaseInteractionListener {
    void clickHomeItem(DataItem dataItem, int position, View view);
}