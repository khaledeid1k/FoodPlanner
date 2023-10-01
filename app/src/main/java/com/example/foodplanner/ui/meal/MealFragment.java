package com.example.foodplanner.ui.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.foodplanner.data.models.meal.IngredientMeasurePair;
import com.example.foodplanner.data.models.meal.Instructions;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.ui.meal.dapter.IngredientAdapter;
import com.example.foodplanner.ui.meal.dapter.InstructionsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MealFragment extends Fragment {

    ImageView imageSingleMeal, favouriteIconSingleMeal;
    TextView nameSingleMeal, categorySingleMeal;
    RecyclerView recyclerViewSingleMeal;
    Button buttonIngredientSingleMeal;
    Button buttonInstructionsSingleMeal;
    IngredientAdapter ingredientAdapter;
    InstructionsAdapter instructionsAdapter;
    Meal meal;
    ArrayList<IngredientMeasurePair> ingredientMeasurePairs;
    ArrayList<Instructions> instructionsArrayList;

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
        addIngredientAndMeasure();
        setUp();
        addInstructions();

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
        ingredientMeasurePairs = new ArrayList<>();
        instructionsArrayList = new ArrayList<>();
        meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();
        buttonIngredientSingleMeal.performClick();

    }

    void setUp() {
        Glide.with(getActivity()).load(meal.getStrMealThumb()).error(
                R.drawable.no_result_search
        ).into(imageSingleMeal);
        nameSingleMeal.setText(meal.getStrMeal());
        categorySingleMeal.setText(meal.getStrCategory());

        ingredientAdapter = new IngredientAdapter(getActivity(),
                ingredientMeasurePairs);

        instructionsAdapter = new InstructionsAdapter(getActivity(),
                instructionsArrayList);

        recyclerViewSingleMeal.setAdapter(ingredientAdapter);
    }

    void addIngredientAndMeasure() {
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient1(), meal.getStrMeasure1()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient2(), meal.getStrMeasure2()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient2(), meal.getStrMeasure3()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient4(), meal.getStrMeasure4()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient5(), meal.getStrMeasure5()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient6(), meal.getStrMeasure6()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient7(), meal.getStrMeasure7()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient8(), meal.getStrMeasure8()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient9(), meal.getStrMeasure9()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient10(), meal.getStrMeasure10()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient11(), meal.getStrMeasure11()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient12(), meal.getStrMeasure12()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient13(), meal.getStrMeasure13()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient14(), meal.getStrMeasure14()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient15(), meal.getStrMeasure15()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient16(), meal.getStrMeasure16()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient17(), meal.getStrMeasure17()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient18(), meal.getStrMeasure18()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient19(), meal.getStrMeasure19()));
        ingredientMeasurePairs.add(new IngredientMeasurePair(meal.getStrIngredient20(), meal.getStrMeasure20()));

        List<IngredientMeasurePair> collect = ingredientMeasurePairs.stream().filter(
                i -> i.getIngredient() != null && !i.getIngredient().trim().isEmpty()
        ).collect(Collectors.toList());
        ingredientMeasurePairs = (ArrayList<IngredientMeasurePair>) collect;


    }

    void addCallBacks() {
        buttonIngredientSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setAdapter(ingredientAdapter);

        });
        buttonInstructionsSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setAdapter(instructionsAdapter);

        });

    }

    void addInstructions() {
        String[] split = meal.getStrInstructions().split("\\.");
        int stepNumber = 1;
        for (String s : split) {
            String trimmedInstruction = s.trim();
            if (!trimmedInstruction.isEmpty()) {
                instructionsArrayList.add(new Instructions(stepNumber + "-", trimmedInstruction));
                stepNumber++;
            }
        }
    }


}

