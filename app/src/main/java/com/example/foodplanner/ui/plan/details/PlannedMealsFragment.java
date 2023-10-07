package com.example.foodplanner.ui.plan.details;

import static com.example.foodplanner.utils.Constants.Favorite;
import static com.example.foodplanner.utils.Constants.Planed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.favourite.FavouriteAdapter;
import com.example.foodplanner.ui.favourite.FavouritePresenter;
import com.example.foodplanner.utils.Constants;
import com.example.foodplanner.utils.Extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PlannedMealsFragment extends Fragment implements PlannedMealView{
    FavouriteAdapter planeAdapter;
    RecyclerView recyclerView;
    ArrayList<Meal> meals;
    PlannedMealsPresenter plannedMealsPresenter;
    LottieAnimationView lottieAnimation;
    TextView noPlanedMeals;
    TextView tileOfListPlannedMeals;
    ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planned_meals, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PlannedMealsFragmentArgs plannedMealsFragmentArgs = PlannedMealsFragmentArgs.fromBundle(getArguments());
        String timeOfMeal = plannedMealsFragmentArgs.getTimeOfMeal();
        String day = plannedMealsFragmentArgs.getDay();
        init(view,day,timeOfMeal);
        setUp();
    }

    void init(View view,String day, String timeOfMeal){
        recyclerView=view.findViewById(R.id.recycle_planed_meals);
        lottieAnimation=view.findViewById(R.id.lottie_animation_planned);
        noPlanedMeals=view.findViewById(R.id.no_plan_Text);
        tileOfListPlannedMeals=view.findViewById(R.id.tile_of_list_planned_meals);
        back=view.findViewById(R.id.back_from_plan_details);

        meals=new ArrayList<>();
        plannedMealsPresenter=new PlannedMealsPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(getActivity())),this,
                day,timeOfMeal );
        planeAdapter=new FavouriteAdapter(meals,getActivity(),plannedMealsPresenter, Planed);
        recyclerView.setAdapter(planeAdapter);
    }
    void setUp(){
        plannedMealsPresenter.getPlanedMeals().observe(getViewLifecycleOwner(),
                planedMeal -> {

            List<Meal> mealsList = planedMeal.stream()
                    .map(plane -> plane.meal)
                    .collect(Collectors.toList());
            planeAdapter.updateData(new ArrayList<>(mealsList));
            Log.i("ssssssss", "setUp: "+planedMeal.size());
            if(mealsList.size()!=0){
                String s = planedMeal.get(0).getTimeOfMeal() +
                        " Of " +
                        planedMeal.get(0).getDay();
                tileOfListPlannedMeals.setText(
                        s
                );
                lottieAnimation.setVisibility(View.INVISIBLE);
                noPlanedMeals.setVisibility(View.INVISIBLE);
            }else {
                lottieAnimation.setVisibility(View.VISIBLE);
                noPlanedMeals.setVisibility(View.VISIBLE);
            }

        });

        back.setOnClickListener(Extensions::closeFragment);
    }

    @Override
    public void deleteItem() {
        planeAdapter.updateData(meals);

    }
}