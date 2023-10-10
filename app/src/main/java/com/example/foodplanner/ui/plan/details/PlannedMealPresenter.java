package com.example.foodplanner.ui.plan.details;

import static com.example.foodplanner.utils.Constants.UserId;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.favourite.OnClickFavorites;

public class PlannedMealPresenter implements PlannedMealPresenterView {

    Repository repository;
    PlannedMealView plannedMealView;


    public PlannedMealPresenter(Repository repository, PlannedMealView plannedMealView
                              ) {
        this.repository = repository;
        this.plannedMealView = plannedMealView;

    }
    @Override
    public void getPlanedMeal(String day, String timeOfMeal){
        plannedMealView.getPlanedMeal(repository.getPlanedMeals(day,timeOfMeal,UserId));
    }

    @Override
    public void deletePlanedMeal(PlanedMeal planedMeal) {
        repository.deletePlanedMeal(planedMeal);
    }

    @Override
    public void onClickPlanedMeal(Meal meal,View view) {
        PlannedMealFragmentDirections.ActionPlannedMealFragmentToMealFragment action =
                PlannedMealFragmentDirections.actionPlannedMealFragmentToMealFragment(
                        meal);
        Navigation.findNavController(view).navigate(
                action
        );
    }
}
