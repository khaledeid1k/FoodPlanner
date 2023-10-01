package com.example.foodplanner.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.ui.home.adapter.CategoriesItem;
import com.example.foodplanner.ui.home.adapter.CountriesItem;
import com.example.foodplanner.ui.home.adapter.HeaderItem;
import com.example.foodplanner.ui.home.adapter.HomeAdapter;
import com.example.foodplanner.ui.home.adapter.MealsItem;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    RecyclerView recyclerView;
    HomePresenter presenter;
    HomeAdapter homeAdapter;
    ArrayList<DataItem> items;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
        addRandomMeal();
        addMealsByFirstLetter();
        addCategoriesWithDetails();
        addAllCountries();
        recyclerView.setAdapter(homeAdapter);
    }

    void setup(View view) {
        recyclerView = view.findViewById(R.id.recycle_home);
        presenter = new HomePresenter(
                RepositoryIm.getInstance(NetWork.getInstance()), "a");
        items = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), items, presenter, presenter);

    }

    void addRandomMeal() {
        presenter.randomMealLiveData().observe(getActivity(), meal -> {
            items.add(0,new HeaderItem(meal));
            homeAdapter.updateData(items);
        });

    }

    void addMealsByFirstLetter() {
        presenter.mealsByFirstLetter().observe(getActivity(), meals -> {
            items.add(new MealsItem(new Tag<>("Meals", meals)));
            homeAdapter.updateData(items);
        });
    }

    void addCategoriesWithDetails() {
        presenter.categoriesWithDetails().observe(getActivity(), categoryWithDetailsList -> {
            items.add(new CategoriesItem(new Tag<>("Categories", categoryWithDetailsList)));
            homeAdapter.updateData(items);
        });
    }

    void addAllCountries() {

        presenter.allCountries().observe(getActivity(), countryList -> {
            items.add(new CountriesItem(new Tag<>("Countries", countryList)));
            homeAdapter.updateData(items);
        });


    }




}







