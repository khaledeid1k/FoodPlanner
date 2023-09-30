package com.example.foodplanner.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.DataItem;
import com.example.foodplanner.data.models.HeaderItem;
import com.example.foodplanner.data.models.MealsItem;
import com.example.foodplanner.data.models.Tag;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.Meals;
import com.example.foodplanner.ui.base.BaseInteractionListener;

import java.util.ArrayList;
import java.util.List;

interface NavigationInteractionListener extends BaseInteractionListener {
    void onNavigate(DataItem dataItem);
}


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    List<DataItem> dataItemList;
    NavigationInteractionListener navigationInteractionListener;
    final int HEADER_ITEM = 0;
    final int TAG_ITEM = 1;
    Context context;
    String TAG="HomeAdapter";

    public HomeAdapter(
            Context context,
            List<DataItem> dataItemList
            //NavigationInteractionListener navigationInteractionListener
    ) {
        this.dataItemList = dataItemList;
      //  this.navigationInteractionListener = navigationInteractionListener;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (dataItemList.get(position) instanceof HeaderItem) {
            return HEADER_ITEM;
        }
        return TAG_ITEM;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeViewHolder homeViewHolder;
        if (viewType == HEADER_ITEM) {
            homeViewHolder = new HeaderViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.head_of_home, parent, false
                    ));
        } else {
            homeViewHolder = new ItemViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.item_home, parent, false
                    ));
        }

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        DataItem dataItem = dataItemList.get(position);
        Log.i(TAG, "onBindViewHoldertitle: "  +dataItem.getTag().getTitle());
        Log.i(TAG, "onBindViewHolderresourse: "  +dataItem.getTag().getResourcesData());

        if(holder instanceof HeaderViewHolder){
            if (dataItem instanceof HeaderItem) {
                Meal meal = ((HeaderItem) dataItem).getMeals();
                ((HeaderViewHolder) holder).nameMeaOfDay.setText(
                        meal.getStrMeal()
                );
                ((HeaderViewHolder) holder).countryMeaOfDay.setText(
                        meal.getStrArea()
                );
                Glide.with(context).load(meal.getStrMealThumb()).error(
                        R.drawable.no_result_search
                ).into(((HeaderViewHolder) holder).imageMealOfDay);


            }
        } else if(holder instanceof ItemViewHolder){
             if (dataItem instanceof MealsItem) {
                Tag<List<Meal>> tag = ((MealsItem) dataItem).getTag();
                String title = tag.getTitle();

                List<Meal> resourcesData = tag.getResourcesData();



                ((ItemViewHolder) holder).tileOfList.setText(
                        title
                );

                ItemsAdapter itemsAdapter = new ItemsAdapter(context,
                        new ArrayList<>(resourcesData));
                ((ItemViewHolder) holder).recycleItemHome.setAdapter(
                        itemsAdapter
                );
            }
        }


    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    public class HeaderViewHolder extends HomeViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        ImageView imageMealOfDay = itemView.findViewById(R.id.img_meal_day);
        TextView nameMeaOfDay = itemView.findViewById(R.id.name_meal_day);
        TextView countryMeaOfDay = itemView.findViewById(R.id.country_meal_day);
    }

    public class ItemViewHolder extends HomeViewHolder {
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView tileOfList = itemView.findViewById(R.id.title_list);
        RecyclerView recycleItemHome = itemView.findViewById(R.id.recycle_item_home);


    }
}
