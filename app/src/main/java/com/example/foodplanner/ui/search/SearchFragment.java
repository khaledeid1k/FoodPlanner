package com.example.foodplanner.ui.search;

import static com.example.foodplanner.utils.Extensions.intiStateAnimation;
import static com.example.foodplanner.utils.Extensions.updateUIState;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.data.local.LocalSourceIm;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.network.RemoteSourceIm;
import com.example.foodplanner.data.repository.RepositoryIm;
import com.example.foodplanner.ui.base.BaseFragment;
import com.example.foodplanner.ui.meals.MealsAdapter;
import com.example.foodplanner.ui.meals.OnClickListener;
import com.example.foodplanner.utils.Constants;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;


public class SearchFragment extends BaseFragment implements SearchFragmentView,
        OnClickListener {
    ChipGroup chipGroup;
    MaterialAutoCompleteTextView searchText;
    RecyclerView recyclerViewOfSearch;
    LottieAnimationView lottieAnimationNoResult;
    AppCompatButton retryButton;


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
        intiStateAnimation(view);

    }

    void init(View view) {

        retryButton = view.findViewById(R.id.retry);
        chipGroup = view.findViewById(R.id.chipGroup);
        searchText = view.findViewById(R.id.search_text_value);
        recyclerViewOfSearch = view.findViewById(R.id.recycler_view_search);
        lottieAnimationNoResult = view.findViewById(R.id.lottie_animation_search);

        searchPresenter = new SearchPresenter(
                RepositoryIm.getInstance(RemoteSourceIm.getInstance(),
                        LocalSourceIm.getInstance(getActivity())), this);
        searchPresenterView = searchPresenter;
        filteredItemArrayList = new ArrayList<>();

        mealsAdapter = new MealsAdapter(filteredItemArrayList, requireActivity(),
                this
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

    @Override
    public void getFilterData(ArrayList<FilteredItem> filteredItems) {
        showNoResult( filteredItems.size() == 0);
        updateUIState(false,false);
        mealsAdapter.updateData(filteredItems);
    }

    @Override
    public void navigateToMeal(Meal meal) {
        SearchFragmentDirections.ActionSearchToMealFragment action =
                SearchFragmentDirections.actionSearchToMealFragment(
                        meal);
        Navigation.findNavController(requireView()).navigate(
                action);
    }

    @Override
    public void showLoading() {
        showNoResult(false);
        updateUIState(true,false);
    }

    @Override
    public void showError(String errorMessage) {
        filteredItemArrayList.clear();
        mealsAdapter.updateData(filteredItemArrayList);
        showNoResult(false);
        updateUIState(false,true);
        retryButton.setOnClickListener(view -> searchPresenterView.sendChipValueAndSearchValue(selectedChipText, wordOfSearch));

    }


    @Override
    public void onclickMeal(String nameOfMeal) {
        searchPresenterView.getMealByName(nameOfMeal);
    }

    private void showNoResult(boolean showNoResult) {
        lottieAnimationNoResult.setVisibility(showNoResult ? View.VISIBLE : View.GONE);
    }

}

