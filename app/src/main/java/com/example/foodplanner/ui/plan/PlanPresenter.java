package com.example.foodplanner.ui.plan;

import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;

import java.util.ArrayList;

public class PlanPresenter {

    Meal meal;
    Repository repository;
    ArrayList<IngredientMeasurePair> ingredientMeasurePairs;
    ArrayList<Instructions> instructionsArrayList;
    public PlanPresenter(Meal meal,
                         ArrayList<IngredientMeasurePair> ingredientMeasurePairs,
                         ArrayList<Instructions> instructionsArrayList,
                         Repository repository) {

        this.meal = meal;
        this.ingredientMeasurePairs = ingredientMeasurePairs;
        this.instructionsArrayList = instructionsArrayList;
        this.repository=repository;
    }
}
