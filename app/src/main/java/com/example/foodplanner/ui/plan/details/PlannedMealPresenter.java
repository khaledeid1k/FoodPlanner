package com.example.foodplanner.ui.plan.details;

import static com.example.foodplanner.utils.Constants.UserId;

import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannedMealPresenter extends BasePresenter implements PlannedMealPresenterView {

    Repository repository;
    PlannedMealView plannedMealView;


    public PlannedMealPresenter(Repository repository, PlannedMealView plannedMealView
    ) {
        this.repository = repository;
        this.plannedMealView = plannedMealView;

    }

    @Override
    public void getPlanedMeal(String day, String timeOfMeal) {
        repository.getPlanedMeal(day, timeOfMeal, UserId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        planedMeal ->  {
                            plannedMealView.getPlanedMeal(planedMeal);
                        },
                        error -> {
                            plannedMealView.showError(error.getMessage());
                        }

                );
    }

    @Override
    public void deletePlanedMeal(PlanedMeal planedMeal) {
        repository.deletePlanedMeal(planedMeal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        () -> {
                        },
                        e -> plannedMealView.showError(e.getMessage())
                );
        ;
    }


}
