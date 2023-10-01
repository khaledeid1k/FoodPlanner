package com.example.foodplanner.data.models;

public class IngredientMeasurePair {
    private String ingredient;
    private String measure;

    public IngredientMeasurePair(String ingredient, String measure) {
        this.ingredient = ingredient;
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }
}
