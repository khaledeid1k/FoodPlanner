package com.example.foodplanner.ui.meals;

import static com.example.foodplanner.utils.Extensions.intiStateAnimation;
import static com.example.foodplanner.utils.Extensions.updateUIState;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.RemoteSourceIm;
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
    MealsPresenterView mealsPresenterView;
    AppCompatButton retryButton;

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
        intiStateAnimation(view);
        retryButton=view.findViewById(R.id.retry);
        nameList = view.findViewById(R.id.meals_text);
        back = view.findViewById(R.id.back_from_meals);
        nameOfItem = MealsFragmentArgs.fromBundle(getArguments()).getNameOfItem();


    }

    void setUp() {
        nameList.setText(getString(R.string.meals_of_first_item, nameOfItem.split(",")[0]));

        mealsPresenter = new MealsPresenter(
                RepositoryIm.getInstance(RemoteSourceIm.getInstance(),
                        LocalSourceIm.getInstance(requireActivity())),
                this);
        mealsPresenterView =mealsPresenter;
        meals = new ArrayList<>();
        mealsAdapter = new MealsAdapter(meals, requireActivity(), this);
        recyclerViewMeals.setAdapter(mealsAdapter);
        back.setOnClickListener(Extensions::closeFragment);
        mealsPresenter.getMeals(nameOfItem);

    }


    @Override
    public void onclickMeal(String nameOfMeal) {
        meals.clear();
        mealsAdapter.updateData(meals);
        mealsPresenterView.onclickMeal(nameOfMeal);
    }

    @Override
    public void getMealByName(Meal meal) {
        updateUIState(false,false);

        MealsFragmentDirections.ActionMealsFragmentToMealFragment action =
                MealsFragmentDirections.actionMealsFragmentToMealFragment(
                     meal);
        Navigation.findNavController(requireView()).navigate(
                action
        );
    }

    @Override
    public void getMeals(ArrayList<FilteredItem> filteredItems) {
        updateUIState(false,false);
        mealsAdapter.updateData(filteredItems);
    }

    @Override
    public void showLoading() {
        updateUIState(true,false);

    }

    @Override
    public void showError(String errorMessage) {
        updateUIState(false,true);
        meals.clear();
        mealsAdapter.updateData(meals);
        retryButton.setOnClickListener(view -> mealsPresenter.getMeals(nameOfItem));

    }
}