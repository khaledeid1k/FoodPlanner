package com.example.foodplanner.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class DataItem<T> implements Parcelable {

    private Tag<T> tag;

    public Tag<T>getTag() {
        return tag;
    }

    public DataItem(Tag<T> tag) {
        this.tag = tag;
    }
    protected DataItem(Parcel in) {
    }

    public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel in) {
            return new DataItem(in);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}












