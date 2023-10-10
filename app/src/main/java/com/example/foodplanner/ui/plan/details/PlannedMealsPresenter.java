package com.example.foodplanner.ui.plan.details;

import static com.example.foodplanner.utils.Constants.UserId;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.favourite.OnClickFavorites;

import java.util.List;

public class PlannedMealsPresenter implements OnClickFavorites {

    Repository repository;
    PlannedMealView plannedMealView;
    String timeOfMeal;
    String day;

    public PlannedMealsPresenter(Repository repository, PlannedMealView plannedMealView,
                                 String day, String timeOfMeal) {
        this.repository = repository;
        this.plannedMealView = plannedMealView;
        this.timeOfMeal = timeOfMeal;
        this.day = day;
    }

    @Override
    public void onClickFavorite(Meal meal, View view) {
        PlannedMealsFragmentDirections.ActionPlannedMealFragmentToMealFragment action =
                PlannedMealsFragmentDirections.actionPlannedMealFragmentToMealFragment(
                        meal);
        Navigation.findNavController(view).navigate(
                action
        );
    }

    @Override
    public void deleteFavorite(Meal meal) {
        repository.deletePlanedMeal(new PlanedMeal(UserId,day,timeOfMeal,
                meal
                ));
        plannedMealView.deleteItem();

    }
    LiveData<List<PlanedMeal>> getPlanedMeals(){
        Log.i("TAG", "getPlanedMeals: "+day+timeOfMeal+UserId);
        return repository.getPlanedMeals(day,timeOfMeal,UserId);
    }
}
