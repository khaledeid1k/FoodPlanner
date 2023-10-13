package com.example.foodplanner.ui.favourite;

import static com.example.foodplanner.utils.Constants.Favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
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
import java.util.List;


public class FavouriteFragment extends BaseFragment implements OnClickFavorites, FavouriteFragmentView {
    FavouriteAdapter favouriteAdapter;
    RecyclerView recyclerView;
    ArrayList<Meal> favourites;
    FavouritePresenter favouritePresenter;
    LottieAnimationView lottieAnimationNoFavourites;
    TextView noFavorites;
    FavoritePresenterView favoritePresenterView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_favourite;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view) {
        recyclerView = view.findViewById(R.id.recycle_favourite);
        lottieAnimationNoFavourites = view.findViewById(R.id.lottie_animation_favourite);
        noFavorites = view.findViewById(R.id.no_favorite_Text);

        favourites = new ArrayList<>();
        favouritePresenter = new FavouritePresenter(RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(getActivity())), this);
        favouriteAdapter = new FavouriteAdapter(favourites, getActivity(), this, Favorite);
        favoritePresenterView = favouritePresenter;
        recyclerView.setAdapter(favouriteAdapter);
    }


    @Override
    public void onClickFavorite(Meal meal, View view) {
        FavouriteFragmentDirections.ActionFavouriteFragmentToMealFragment action = FavouriteFragmentDirections.actionFavouriteFragmentToMealFragment(meal);
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void deleteFavorite(Meal meal) {
        favoritePresenterView.deleteFavorite(meal);

    }

    @Override
    public void getFavoritesMeals(LiveData<List<Meal>> mealsLiveData) {
        mealsLiveData.observe(getViewLifecycleOwner(), meals -> {
            favouriteAdapter.updateData(new ArrayList<>(meals));
            if (meals.size() != 0) {
                lottieAnimationNoFavourites.setVisibility(View.INVISIBLE);
                noFavorites.setVisibility(View.INVISIBLE);
            } else {
                lottieAnimationNoFavourites.setVisibility(View.VISIBLE);
                noFavorites.setVisibility(View.VISIBLE);
            }

        });

    }
}