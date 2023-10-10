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

public class CategoryPresenter implements OnClickCategory{
    Repository repository;

    public CategoryPresenter(Repository repository) {
        this.repository = repository;
        getAllCategoriesWithDetails();
    }

    private final MutableLiveData<List<CategoryWithDetails>> allCategoriesWithDetails = new MutableLiveData<>();

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

    @Override
    public void ClickCategory(String nameOfCategory, View view) {
        CategoryFragmentDirections.ActionCategoryFragmentToMealsFragment action=
                CategoryFragmentDirections.actionCategoryFragmentToMealsFragment(nameOfCategory);
        Navigation.findNavController(view).navigate(action);
    }
}
