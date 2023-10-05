package com.example.foodplanner.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.network.NetWork;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.meals.MealsAdapter;
import com.example.foodplanner.utils.Constants;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SearchFragment extends Fragment {
    String TAG = "SearchFragment";
    ChipGroup chipGroup;
    MaterialAutoCompleteTextView searchText;
    Chip chip1, chip2, chip3;
    SearchPresenter searchPresenter;
    RecyclerView recyclerViewOfSearch;
    ArrayList<FilteredItem> filteredItemArrayList;
    MealsAdapter mealsAdapter;
    LottieAnimationView lottieAnimation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();
    }

    void init(View view) {
        chipGroup = view.findViewById(R.id.chipGroup);
        chip1 = view.findViewById(R.id.category_search);
        chip2 = view.findViewById(R.id.country_search);
        chip3 = view.findViewById(R.id.ingredients_search);
        searchText = view.findViewById(R.id.search_text_value);
        recyclerViewOfSearch = view.findViewById(R.id.recycler_view_search);
        lottieAnimation = view.findViewById(R.id.lottie_animation_search);

        searchPresenter = new SearchPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(getActivity())));

        filteredItemArrayList=new ArrayList<>();

        mealsAdapter = new MealsAdapter(filteredItemArrayList, requireActivity(),
                searchPresenter
        );


        recyclerViewOfSearch.setAdapter(mealsAdapter);

    }


    void setUp() {
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = requireActivity().findViewById(checkedId);
            if (selectedChip != null) {
                String selectedWord = selectedChip.getText().toString();
                searchPresenter.getTextOfSelectedChip(selectedWord);
            } else {
                searchPresenter.getTextOfSelectedChip(Constants.Meal);
            }

        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0) {
                    searchPresenter.getTextOfSearch(charSequence.toString());
                }else {
                    searchPresenter.getTextOfSearch(Constants.Empty);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchPresenter.filteredItemsLiveData().observe(getViewLifecycleOwner(),
                filteredItems -> {
                    Log.i(TAG, "afdsdfsdf: "+filteredItems);
                    ArrayList<FilteredItem> collect =
                            (ArrayList<FilteredItem>) filteredItems.stream().distinct()
                                    .sorted(Comparator.comparing(FilteredItem::getStrMeal))
                                    .collect(Collectors.toList());

                    if(collect.size()!=0){
                        lottieAnimation.setVisibility(View.INVISIBLE);
                    }else {
                        lottieAnimation.setVisibility(View.VISIBLE);
                    }

                    mealsAdapter.updateData(collect);
                });

    }
}