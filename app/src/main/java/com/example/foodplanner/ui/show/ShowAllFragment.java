package com.example.foodplanner.ui.show;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.ui.home.adapter.ItemsAdapter;
import com.example.foodplanner.ui.home.adapter.MealsItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.utils.Constants;

import java.util.List;


public class ShowAllFragment extends Fragment {
TextView tileOfShowAll;
RecyclerView recyclerViewShowAll;
    ItemsAdapter itemsAdapter;
    DataItem dataItem;
    ShowAllPresenter showAllPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUp(view);
         dataItem = ShowAllFragmentArgs.fromBundle(getArguments()).getDataitem();

        if(dataItem instanceof MealsItem){
            Tag<List<Meal>> tag = ((MealsItem) dataItem).getTag();
            Toast.makeText(getActivity(),    tag.getTitle(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(),tag.getResourcesData().get(0).getStrArea(), Toast.LENGTH_SHORT).show();
            itemsAdapter =new ItemsAdapter(getActivity(),dataItem,showAllPresenter,
                    Constants.VIEW_TYPE_GRID);
            recyclerViewShowAll.setAdapter(itemsAdapter);

        }
        tileOfShowAll.setText(dataItem.getTag().getTitle());


    }
    void setUp(View view){
        tileOfShowAll=view.findViewById(R.id.tile_of_list_of_show_all);
        recyclerViewShowAll=view.findViewById(R.id.recycle_of_show_aLl);
        showAllPresenter=new ShowAllPresenter();

    }
}