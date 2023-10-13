package com.example.foodplanner.ui.meals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.utils.Extensions;

import java.util.ArrayList;


public class MealsFragment extends BaseFragment  implements OnClickListener ,
        MealsFragmentView {
    RecyclerView recyclerViewMeals;
    TextView nameList;
    MealsAdapter mealsAdapter;
    MealsPresenter mealsPresenter;
    ArrayList<FilteredItem> meals;
    ImageView back;
    String nameOfItem;
    LottieAnimationView lottieAnimationLoadingMeals;
    MealsPresenterView mealsPresenterView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_meals;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view) {
        recyclerViewMeals = view.findViewById(R.id.recycle_of_meals);
        nameList = view.findViewById(R.id.meals_text);
        back = view.findViewById(R.id.back_from_meals);
        lottieAnimationLoadingMeals = view.findViewById(R.id.lottie_animation_meals);
        nameOfItem = MealsFragmentArgs.fromBundle(getArguments()).getNameOfItem();


    }

    void setUp() {
        nameList.setText(getString(R.string.meals_of_first_item, nameOfItem.split(",")[0]));

        mealsPresenter = new MealsPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(requireActivity())),
                nameOfItem,this);
        mealsPresenterView =mealsPresenter;
        meals = new ArrayList<>();
        mealsAdapter = new MealsAdapter(meals, requireActivity(), this);
        recyclerViewMeals.setAdapter(mealsAdapter);
        back.setOnClickListener(Extensions::closeFragment);

    }


    @Override
    public void onclickMeal(String nameOfMeal) {
        mealsPresenterView.onclickMeal(nameOfMeal);
    }

    @Override
    public void getMealByName(Meal meal) {
        MealsFragmentDirections.ActionMealsFragmentToMealFragment action =
                MealsFragmentDirections.actionMealsFragmentToMealFragment(
                     meal);
        Navigation.findNavController(requireView()).navigate(
                action
        );
    }

    @Override
    public void getMeals(ArrayList<FilteredItem> filteredItems) {
        lottieAnimationLoadingMeals.setVisibility(View.INVISIBLE);
        mealsAdapter.updateData(filteredItems);
    }
}