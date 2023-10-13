package com.example.foodplanner.ui.plan.details;

import static com.example.foodplanner.utils.Constants.UserId;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.repository.Repository;

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


}
