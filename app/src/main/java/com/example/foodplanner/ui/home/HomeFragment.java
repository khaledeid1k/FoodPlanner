package com.example.foodplanner.ui.home;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.HeaderItem;
import com.example.foodplanner.data.models.MealsItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeView {
    String TAG="HomeFragment";

    Meal header;
    List<Meal> listMeals;
    RecyclerView recyclerView;
    HomePresenter presenter;
    HomeAdapter homeAdapter;
    List<DataItem> items ;
    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setup(view);
        presenter.getMealsByFirstLetter("a");
        presenter.getAllCountries();
        presenter.getAllCategoriesWithDetails();
        presenter.getRandomMeal();

    }

    void setup(View view){
        recyclerView=view.findViewById(R.id.recycle_home);

        presenter=new HomePresenter(this,
                RepositoryIm.getInstance(NetWork.getInstance()));
    }
    @Override
    public void getRandomMeal(Meal meal) {
        header=meal;
//        Glide.with(getActivity()).load(meal.getStrMealThumb()).error(
//                R.drawable.no_result_search
//        ).into(imageMealOfDay);
//        nameMeaOfDay.setText(meal.getStrMeal());
//        countryMeaOfDay.setText(meal.getStrArea());
    }

    @Override
    public void getMealsByFirstLetter(List<Meal> meals) {
        listMeals=meals;
        Log.i(TAG, "getMealsByFirstLetter: "+meals.get(0).getStrCategory());
        items = Arrays.asList(
                new HeaderItem(header),
                new MealsItem( new Tag<>("Meals", listMeals))
        );
        homeAdapter=new HomeAdapter(getActivity(),items);

        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void getAllCategoriesWithDetails(List<CategoryWithDetails> categoryWithDetailsList) {
        Log.i(TAG, "getAllCategoriesWithDetails: "+categoryWithDetailsList);
    }

    @Override
    public void getAllCountries(List<Country> countryList) {
        Log.i(TAG, "getAllCountries: "+ countryList);

    }


}