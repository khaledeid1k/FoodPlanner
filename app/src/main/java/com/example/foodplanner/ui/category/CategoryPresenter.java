package com.example.foodplanner.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.network.StateOfResponse;
import com.example.foodplanner.data.repository.Repository;

import java.util.List;

public class CategoryPresenter {
    Repository repository;

    public CategoryPresenter(Repository repository) {
        this.repository = repository;
        getAllCategoriesWithDetails();
    }

    private MutableLiveData<List<CategoryWithDetails>> allCategoriesWithDetails = new MutableLiveData<>();

    public LiveData<List<CategoryWithDetails>> categoriesWithDetails() {
        return allCategoriesWithDetails;
    }

    private void getAllCategoriesWithDetails() {
        repository.getAllCategoriesWithDetails(new StateOfResponse<>() {
            @Override
            public void succeeded(CategoriesWithDetails response) {
                allCategoriesWithDetails.setValue(response.getCategories());
            }

            @Override
            public void failure(String message) {

            }
        });
    }
}
