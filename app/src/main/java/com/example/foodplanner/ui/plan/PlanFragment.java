package com.example.foodplanner.ui.plan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.foodplanner.R;
import com.example.foodplanner.utils.Constants;


public class PlanFragment extends Fragment {

    CheckBox showSaturday;
    ConstraintLayout mealsOfSaturday;
    ImageView breakfastSaturday, dinnerSaturday, lunchSaturday;

    CheckBox showSunday;
    ConstraintLayout mealsOfSunday;
    ImageView breakfastSunday, dinnerSunday, lunchSunday;


    CheckBox showMonday;
    ConstraintLayout mealsOfMonday;
    ImageView breakfastMonday, dinnerMonday, lunchMonday;




    CheckBox showTuesday;
    ConstraintLayout mealsOfTuesday;
    ImageView breakfastTuesday, dinnerTuesday, lunchTuesday;

    CheckBox showWednesday;
    ConstraintLayout mealsOfWednesday;
    ImageView breakfastWednesday, dinnerWednesday, lunchWednesday;

    CheckBox showThursday;
    ConstraintLayout mealsOfThursday;
    ImageView breakfastThursday, dinnerThursday, lunchThursday;

    CheckBox showFriday;
    ConstraintLayout mealsOfFriday;
    ImageView breakfastFriday, dinnerFriday, lunchFriday;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view) {
        showSaturday = view.findViewById(R.id.show_saturday);
        mealsOfSaturday = view.findViewById(R.id.meals_of_saturday);
        breakfastSaturday = view.findViewById(R.id.image_breakfast_saturday_item);
        dinnerSaturday = view.findViewById(R.id.image_dinner_saturday_item);
       lunchSaturday = view.findViewById(R.id.image_lunch_saturday_item);

        showSunday = view.findViewById(R.id.show_sunday);
        mealsOfSunday = view.findViewById(R.id.meals_of_sunday);
        breakfastSunday = view.findViewById(R.id.image_breakfast_sunday_item);
        dinnerSunday = view.findViewById(R.id.image_sunday_dinner_item);
        lunchSunday = view.findViewById(R.id.image_lunch_sunday_item);


        showMonday = view.findViewById(R.id.show_monday);
        mealsOfMonday = view.findViewById(R.id.meals_of_monday);
        breakfastMonday = view.findViewById(R.id.image_breakfast_monday_item);
        dinnerMonday = view.findViewById(R.id.image_dinner_monday_item);
        lunchMonday = view.findViewById(R.id.image_lunch_monday_item);

        showTuesday = view.findViewById(R.id.show_tuesday);
        mealsOfTuesday = view.findViewById(R.id.meals_of_tuesday);
        breakfastTuesday = view.findViewById(R.id.image_breakfast_tuesday_item);
        dinnerTuesday = view.findViewById(R.id.image_dinner_tuesday_item);
        lunchTuesday = view.findViewById(R.id.image_lunch_tuesday_item);


        showWednesday = view.findViewById(R.id.show_wednesday);
        mealsOfWednesday = view.findViewById(R.id.meals_of_wednesday);
        breakfastWednesday = view.findViewById(R.id.image_breakfast_wednesday_item);
        dinnerWednesday = view.findViewById(R.id.image_dinner_wednesday_item);
        lunchWednesday = view.findViewById(R.id.image_lunch_wednesday_item);

        showThursday = view.findViewById(R.id.show_thursday);
        mealsOfThursday = view.findViewById(R.id.meals_of_thursday);
        breakfastThursday = view.findViewById(R.id.image_breakfast_thursday_item);
        dinnerThursday = view.findViewById(R.id.image_dinner_thursday_item);
        lunchThursday = view.findViewById(R.id.image_lunch_thursday_item);

        showFriday = view.findViewById(R.id.show_friday);
        mealsOfFriday = view.findViewById(R.id.meals_of_friday);
        breakfastFriday = view.findViewById(R.id.image_breakfast_friday_item);
        dinnerFriday = view.findViewById(R.id.image_dinner_friday_item);
        lunchFriday = view.findViewById(R.id.image_lunch_friday_item);

    }

    void setUp() {
        setupMenuClickListeners(
                Constants.Saturday,
                breakfastSaturday,
                dinnerSaturday,
                lunchSaturday);
        setupMenuClickListeners(
                Constants.Sunday,
                breakfastSunday,
                dinnerSunday,
                lunchSunday);


        setupMenuClickListeners(
                Constants.Monday,
                breakfastMonday,
                dinnerMonday,
                lunchMonday);


        setupMenuClickListeners(
                Constants.Tuesday,
                breakfastTuesday,
                dinnerTuesday,
                lunchTuesday);

        setupMenuClickListeners(
                Constants.Wednesday,
                breakfastWednesday,
                dinnerWednesday,
                lunchWednesday);

        setupMenuClickListeners(
                Constants.Thursday,
                breakfastThursday,
                dinnerThursday,
                lunchThursday);

        setupMenuClickListeners(
                Constants.Friday,
                breakfastFriday,
                dinnerFriday,
                lunchFriday);




    }

    @Override
    public void onStart() {
        super.onStart();
        setupMenuVisibility(showSaturday, mealsOfSaturday);
        setupMenuVisibility(showSunday, mealsOfSunday);
        setupMenuVisibility(showMonday, mealsOfMonday);
        setupMenuVisibility(showTuesday, mealsOfTuesday);
        setupMenuVisibility(showWednesday, mealsOfWednesday);
        setupMenuVisibility(showThursday, mealsOfThursday);
        setupMenuVisibility(showFriday, mealsOfFriday);


        checkManuOfSaturday(showSaturday, mealsOfSaturday);
        checkManuOfSaturday(showSunday, mealsOfSunday);
        checkManuOfSaturday(showMonday, mealsOfMonday);
        checkManuOfSaturday(showTuesday, mealsOfTuesday);
        checkManuOfSaturday(showWednesday, mealsOfWednesday);
        checkManuOfSaturday(showThursday, mealsOfThursday);
        checkManuOfSaturday(showFriday, mealsOfFriday);


    }
    void  checkManuOfSaturday(CheckBox checkBox, View menuView){
        if (checkBox.isChecked()) {
            menuView.setVisibility(View.VISIBLE);
        } else {
            menuView.setVisibility(View.GONE);
        }
    }


    void openMeals(String day, String mealType, View view) {
        PlanFragmentDirections.ActionPlanToPlannedMealFragment action =
                PlanFragmentDirections.actionPlanToPlannedMealFragment(day, mealType);
        Navigation.findNavController(view).navigate(action);
    }
    void setupMenuVisibility(CheckBox checkBox, View menuView) {
        checkBox.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                menuView.setVisibility(View.VISIBLE);
            } else {
                menuView.setVisibility(View.GONE);
            }
        });
    }
    void setupMenuClickListeners(String day, View... menuItems) {
        for (View menuItem : menuItems) {
            menuItem.setOnClickListener(view -> {
                openMeals(day, getMenuType(menuItem.getId()), view);
            });
        }
    }
    String getMenuType(int viewId) {
        switch (viewId) {

            case R.id.image_breakfast_saturday_item:
            case R.id.image_breakfast_sunday_item:
            case R.id.image_breakfast_monday_item:
            case R.id.image_breakfast_tuesday_item:
            case R.id.image_breakfast_wednesday_item:
            case R.id.image_breakfast_thursday_item:
            case R.id.image_breakfast_friday_item:
                return Constants.BreakFast;

            case R.id.image_dinner_saturday_item:
            case R.id.image_sunday_dinner_item:
            case R.id.image_dinner_monday_item:
            case R.id.image_dinner_tuesday_item:
            case R.id.image_dinner_wednesday_item:
            case R.id.image_dinner_thursday_item:
            case R.id.image_dinner_friday_item:
                return Constants.Dinner;
            case R.id.image_lunch_saturday_item:
            case R.id.image_lunch_sunday_item:
            case R.id.image_lunch_monday_item:
            case R.id.image_lunch_tuesday_item:
            case R.id.image_lunch_wednesday_item:
            case R.id.image_lunch_thursday_item:
            case R.id.image_lunch_friday_item:
                return Constants.Lunch;
            default:
                return "";
        }
    }
}

