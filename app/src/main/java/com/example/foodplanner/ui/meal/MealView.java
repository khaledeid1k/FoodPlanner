package com.example.foodplanner.ui.meal;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;

import java.util.ArrayList;

public interface MealView {
    void  getIngredientAndMeasure(ArrayList<IngredientMeasurePair> ingredientMeasurePairs);
    void  getInstructions(ArrayList<Instructions> instructions);
}
