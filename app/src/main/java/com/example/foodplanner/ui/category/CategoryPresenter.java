package com.example.foodplanner.ui.category;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;

import java.util.List;

public class CategoryPresenter {
    Repository repository;
    CategoryView categoryView;

    public CategoryPresenter(Repository repository,CategoryView categoryView) {
        this.repository = repository;
        this.categoryView = categoryView;
        getAllCategoriesWithDetails();
    }


    private void getAllCategoriesWithDetails() {
        repository.getAllCategoriesWithDetails(new StateOfResponse<>() {
            @Override
            public void succeeded(CategoriesWithDetails response) {
                categoryView.getAllCategoriesWithDetails(response.getCategories());
            }

            @Override
            public void failure(String message) {

            }
        });
    }


}
