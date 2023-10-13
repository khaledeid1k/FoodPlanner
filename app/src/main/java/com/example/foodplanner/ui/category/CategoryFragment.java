package com.example.foodplanner.ui.category;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment implements OnClickCategory,CategoryView{

    CategoryPresenter categoryPresenter;
    ArrayList<CategoryWithDetails> categories;
    RecyclerView recycleOfCategories;
    CategoryAdapter categoryAdapter;
    LottieAnimationView lottieAnimation;

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
        lottieAnimation=view.findViewById(R.id.lottie_animation_category);
        categories =new ArrayList<>();
        categoryPresenter = new CategoryPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(getActivity())),this);
        categoryAdapter=new CategoryAdapter(getContext(), categories,this);
        recycleOfCategories.setAdapter(categoryAdapter);

    }
    @Override
    public void ClickCategory(String nameOfCategory, View view) {
        CategoryFragmentDirections.ActionCategoryFragmentToMealsFragment action=
                CategoryFragmentDirections.actionCategoryFragmentToMealsFragment(nameOfCategory);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void getAllCategoriesWithDetails(List<CategoryWithDetails> categoriesWithDetails) {
        categoryAdapter.updateData(new ArrayList<>(categoriesWithDetails));
        lottieAnimation.setVisibility(View.INVISIBLE);
    }
}