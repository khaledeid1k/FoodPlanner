package com.example.foodplanner.ui.favourite;

import static com.example.foodplanner.utils.Constants.Favorite;
import static com.example.foodplanner.utils.Extensions.intiStateAnimation;
import static com.example.foodplanner.utils.Extensions.updateUIState;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.RemoteSourceIm;
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
    AppCompatButton retryButton;
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
        intiStateAnimation(view);
        retryButton = view.findViewById(R.id.retry);
        recyclerView = view.findViewById(R.id.recycle_favourite);
        lottieAnimationNoFavourites = view.findViewById(R.id.lottie_animation_favourite);
        noFavorites = view.findViewById(R.id.no_favorite_Text);
        favourites = new ArrayList<>();
        favouritePresenter = new FavouritePresenter(RepositoryIm.getInstance(RemoteSourceIm.getInstance(), LocalSourceIm.getInstance(getActivity())), this);
        favouriteAdapter = new FavouriteAdapter(favourites, getActivity(), this, Favorite);
        favoritePresenterView = favouritePresenter;
        recyclerView.setAdapter(favouriteAdapter);
        favoritePresenterView.getFavoritesMeals();
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
    public void getFavoritesMeals(List<Meal> meals) {
        favouriteAdapter.updateData(new ArrayList<>(meals));
        updateUIState(false, false);
        if (meals.size() != 0) {
            lottieAnimationNoFavourites.setVisibility(View.INVISIBLE);
            noFavorites.setVisibility(View.INVISIBLE);
        } else {
            lottieAnimationNoFavourites.setVisibility(View.VISIBLE);
            noFavorites.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void showLoading() {
        updateUIState(true, false);
    }

    @Override
    public void showError(String errorMessage) {
        updateUIState(false, true);
        retryButton.setOnClickListener(view -> {
            favoritePresenterView.getFavoritesMeals();
        });
    }
}