package com.example.foodplanner.ui.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.foodplanner.utils.Constants;
import com.example.foodplanner.utils.Extensions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;


public class MealFragment extends Fragment {

    ImageView imageSingleMeal,backFromMeal;
    CheckBox favouriteIconSingleMeal;
    TextView nameSingleMeal, categorySingleMeal,tileOfMeal;
    RecyclerView recyclerViewSingleMeal;
    Button buttonIngredientSingleMeal,buttonVideoSingleMeal,buttonInstructionsSingleMeal;


    IngredientAdapter ingredientAdapter;
    InstructionsAdapter instructionsAdapter;
    ArrayList<IngredientMeasurePair> ingredientMeasurePairs;
    ArrayList<Instructions> instructionsArrayList;
    Meal meal;
    MealPresenter mealPresenter;
    YouTubePlayerView youTubePlayerView;

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
        moveBetweenIngredientAndInstructionsAndVideo();
        showVideo();
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
        buttonVideoSingleMeal = view.findViewById(R.id.button_video_single_meal);
        youTubePlayerView = view.findViewById(R.id.video_layout);

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
            if (!Constants.isLogin) {
                favouriteIconSingleMeal.setChecked(false);
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainerView);

                NavController controller = navHostFragment.getNavController();
                Extensions.showRequestLogDialog(controller, requireContext());
            } else {
                favouriteIconSingleMeal.setEnabled(true);
                if (favouriteIconSingleMeal.isChecked()) {
                    mealPresenter.isFavouriteClicked.setValue(true);
                } else {
                    mealPresenter.isFavouriteClicked.setValue(false);
                }
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


    void moveBetweenIngredientAndInstructionsAndVideo() {
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
        buttonVideoSingleMeal.setOnClickListener(view -> {


        });

    }

    void showVideo(){
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

