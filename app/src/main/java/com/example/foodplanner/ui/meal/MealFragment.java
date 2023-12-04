package com.example.foodplanner.ui.meal;

import static com.example.foodplanner.utils.Constants.UserId;
import static com.example.foodplanner.utils.Extensions.showRequestLoginDialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.Instructions;
import com.example.foodplanner.data.models.PlanedMeal;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.RemoteSourceIm;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.ui.meal.dapter.IngredientAdapter;
import com.example.foodplanner.ui.meal.dapter.InstructionsAdapter;
import com.example.foodplanner.utils.Constants;
import com.example.foodplanner.utils.Extensions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;


public class MealFragment extends BaseFragment implements MealView {

    ImageView imageSingleMeal, backFromMeal, addMealToPlan;
    CheckBox favouriteIconSingleMeal;
    TextView nameSingleMeal, countrySingleMeal, tileOfMeal;
    RecyclerView recyclerViewSingleMeal;
    Button buttonIngredientSingleMeal, buttonVideoSingleMeal, buttonInstructionsSingleMeal;
    YouTubePlayerView youTubePlayerView;


    MealPresenterView mealPresenterView;
    IngredientAdapter ingredientAdapter;
    InstructionsAdapter instructionsAdapter;
    Meal meal;
    MealPresenter mealPresenter;
    boolean isLogin;
    NavController controller;

    @Override
    protected int getLayout() {
        return R.layout.fragment_meal;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        setUp();
        moveBetweenIngredientAndInstructions();
        showVideo();
        showDaysOfWeekDialog();
    }


    void inti(View view) {
        recyclerViewSingleMeal = view.findViewById(R.id.recyclerview_single_meal);
        nameSingleMeal = view.findViewById(R.id.name_single_meal);
        tileOfMeal = view.findViewById(R.id.tile_of_meal);
        countrySingleMeal = view.findViewById(R.id.country_single_meal);
        imageSingleMeal = view.findViewById(R.id.image_single_meal);
        backFromMeal = view.findViewById(R.id.back_from_meal);
        favouriteIconSingleMeal = view.findViewById(R.id.favourite_icon_single_meal);
        buttonIngredientSingleMeal = view.findViewById(R.id.button_ingredient_single_meal);
        buttonInstructionsSingleMeal = view.findViewById(R.id.button_instructions_single_meal);
        buttonVideoSingleMeal = view.findViewById(R.id.button_video_single_meal);
        youTubePlayerView = view.findViewById(R.id.video_layout);
        addMealToPlan = view.findViewById(R.id.add_to_plan);

        buttonIngredientSingleMeal.performClick();
        meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();
        mealPresenter = new MealPresenter(meal,
                RepositoryIm.getInstance(
                        RemoteSourceIm.getInstance(),
                        LocalSourceIm.getInstance(getActivity())), this);
        mealPresenterView=mealPresenter;


    }

    void setUp() {
        Glide.with(requireActivity()).load(meal.getStrMealThumb()).error(R.drawable.no_result_search).into(imageSingleMeal);
        nameSingleMeal.setText(meal.getStrMeal());
        tileOfMeal.setText(meal.getStrMeal());
        backFromMeal.setOnClickListener(Extensions::closeFragment);
        countrySingleMeal.setText(meal.getStrArea());
        mealPresenterView.getIdMeal(meal.getIdMeal());

        recyclerViewSingleMeal.setAdapter(ingredientAdapter);

        isLogin = !Constants.UserId.equals("");
        addMealToFavorite();
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        controller = navHostFragment.getNavController();

    }



    void moveBetweenIngredientAndInstructions() {
        buttonIngredientSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.INVISIBLE);
            recyclerViewSingleMeal.setAdapter(ingredientAdapter);
        });
        buttonInstructionsSingleMeal.setOnClickListener(view -> {
            recyclerViewSingleMeal.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.INVISIBLE);
            recyclerViewSingleMeal.setAdapter(instructionsAdapter);
        });

    }

    void addMealToFavorite() {
        favouriteIconSingleMeal.setOnClickListener(view -> {
            if (isLogin) {
                favouriteIconSingleMeal.setEnabled(true);
            } else {
                favouriteIconSingleMeal.setChecked(false);
                showRequestLoginDialog(controller, requireContext());
            }
        });

    }

    void showVideo() {
        buttonVideoSingleMeal.setOnClickListener(view -> {
            String strYoutube = meal.getStrYoutube();
            String[] split = strYoutube.split("=");
            recyclerViewSingleMeal.setVisibility(View.INVISIBLE);
            youTubePlayerView.setVisibility(View.VISIBLE);
            getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = split[1];
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        });
    }

    void showDaysOfWeekDialog() {
        addMealToPlan.setOnClickListener(view -> {
            if (isLogin) {
                showWeekDialog();
            } else {
                showRequestLoginDialog(controller, requireContext());

            }

        });

    }

    private void showWeekDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.selected_planned_meal)
                .setItems(R.array.days_of_week, (dialog, which) -> {
                    String dayOfWeek = getResources().getStringArray(R.array.days_of_week)[which];
                    showDayDialog(dayOfWeek);
                }).create().show();
    }

    private void showDayDialog(String dayOfWeek) {
        new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.choose_type_of_meal)
                .setItems(R.array.meals_of_day, (dialog, which) -> {
                    String timeOfMeal = getResources().getStringArray(R.array.meals_of_day)[which];
                    if (!timeOfMeal.isEmpty()) {
                        saveToPlanedMeal(dayOfWeek, timeOfMeal);
                    }
                }).create().show();
    }

    void saveToPlanedMeal(String dayOfWeek, String timeOfMeal) {
        mealPresenterView.savePlanedMeal(new PlanedMeal(
                UserId, dayOfWeek, timeOfMeal, meal));
        Toast.makeText(requireActivity(), "Saved To Plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        stateOfMeal();
    }

    void stateOfMeal() {
        if (favouriteIconSingleMeal.isChecked()) {
            meal.setUserId(UserId);
            mealPresenterView.saveToFavorite(meal);
        } else {
            mealPresenterView.deleteFromFavorite(meal);
        }
    }

    @Override
    public void getIngredientAndMeasure(ArrayList<IngredientMeasurePair> ingredientMeasurePairs) {
        ingredientAdapter = new IngredientAdapter(getActivity(), ingredientMeasurePairs);


    }

    @Override
    public void getInstructions(ArrayList<Instructions> instructions) {
        instructionsAdapter = new InstructionsAdapter(getActivity(), instructions);

    }

    @Override
    public void getIsFavoriteMealById(Boolean isFavorite) {
                favouriteIconSingleMeal.setChecked(isFavorite);

    }


}

