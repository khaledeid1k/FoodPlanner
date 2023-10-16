package com.example.foodplanner.ui.category;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.filter.FilteredItems;
import com.example.foodplanner.data.network.StateOfResponsee;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;
import com.example.foodplanner.ui.base.BasePresenterView;

public class CategoryPresenter extends BasePresenter implements BasePresenterView<CategoriesWithDetails> {
    Repository repository;
    CategoryView categoryView;

    public CategoryPresenter(Repository repository,CategoryView categoryView) {
        this.repository = repository;
        this.categoryView = categoryView;
        getAllCategoriesWithDetails();
    }


    private void getAllCategoriesWithDetails() {
        applySchedulersAndPostUIStates( repository.getAllCategoriesWithDetails(),
                this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showData(CategoriesWithDetails data) {
            categoryView.getAllCategoriesWithDetails(data.getCategories());
    }

    @Override
    public void showError(String errorMessage) {

    }
}

