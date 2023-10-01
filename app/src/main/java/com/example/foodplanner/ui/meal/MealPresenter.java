package com.example.foodplanner.ui.meal;

import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealPresenter {




    Meal meal;
    ArrayList<IngredientMeasurePair> ingredientMeasurePairs;
    ArrayList<Instructions> instructionsArrayList;
    public MealPresenter(Meal meal,
                         ArrayList<IngredientMeasurePair> ingredientMeasurePairs,
                         ArrayList<Instructions> instructionsArrayList) {

        this.meal = meal;
        this.ingredientMeasurePairs = ingredientMeasurePairs;
        this.instructionsArrayList = instructionsArrayList;
        addIngredientAndMeasure();
        addInstructions();
    }

    void addIngredientAndMeasure() {
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient1(), meal.getStrMeasure1()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient2(), meal.getStrMeasure2()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient2(), meal.getStrMeasure3()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient4(), meal.getStrMeasure4()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient5(), meal.getStrMeasure5()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient6(), meal.getStrMeasure6()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient7(), meal.getStrMeasure7()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient8(), meal.getStrMeasure8()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient9(), meal.getStrMeasure9()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient10(), meal.getStrMeasure10()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient11(), meal.getStrMeasure11()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient12(), meal.getStrMeasure12()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient13(), meal.getStrMeasure13()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient14(), meal.getStrMeasure14()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient15(), meal.getStrMeasure15()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient16(), meal.getStrMeasure16()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient17(), meal.getStrMeasure17()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient18(), meal.getStrMeasure18()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient19(), meal.getStrMeasure19()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient20(), meal.getStrMeasure20()));

        List<IngredientMeasurePair> collect = ingredientMeasurePairs.stream().filter(
                i -> i.getIngredient() != null && !i.getIngredient().trim().isEmpty()
        ).collect(Collectors.toList());
        ingredientMeasurePairs = (ArrayList<IngredientMeasurePair>) collect;


    }

    void addInstructions() {
        String[] split = meal.getStrInstructions().split("\\.");
        int stepNumber = 1;
        for (String s : split) {
            String trimmedInstruction = s.trim();
            if (!trimmedInstruction.isEmpty()) {
                instructionsArrayList.add(new Instructions(stepNumber + "-", trimmedInstruction));
                stepNumber++;
            }
        }
    }
}
