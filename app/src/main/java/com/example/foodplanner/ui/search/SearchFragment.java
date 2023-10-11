package com.example.foodplanner.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.foodplanner.ui.base.BaseFragment;
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


public class SearchFragment extends BaseFragment {
    ChipGroup chipGroup;
    MaterialAutoCompleteTextView searchText;
    RecyclerView recyclerViewOfSearch;
    LottieAnimationView lottieAnimation;


    String wordOfSearch = Constants.Empty;
    String selectedChipText = Constants.Meal;
    SearchPresenter searchPresenter;
    MealsAdapter mealsAdapter;
    ArrayList<FilteredItem> filteredItemArrayList;
    SearchPresenterView searchPresenterView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setChipText();
        setWordOfSearch();
        observeFilterData();
    }

    void init(View view) {
        chipGroup = view.findViewById(R.id.chipGroup);
        searchText = view.findViewById(R.id.search_text_value);
        recyclerViewOfSearch = view.findViewById(R.id.recycler_view_search);
        lottieAnimation = view.findViewById(R.id.lottie_animation_search);

        searchPresenter = new SearchPresenter(
                RepositoryIm.getInstance(NetWork.getInstance(),
                        LocalSourceIm.getInstance(getActivity())));
        searchPresenterView = searchPresenter;
        filteredItemArrayList = new ArrayList<>();

        mealsAdapter = new MealsAdapter(filteredItemArrayList, requireActivity(),
                searchPresenter
        );

        recyclerViewOfSearch.setAdapter(mealsAdapter);

    }


    void setChipText() {
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = requireActivity().findViewById(checkedId);

            if (selectedChip != null) {
                selectedChipText = selectedChip.getText().toString();
                searchPresenterView.sendChipValueAndSearchValue(selectedChipText, wordOfSearch);
            } else {
                selectedChipText = Constants.Meal;
                searchPresenterView.sendChipValueAndSearchValue(selectedChipText, wordOfSearch);
            }

        });

    }

    void setWordOfSearch() {
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    wordOfSearch = charSequence.toString();
                    searchPresenterView.sendChipValueAndSearchValue(selectedChipText, wordOfSearch);
                } else {
                    wordOfSearch = Constants.Empty;
                    searchPresenterView.sendChipValueAndSearchValue(selectedChipText, wordOfSearch);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    void observeFilterData() {
        searchPresenter.filteredItemsLiveData().observe(getViewLifecycleOwner(),
                filteredItems -> {
                    if (filteredItems.size() != 0) {
                        lottieAnimation.setVisibility(View.INVISIBLE);
                    } else {
                        lottieAnimation.setVisibility(View.VISIBLE);
                    }

                    mealsAdapter.updateData(filteredItems);


                });
    }
}