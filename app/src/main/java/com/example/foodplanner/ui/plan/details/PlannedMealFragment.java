package com.example.foodplanner.ui.plan.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.ui.favourite.FavouriteAdapter;
import com.example.foodplanner.utils.Extensions;


public class PlannedMealFragment extends BaseFragment implements PlannedMealView {
    ImageFilterView mealImage;
    LottieAnimationView lottieAnimation;
    TextView noPlanedMeals;
    TextView tileOfListPlannedMeals;
    TextView nameOfMeal;
    Button deleteMeal;
    ImageView back;
    CardView cardOfPlanedMeal;

    PlannedMealPresenterView plannedMealPresenterView;
    String timeOfMeal;
    String day;
    PlannedMealPresenter plannedMealPresenter;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        receiveData();
        init(view);
        setUp();
        setDayAndTime();

    }

    void init(View view) {
        mealImage = view.findViewById(R.id.photo_meal);
        nameOfMeal = view.findViewById(R.id.title_meal);
        deleteMeal = view.findViewById(R.id.delete_meal);
        lottieAnimation = view.findViewById(R.id.lottie_animation_planned);
        noPlanedMeals = view.findViewById(R.id.no_plan_Text);
        tileOfListPlannedMeals = view.findViewById(R.id.tile_of_list_planned_meals);
        back = view.findViewById(R.id.back_from_plan_details);
        cardOfPlanedMeal = view.findViewById(R.id.card_of_planed_meal);


    }

    void receiveData() {
        PlannedMealFragmentArgs plannedMealsFragmentArgs = PlannedMealFragmentArgs.fromBundle(getArguments());
        timeOfMeal = plannedMealsFragmentArgs.getTimeOfMeal();
        day = plannedMealsFragmentArgs.getDay();
    }

    void setUp() {
        plannedMealPresenter = new PlannedMealPresenter(RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(getActivity())), this);
        plannedMealPresenterView = plannedMealPresenter;
        plannedMealPresenterView.getPlanedMeal(day, timeOfMeal);
        back.setOnClickListener(Extensions::closeFragment);


    }
    void  addData(PlanedMeal planedMeal){

        Glide.with(requireActivity()).load(planedMeal.getMeal().getStrMealThumb())
                .error(
                R.drawable.ic_launcher_background
        ).into(mealImage);
        nameOfMeal.setText(planedMeal.getMeal().getStrMeal());

        deletePlanedMeal(planedMeal);

        navigateToMealDetails(planedMeal.getMeal());

    }

    void setDayAndTime(){
        String timeAndDay = timeOfMeal + " Of " + day;
        tileOfListPlannedMeals.setText(timeAndDay);
    }


    public void deletePlanedMeal(PlanedMeal planedMeal) {
        deleteMeal.setOnClickListener(view -> {
            plannedMealPresenterView.deletePlanedMeal(planedMeal);
            NoPlanedMeal();
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_planned_meals;
    }

    @Override
    public void getPlanedMeal(LiveData<PlanedMeal> planedMealLiveData) {
        planedMealLiveData.observe(getViewLifecycleOwner(), planedMeal -> {
            if (planedMeal != null) {
                existPlanedMeal();
                addData(planedMeal);
            } else {
                NoPlanedMeal();
            }

        });
    }
    void NoPlanedMeal(){
        lottieAnimation.setVisibility(View.VISIBLE);
        noPlanedMeals.setVisibility(View.VISIBLE);
        deleteMeal.setVisibility(View.INVISIBLE);
        cardOfPlanedMeal.setVisibility(View.INVISIBLE);
    }
    void existPlanedMeal(){
        lottieAnimation.setVisibility(View.INVISIBLE);
        noPlanedMeals.setVisibility(View.INVISIBLE);
        deleteMeal.setVisibility(View.VISIBLE);
        cardOfPlanedMeal.setVisibility(View.VISIBLE);
    }

    void navigateToMealDetails(Meal meal){
        mealImage.setOnClickListener(view -> {
            PlannedMealFragmentDirections.ActionPlannedMealFragmentToMealFragment action =
                    PlannedMealFragmentDirections.actionPlannedMealFragmentToMealFragment(
                            meal);
            Navigation.findNavController(view).navigate(
                    action
            );
        });
    }
}