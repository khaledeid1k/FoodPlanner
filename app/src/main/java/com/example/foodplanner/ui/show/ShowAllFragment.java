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
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.ui.home.adapter.CategoriesItem;
import com.example.foodplanner.ui.home.adapter.CountriesItem;
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
        showData();

    }

    void setUp(View view) {
        tileOfShowAll = view.findViewById(R.id.tile_of_list_of_show_all);
        recyclerViewShowAll = view.findViewById(R.id.recycle_of_show_aLl);
        showAllPresenter = new ShowAllPresenter();

    }

    void showData() {
        if (dataItem instanceof MealsItem) {
            Tag<List<Meal>> tag = ((MealsItem) dataItem).getTag();
            itemsAdapter = new ItemsAdapter(
                    getActivity(),
                    dataItem,
                    showAllPresenter,
                    Constants.VIEW_TYPE_GRID);

            recyclerViewShowAll.setAdapter(itemsAdapter);
            tileOfShowAll.setText(tag.getTitle());
        } else if (dataItem instanceof CategoriesItem) {
            Tag<List<CategoryWithDetails>> tag = ((CategoriesItem) dataItem).getTag();
            itemsAdapter = new ItemsAdapter(
                    getActivity(),
                    dataItem,
                    showAllPresenter,
                    Constants.VIEW_TYPE_GRID);

            recyclerViewShowAll.setAdapter(itemsAdapter);
            tileOfShowAll.setText(tag.getTitle());
        } else if (dataItem instanceof CountriesItem) {
            Tag<List<Country>> tag = ((CountriesItem) dataItem).getTag();
            itemsAdapter = new ItemsAdapter(
                    getActivity(),
                    dataItem,
                    showAllPresenter,
                    Constants.VIEW_TYPE_GRID);

            recyclerViewShowAll.setAdapter(itemsAdapter);
            tileOfShowAll.setText(tag.getTitle());
        }
    }
}