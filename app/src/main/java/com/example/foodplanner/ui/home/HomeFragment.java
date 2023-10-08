package com.example.foodplanner.ui.home;

import static com.example.foodplanner.utils.Extensions.moveToLoginScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.home.adapter.CategoriesItem;
import com.example.foodplanner.ui.home.adapter.CountriesItem;
import com.example.foodplanner.ui.home.adapter.HeaderItem;
import com.example.foodplanner.ui.home.adapter.HomeAdapter;
import com.example.foodplanner.ui.home.adapter.MealsItem;
import com.example.foodplanner.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeView {

    RecyclerView recyclerView;
    HomePresenter presenter;
    HomeAdapter homeAdapter;
    ArrayList<DataItem> items;
    LottieAnimationView lottieAnimation;
    private static final String TAG = "HomeFragmentlollllllllll";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        addRandomMeal();
        addMealsByFirstLetter();
        addCategoriesWithDetails();
        addAllCountries();

    }

    void inti(View view) {
        recyclerView = view.findViewById(R.id.recycle_home);
        lottieAnimation = view.findViewById(R.id.lottie_animation_home);
        presenter = new HomePresenter(
                RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(requireActivity())),this);
        items = new ArrayList<>();
        homeAdapter = new HomeAdapter(requireActivity(), items, presenter, presenter);
        recyclerView.setAdapter(homeAdapter);
    }

    void addRandomMeal() {
        presenter.randomMealLiveData().observe(getViewLifecycleOwner(), meal -> {
            lottieAnimation.setVisibility(View.INVISIBLE);
            items.add(0,new HeaderItem(meal));
            homeAdapter.updateData(items);
        });

    }

    void addMealsByFirstLetter() {
        presenter.mealsByFirstLetter().observe(getViewLifecycleOwner(), meals -> {
            items.add(new MealsItem(new Tag<>("Meals", meals)));
            lottieAnimation.setVisibility(View.INVISIBLE);

            homeAdapter.updateData(items);
        });
    }

    void addCategoriesWithDetails() {
        presenter.categoriesWithDetails().observe(getViewLifecycleOwner(), categoryWithDetailsList -> {
            items.add(new CategoriesItem(new Tag<>("Categories", categoryWithDetailsList)));
            lottieAnimation.setVisibility(View.INVISIBLE);

            homeAdapter.updateData(items);
        });
    }

    void addAllCountries() {

        presenter.allCountries().observe(getViewLifecycleOwner(), countryList -> {
            items.add(new CountriesItem(new Tag<>("Countries", countryList)));
            lottieAnimation.setVisibility(View.INVISIBLE);

            homeAdapter.updateData(items);
        });


    }


    @Override
    public void logout() {
        Constants.UserId="";
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {

                moveToLoginScreen( navHostFragment.getNavController());
                Log.i(TAG, "logout: ");
            } else {
                Log.i(TAG, "Error in logout: ");

            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            moveToLoginScreen( navHostFragment.getNavController());
            Log.d(TAG, "Sign out was successful");
        } else {
            Log.e(TAG, "Sign out failed");
        }
    }
}







