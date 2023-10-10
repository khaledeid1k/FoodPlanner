package com.example.foodplanner.ui.favourite;

import static com.example.foodplanner.utils.Constants.Favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;

import java.util.ArrayList;


public class FavouriteFragment extends BaseFragment implements FavouriteView {
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView;
    ArrayList<Meal> favourites;
    FavouritePresenter favouritePresenter;
    LottieAnimationView lottieAnimationNoFavourites;
    TextView noFavorites;

    @Override
    protected int getLayout() {
        return R.layout.fragment_favourite;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view){
        recyclerView=view.findViewById(R.id.recycle_favourite);
        lottieAnimationNoFavourites =view.findViewById(R.id.lottie_animation_favourite);
        noFavorites=view.findViewById(R.id.no_favorite_Text);

        favourites =new ArrayList<>();
        favouritePresenter=new FavouritePresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(getActivity())),this);
        favouriteAdapter=new FavouriteAdapter(favourites,getActivity(),favouritePresenter,Favorite);
        recyclerView.setAdapter(favouriteAdapter);
    }
    void setUp(){
        favouritePresenter.getFavoritesMeals().observe(getViewLifecycleOwner(), meals -> {
            favouriteAdapter.updateData(new ArrayList<>(meals));
         if(meals.size()!=0){
             lottieAnimationNoFavourites.setVisibility(View.INVISIBLE);
             noFavorites.setVisibility(View.INVISIBLE);
         }else {
             lottieAnimationNoFavourites.setVisibility(View.VISIBLE);
             noFavorites.setVisibility(View.VISIBLE);
         }

        });

    }

    @Override
    public void deleteItem() {
        favouriteAdapter.updateData(favourites);
    }
}