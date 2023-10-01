package com.example.foodplanner.data.models.meal;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Meals implements Parcelable {

    List<Meal> meals;

    protected Meals(Parcel in) {
        meals = in.createTypedArrayList(Meal.CREATOR);
    }

    public static final Creator<Meals> CREATOR = new Creator<Meals>() {
        @Override
        public Meals createFromParcel(Parcel in) {
            return new Meals(in);
        }

        @Override
        public Meals[] newArray(int size) {
            return new Meals[size];
        }
    };

    public List<Meal> getMeals() {
        return meals;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(meals);
    }
}
