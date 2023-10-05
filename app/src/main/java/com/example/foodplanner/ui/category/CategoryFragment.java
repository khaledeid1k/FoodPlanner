package com.example.foodplanner.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    CategoryPresenter categoryPresenter;
    ArrayList<CategoryWithDetails> categoryWithDetails;
    RecyclerView recycleCategory;
    CategoryAdapter categoryAdapter;
    LottieAnimationView lottieAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inti(view);
        setUp();
    }
    void  inti(View view){
        recycleCategory=view.findViewById(R.id.recycle_category);
        lottieAnimation=view.findViewById(R.id.lottie_animation_category);
        categoryWithDetails=new ArrayList<>();
        categoryPresenter = new CategoryPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(), LocalSourceIm.getInstance(getActivity())));
        categoryAdapter=new CategoryAdapter(getContext(),categoryWithDetails,categoryPresenter);
        recycleCategory.setAdapter(categoryAdapter);

    }
    void setUp(){
        categoryPresenter.categoriesWithDetails().observe(getViewLifecycleOwner(),
                categoryWithDetailsList -> {
                    categoryAdapter.updateData(new ArrayList<>(categoryWithDetailsList));
                    lottieAnimation.setVisibility(View.INVISIBLE);
                }
        );
    }
}