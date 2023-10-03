package com.example.foodplanner.ui.meals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSource;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.home.HomePresenter;
import com.example.foodplanner.ui.home.adapter.ItemsAdapter;
import com.example.foodplanner.ui.meal.MealFragmentArgs;
import com.example.foodplanner.utils.Constants;
import com.example.foodplanner.utils.Extensions;

import java.util.ArrayList;


public class MealsFragment extends Fragment {
    RecyclerView recyclerViewMeals;
    TextView nameList;
    MealsAdapter mealsAdapter;
    MealsPresenter mealsPresenter;
    ArrayList<FilteredItem> meals;
    ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view){
        recyclerViewMeals=view.findViewById(R.id.recycle_of_meals);
        nameList=view.findViewById(R.id.meals_text);
        back = view.findViewById(R.id.back_from_meals);

        String nameOfItem = MealsFragmentArgs.fromBundle(getArguments()).getNameOfItem();
        nameList.setText(getString(R.string.meals_of_first_item, nameOfItem.split(",")[0]));
        mealsPresenter = new MealsPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(requireActivity())),nameOfItem);
        meals=new ArrayList<>();
        mealsAdapter = new MealsAdapter(meals, requireActivity(),mealsPresenter);
        recyclerViewMeals.setAdapter(mealsAdapter);

    }
    void setUp(){
        mealsPresenter.filteredItemsLiveData().observe(getViewLifecycleOwner(),
                filteredItems -> {
                    mealsAdapter.updateData(filteredItems.getMeals());
                });

        back.setOnClickListener(Extensions::closeFragment);
    }
}