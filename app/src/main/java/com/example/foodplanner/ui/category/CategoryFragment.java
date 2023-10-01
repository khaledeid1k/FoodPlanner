package com.example.foodplanner.ui.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.home.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    CategoryPresenter categoryPresenter;
    ArrayList<CategoryWithDetails> categoryWithDetails;
    RecyclerView recycleCategory;
    CategoryAdapter categoryAdapter;

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
        categoryWithDetails=new ArrayList<>();
        categoryPresenter = new CategoryPresenter(
                RepositoryIm.getInstance(NetWork.getInstance()));
        categoryAdapter=new CategoryAdapter(getContext(),categoryWithDetails);
        recycleCategory.setAdapter(categoryAdapter);

    }
    void setUp(){
        categoryPresenter.categoriesWithDetails().observe(getActivity(), new Observer<List<CategoryWithDetails>>() {
            @Override
            public void onChanged(List<CategoryWithDetails> categoryWithDetailsList) {
             categoryAdapter.updateData(new ArrayList<>(categoryWithDetailsList));
            }
        });
    }
}