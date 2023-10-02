package com.example.foodplanner.data.local.dp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.utils.Constants;

@Database(entities = {Meal.class} , version = 1)
public abstract class FoodDataBase extends RoomDatabase {

    private  static  FoodDataBase foodDataBase=null;

    public abstract  FoodDao getFoodDao();

    public static FoodDataBase getInstance(Context context){

        if(foodDataBase==null){
            foodDataBase= Room.databaseBuilder(context.getApplicationContext(),
                    FoodDataBase.class, Constants.dataBaseName).build();
        }
        return  foodDataBase;
    }


}


