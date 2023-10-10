package com.example.foodplanner.ui.meals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.utils.Extensions;

import java.util.ArrayList;


public class MealsFragment extends BaseFragment {
    RecyclerView recyclerViewMeals;
    TextView nameList;
    MealsAdapter mealsAdapter;
    MealsPresenter mealsPresenter;
    ArrayList<FilteredItem> meals;
    ImageView back;
    String nameOfItem;
    LottieAnimationView lottieAnimationLoadingMeals;

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
                        LocalSourceIm.getInstance(requireActivity())), nameOfItem);
        meals = new ArrayList<>();
        mealsAdapter = new MealsAdapter(meals, requireActivity(), mealsPresenter);
        recyclerViewMeals.setAdapter(mealsAdapter);
        observeFilteredMeals();
        back.setOnClickListener(Extensions::closeFragment);

    }

    void observeFilteredMeals() {
        mealsPresenter.filteredItemsLiveData().observe(getViewLifecycleOwner(),
                filteredItems -> {
                    lottieAnimationLoadingMeals.setVisibility(View.INVISIBLE);
                    mealsAdapter.updateData(filteredItems.getMeals());
                });
    }
}