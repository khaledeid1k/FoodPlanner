package com.example.foodplanner.data.local.dp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.utils.Constants;

@Database(entities = {Meal.class ,PlanedMeal.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})

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


