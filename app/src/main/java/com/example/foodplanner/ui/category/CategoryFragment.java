package com.example.foodplanner.ui.category;

import static com.example.foodplanner.utils.Extensions.intiStateAnimation;
import static com.example.foodplanner.utils.Extensions.updateUIState;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.network.RemoteSourceIm;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment implements OnClickCategory,CategoryView{

    CategoryPresenter categoryPresenter;
    CategoryPresenterView categoryPresenterView;
    ArrayList<CategoryWithDetails> categories;
    RecyclerView recycleOfCategories;
    CategoryAdapter categoryAdapter;
    AppCompatButton retryButton;

    @Override
    protected int getLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
    }
    void  inti(View view){
        recycleOfCategories =view.findViewById(R.id.recycle_category);
        intiStateAnimation(view);

        retryButton=view.findViewById(R.id.retry);

        categories =new ArrayList<>();
        categoryPresenter = new CategoryPresenter(
                RepositoryIm.getInstance(RemoteSourceIm.getInstance(),
                        LocalSourceIm.getInstance(getActivity())),this);
        categoryPresenterView=categoryPresenter;
        categoryAdapter=new CategoryAdapter(getContext(), categories,this);
        recycleOfCategories.setAdapter(categoryAdapter);
        categoryPresenterView.getAllCategories();
    }
    @Override
    public void clickCategory(String nameOfCategory, View view) {
        CategoryFragmentDirections.ActionCategoryFragmentToMealsFragment action=
                CategoryFragmentDirections.actionCategoryFragmentToMealsFragment(nameOfCategory);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void getAllCategoriesWithDetails(ArrayList<CategoryWithDetails> categoriesWithDetails) {
        categoryAdapter.updateData(categoriesWithDetails);
        updateUIState(false,false);
    }

    @Override
    public void showLoading() {
        updateUIState(true,false);
    }

    @Override
    public void showError(String errorMessage) {
        categories.clear();
        updateUIState(false,true);
        retryButton.setOnClickListener(view -> categoryPresenterView.getAllCategories());

    }
}