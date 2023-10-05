package com.example.foodplanner.ui.favourite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.meals.OnClickListener;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment implements FavouriteView {
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView;
    ArrayList<Meal> meals;
    FavouritePresenter favouritePresenter;
    LottieAnimationView lottieAnimation;
    TextView noFavorites;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view){
        recyclerView=view.findViewById(R.id.recycle_favourite);
        lottieAnimation=view.findViewById(R.id.lottie_animation_favourite);
        noFavorites=view.findViewById(R.id.no_favorite_Text);

        meals=new ArrayList<>();
        favouritePresenter=new FavouritePresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(getActivity())),this);
        favouriteAdapter=new FavouriteAdapter(meals,getActivity(),favouritePresenter);
        recyclerView.setAdapter(favouriteAdapter);
    }
    void setUp(){
        favouritePresenter.getFavoritesMeals().observe(getViewLifecycleOwner(), meals -> {
            favouriteAdapter.updateData(new ArrayList<>(meals));
         if(meals.size()!=0){
             lottieAnimation.setVisibility(View.INVISIBLE);
             noFavorites.setVisibility(View.INVISIBLE);

         }

        });

    }

    @Override
    public void deleteItem() {
        favouriteAdapter.updateData(meals);
    }
}