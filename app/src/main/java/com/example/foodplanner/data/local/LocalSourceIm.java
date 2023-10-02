package com.example.foodplanner.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.local.dp.FoodDao;
import com.example.foodplanner.data.local.dp.FoodDataBase;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public class LocalSourceIm implements LocalSource {

    FoodDao foodDao;
    Context context;

    private static LocalSourceIm localSourceIm = null;

    public static LocalSourceIm getInstance(Context context) {
        if (localSourceIm == null) {
            localSourceIm = new LocalSourceIm(context);
        }
        return localSourceIm;
    }

    public LocalSourceIm(Context context) {
        this.context = context;
        FoodDataBase dataBase = FoodDataBase.getInstance(context);
        foodDao = dataBase.getFoodDao();
    }

    @Override
    public LiveData<List<Meal>> getFavoritesMeals() {
        return foodDao.getAllFavoritesMeals();
    }

    @Override
    public void saveMeal(Meal meal) {
        new Thread(() -> foodDao.saveMeal(meal)).start();

    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(() -> foodDao.deleteMeal(meal)).start();

    }
}
