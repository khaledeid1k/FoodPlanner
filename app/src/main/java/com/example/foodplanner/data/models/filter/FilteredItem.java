package com.example.foodplanner.data.models.filter;

import java.util.Objects;

public class FilteredItem {
    String strMeal;
    String strMealThumb;
    String idMeal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilteredItem that = (FilteredItem) o;
        return Objects.equals(strMeal, that.strMeal) && Objects.equals(strMealThumb, that.strMealThumb) && Objects.equals(idMeal, that.idMeal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strMeal, strMealThumb, idMeal);
    }

    public FilteredItem(String strMeal, String strMealThumb, String idMeal) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }
}
