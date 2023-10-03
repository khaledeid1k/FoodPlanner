package com.example.foodplanner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
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

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    HomePresenter presenter;
    HomeAdapter homeAdapter;
    ArrayList<DataItem> items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        addRandomMeal();
        addMealsByFirstLetter();
        addCategoriesWithDetails();
        addAllCountries();
        recyclerView.setAdapter(homeAdapter);
    }

    void inti(View view) {
        recyclerView = view.findViewById(R.id.recycle_home);
        presenter = new HomePresenter(
                RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(requireActivity())));
        items = new ArrayList<>();
        homeAdapter = new HomeAdapter(requireActivity(), items, presenter, presenter);

    }

    void addRandomMeal() {
        presenter.randomMealLiveData().observe(getViewLifecycleOwner(), meal -> {
            items.add(0,new HeaderItem(meal));
            homeAdapter.updateData(items);
        });

    }

    void addMealsByFirstLetter() {
        presenter.mealsByFirstLetter().observe(getViewLifecycleOwner(), meals -> {
            items.add(new MealsItem(new Tag<>("Meals", meals)));
            homeAdapter.updateData(items);
        });
    }

    void addCategoriesWithDetails() {
        presenter.categoriesWithDetails().observe(getViewLifecycleOwner(), categoryWithDetailsList -> {
            items.add(new CategoriesItem(new Tag<>("Categories", categoryWithDetailsList)));
            homeAdapter.updateData(items);
        });
    }

    void addAllCountries() {

        presenter.allCountries().observe(getViewLifecycleOwner(), countryList -> {
            items.add(new CountriesItem(new Tag<>("Countries", countryList)));
            homeAdapter.updateData(items);
        });


    }




}







