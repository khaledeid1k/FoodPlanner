package com.example.foodplanner.ui.home.adapter;

import android.os.Parcelable;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.ui.base.BaseInteractionListener;

public  interface OnClickItem extends BaseInteractionListener {
    void click(DataItem dataItem,int position);
}