package com.example.foodplanner.ui.home;

import static com.example.foodplanner.utils.Extensions.intiStateAnimation;
import static com.example.foodplanner.utils.Extensions.updateUIState;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.RemoteSourceIm;
import com.example.foodplanner.data.network.auth.ILogOut;
import com.example.foodplanner.data.network.auth.Logout;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.ui.home.adapter.CategoriesItem;
import com.example.foodplanner.ui.home.adapter.CountriesItem;
import com.example.foodplanner.ui.home.adapter.HeaderItem;
import com.example.foodplanner.ui.home.adapter.HomeAdapter;
import com.example.foodplanner.ui.home.adapter.HomeInteractionListener;
import com.example.foodplanner.ui.home.adapter.MealsItem;
import com.example.foodplanner.ui.home.adapter.OnClickHomeHorizontalItem;
import com.example.foodplanner.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeInteractionListener,
        OnClickHomeHorizontalItem, HomeFragmentView {

    RecyclerView recyclerView;
    HomePresenter presenter;
    HomePresenterView homePresenterView;
    HomeAdapter homeAdapter;
    ArrayList<DataItem> items;
    ILogOut ILogOut;
    AppCompatButton retryButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
    }

    void inti(View view) {
        recyclerView = view.findViewById(R.id.recycle_home);
        intiStateAnimation(view);
        retryButton=view.findViewById(R.id.retry);
        presenter = new HomePresenter(
                RepositoryIm.getInstance(RemoteSourceIm.getInstance(),
                        LocalSourceIm.getInstance(requireActivity())), this);
        items = new ArrayList<>();
        homeAdapter = new HomeAdapter(
                requireActivity(),
                items,
                this,
                this);
        recyclerView.setAdapter(homeAdapter);
        ILogOut = new Logout();
        homePresenterView=presenter;
        homePresenterView.getRandomMeal();
        homePresenterView.getAllCountries();
        homePresenterView.getMealsByFirstLetter();
        homePresenterView.getAllCategoriesWithDetails();



    }


    @Override
    public void navigateToShowAll(DataItem dataItem, View view) {
        if (dataItem instanceof MealsItem) {
            HomeFragmentDirections.ActionHomeFragmentToShowAllFragment action =
                    HomeFragmentDirections.actionHomeFragmentToShowAllFragment(dataItem);
            Navigation.findNavController(view).navigate(
                    action
            );
        } else if (dataItem instanceof CategoriesItem) {
            HomeFragmentDirections.ActionHomeFragmentToShowAllFragment action =
                    HomeFragmentDirections.actionHomeFragmentToShowAllFragment(dataItem);
            Navigation.findNavController(view).navigate(
                    action
            );
        } else if (dataItem instanceof CountriesItem) {
            HomeFragmentDirections.ActionHomeFragmentToShowAllFragment action =
                    HomeFragmentDirections.actionHomeFragmentToShowAllFragment(dataItem);
            Navigation.findNavController(view).navigate(
                    action
            );
        } else if (dataItem instanceof HeaderItem) {
            navigateToDetailsOfRandomMeal(dataItem, view);
        }
    }
    void navigateToDetailsOfRandomMeal(DataItem dataItem, View view) {
        HomeFragmentDirections.ActionHomeFragmentToMealFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealFragment(((HeaderItem) dataItem).getTag().getResourcesData());
        Navigation.findNavController(view).navigate(
                action
        );
    }
    @Override
    public void navigateToDetails(DataItem dataItem, int position, View view) {
        if (dataItem instanceof MealsItem) {
            HomeFragmentDirections.ActionHomeFragmentToMealFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealFragment(((MealsItem) dataItem).getTag().getResourcesData().get(0));
            Navigation.findNavController(view).navigate(
                    action
            );
        } else if (dataItem instanceof CategoriesItem) {
            HomeFragmentDirections.ActionHomeFragmentToMealsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealsFragment(((CategoriesItem) dataItem).getTag().getResourcesData().get(position).getStrCategory() + Constants.CATEGORY);
            Navigation.findNavController(view).navigate(
                    action
            );
        } else if (dataItem instanceof CountriesItem) {
            HomeFragmentDirections.ActionHomeFragmentToMealsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealsFragment(((CountriesItem) dataItem).getTag().getResourcesData().get(position).getStrArea() + Constants.COUNTRY);
            Navigation.findNavController(view).navigate(
                    action
            );
        }
    }

    @Override
    public void logout(View view) {
        ILogOut.logOut();
        Navigation.findNavController(view).navigate(R.id.action_homeF_to_loginFragment);
    }




    @Override
    public void getRandomMeal(Meal meal) {
        updateUIState(false,false);

        items.add(0, new HeaderItem(meal));
        homeAdapter.updateData(items);

    }

    @Override
    public void getMealsByFirstLetter(List<Meal> meals) {
        updateUIState(false,false);

        items.add(new MealsItem(new Tag<>("Meals", meals)));
        homeAdapter.updateData(items);

    }

    @Override
    public void getCategoriesWithDetails(List<CategoryWithDetails> categoriesWithDetailsList) {
        updateUIState(false,false);

        items.add(new CategoriesItem(new Tag<>("Categories", categoriesWithDetailsList)));
        homeAdapter.updateData(items);
    }

    @Override
    public void getAllCountries(List<Country> countries) {
        updateUIState(false,false);

        items.add(new CountriesItem(new Tag<>("Countries", countries)));
        homeAdapter.updateData(items);

    }

    @Override
    public void showLoading() {
        updateUIState(true,false);
    }

    @Override
    public void showError(String errorMessage) {
        updateUIState(false,true);
        retryButton.setOnClickListener(view -> {
            homePresenterView.getRandomMeal();
            homePresenterView.getAllCountries();
            homePresenterView.getMealsByFirstLetter();
            homePresenterView.getAllCategoriesWithDetails();

        });

    }
}







