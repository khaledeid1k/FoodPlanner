package com.example.foodplanner.ui.showAll;

import android.view.View;

import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.ui.home.adapter.CategoriesItem;
import com.example.foodplanner.ui.home.adapter.CountriesItem;
import com.example.foodplanner.ui.home.adapter.MealsItem;
import com.example.foodplanner.ui.home.adapter.OnClickHomeHorizontalItem;
import com.example.foodplanner.utils.Constants;


public class ShowAllPresenter implements OnClickHomeHorizontalItem {

    public ShowAllPresenter() {
    }


    @Override
    public void clickHomeItem(DataItem dataItem, int position, View view) {
        if (dataItem instanceof MealsItem) {
            ShowAllFragmentDirections.ActionShowAllFragmentToMealFragment action =
                    ShowAllFragmentDirections.actionShowAllFragmentToMealFragment(((MealsItem)dataItem).getTag().getResourcesData().get(position));
            Navigation.findNavController(view).navigate(
                    action
            );
        }
        else if (dataItem instanceof CategoriesItem) {
            ShowAllFragmentDirections.ActionShowAllFragmentToMealsFragment action =
                    ShowAllFragmentDirections.actionShowAllFragmentToMealsFragment(((CategoriesItem)dataItem).getTag().getResourcesData().get(position).getStrCategory()+ Constants.CATEGORY);
            Navigation.findNavController(view).navigate(
                    action
            );
        }
        else if (dataItem instanceof CountriesItem) {
         ShowAllFragmentDirections.ActionShowAllFragmentToMealsFragment action =
                 ShowAllFragmentDirections.actionShowAllFragmentToMealsFragment(((CountriesItem)dataItem).getTag().getResourcesData().get(position).getStrArea()+Constants.COUNTRY);
            Navigation.findNavController(view).navigate(
                    action
            );
        }
    }
}
