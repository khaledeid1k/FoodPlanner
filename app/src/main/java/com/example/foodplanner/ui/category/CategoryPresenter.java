package com.example.foodplanner.ui.category;

import static com.example.foodplanner.utils.Extensions.updateUIState;

import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.repository.Repository;
import com.example.foodplanner.ui.base.BasePresenter;
import com.example.foodplanner.ui.base.BasePresenterView;

public class CategoryPresenter extends BasePresenter implements
        BasePresenterView<CategoriesWithDetails>,CategoryPresenterView {
    Repository repository;
    CategoryView categoryView;

    public CategoryPresenter(Repository repository,CategoryView categoryView) {
        this.repository = repository;
        this.categoryView = categoryView;
    }

    @Override
    public void getAllCategories() {
        applySchedulersAndPostUIStates( repository.getAllCategoriesWithDetails(),
                this);
    }


    @Override
    public void showLoading() {
        categoryView.showLoading();
    }

    @Override
    public void showData(CategoriesWithDetails data) {
            categoryView.getAllCategoriesWithDetails(data.getCategories());
    }

    @Override
    public void showError(String errorMessage) {
        categoryView.showError(errorMessage);
    }
}

