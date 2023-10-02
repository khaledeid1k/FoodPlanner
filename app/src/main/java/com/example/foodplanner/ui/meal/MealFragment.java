package com.example.foodplanner.ui.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.meal.dapter.IngredientAdapter;
import com.example.foodplanner.ui.meal.dapter.InstructionsAdapter;

import java.util.ArrayList;


public class MealFragment extends Fragment {

    ImageView imageSingleMeal, favouriteIconSingleMeal;
    TextView nameSingleMeal, categorySingleMeal;
    RecyclerView recyclerViewSingleMeal;
    Button buttonIngredientSingleMeal;
    Button buttonInstructionsSingleMeal;
    IngredientAdapter ingredientAdapter;
    InstructionsAdapter instructionsAdapter;
    ArrayList<IngredientMeasurePair> ingredientMeasurePairs;
    ArrayList<Instructions> instructionsArrayList;
    Meal meal;
    MealPresenter mealPresenter;
    boolean flag=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        setUp();
        addCallBacks();
    }

    void inti(View view) {
        recyclerViewSingleMeal = view.findViewById(R.id.recyclerview_single_meal);
        nameSingleMeal = view.findViewById(R.id.name_single_meal);
        categorySingleMeal = view.findViewById(R.id.category_single_meal);
        imageSingleMeal = view.findViewById(R.id.image_single_meal);
        favouriteIconSingleMeal = view.findViewById(R.id.favourite_icon_single_meal);
        buttonIngredientSingleMeal = view.findViewById(R.id.button_ingredient_single_meal);
        buttonInstructionsSingleMeal = view.findViewById(R.id.button_instructions_single_meal);
        buttonIngredientSingleMeal.performClick();

        meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();
        instructionsArrayList = new ArrayList<>();
        ingredientMeasurePairs = new ArrayList<>();
        mealPresenter = new MealPresenter(meal, ingredientMeasurePairs,
                instructionsArrayList,
                RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(getActivity())));
        ingredientAdapter = new IngredientAdapter(getActivity(), ingredientMeasurePairs);
        instructionsAdapter = new InstructionsAdapter(getActivity(), instructionsArrayList);
    }

    void setUp() {
        Glide.with(getActivity()).load(meal.getStrMealThumb()).error(
                R.drawable.no_result_search
        ).into(imageSingleMeal);

        nameSingleMeal.setText(meal.getStrMeal());

        categorySingleMeal.setText(meal.getStrCategory());

       favouriteIconSingleMeal.setOnClickListener(view -> {
           if (flag){
               favouriteIconSingleMeal.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.favorite));
               flag=false;
               mealPresenter.deleteFromFavorite(meal);
           }else {
               favouriteIconSingleMeal.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.favorite_done));
               flag=true;
               mealPresenter.saveToFavorite(meal);
           }
       });

        recyclerViewSingleMeal.setAdapter(ingredientAdapter);
    }


    void addCallBacks() {
        buttonIngredientSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setAdapter(ingredientAdapter);

        });
        buttonInstructionsSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setAdapter(instructionsAdapter);

        });

    }

}

