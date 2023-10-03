package com.example.foodplanner.ui.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.foodplanner.utils.Extensions;

import java.util.ArrayList;


public class MealFragment extends Fragment {

    ImageView imageSingleMeal,backFromMeal;
    CheckBox favouriteIconSingleMeal;
    TextView nameSingleMeal, categorySingleMeal,tileOfMeal;
    RecyclerView recyclerViewSingleMeal;
    Button buttonIngredientSingleMeal;
    Button buttonInstructionsSingleMeal;

    IngredientAdapter ingredientAdapter;
    InstructionsAdapter instructionsAdapter;
    ArrayList<IngredientMeasurePair> ingredientMeasurePairs;
    ArrayList<Instructions> instructionsArrayList;
    Meal meal;
    MealPresenter mealPresenter;
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
        moveBetweenIngredientAndInstructions();
    }


    void inti(View view) {
        recyclerViewSingleMeal = view.findViewById(R.id.recyclerview_single_meal);
        nameSingleMeal = view.findViewById(R.id.name_single_meal);
        tileOfMeal = view.findViewById(R.id.tile_of_meal);
        categorySingleMeal = view.findViewById(R.id.category_single_meal);
        imageSingleMeal = view.findViewById(R.id.image_single_meal);
        backFromMeal = view.findViewById(R.id.back_from_meal);
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
        Glide.with(requireActivity()).load(meal.getStrMealThumb()).error(
                R.drawable.no_result_search
        ).into(imageSingleMeal);

        nameSingleMeal.setText(meal.getStrMeal());
        tileOfMeal.setText(meal.getStrMeal());
        backFromMeal.setOnClickListener(Extensions::closeFragment);
        categorySingleMeal.setText(meal.getStrCategory());

       favouriteIconSingleMeal.setOnClickListener(view -> {
           if (favouriteIconSingleMeal.isChecked()) {
              mealPresenter.isFavouriteClicked.setValue(true);
           } else {
               mealPresenter.isFavouriteClicked.setValue(false);
           }

       });
        mealPresenter.checkMealInFavoriteOrNot(meal.getIdMeal()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                favouriteIconSingleMeal.setChecked(aBoolean);
            }
        });
        recyclerViewSingleMeal.setAdapter(ingredientAdapter);
    }


    void moveBetweenIngredientAndInstructions() {
        buttonIngredientSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setAdapter(ingredientAdapter);

        });
        buttonInstructionsSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setAdapter(instructionsAdapter);

        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if(favouriteIconSingleMeal.isChecked()){
            mealPresenter.saveToFavorite(meal);
        }else {
            mealPresenter.deleteFromFavorite(meal);
        }
    }

}

