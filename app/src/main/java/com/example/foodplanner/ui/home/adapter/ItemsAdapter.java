package com.example.foodplanner.ui.home.adapter;

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
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.country.Country;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    Context context;
    ArrayList items;
    DataItem dataItem;
    OnClickItem onClickItem;
    int type;

    public ItemsAdapter(Context context, DataItem dataItem, OnClickItem onClickItem,int type) {
        this.context = context;
        this.dataItem = dataItem;
        this.onClickItem = onClickItem;
        this.type = type;
        if (dataItem instanceof MealsItem) {
            List<Meal> resourcesData = ((MealsItem) dataItem).getTag().getResourcesData();
            items = new ArrayList<>(resourcesData.subList(0,6));
        } else if (dataItem instanceof CategoriesItem) {
            List<CategoryWithDetails> resourcesData = ((CategoriesItem) dataItem).getTag().getResourcesData();
            items = new ArrayList<>(resourcesData.subList(0,8));
        } else if (dataItem instanceof CountriesItem) {
            List<Country> resourcesData = ((CountriesItem) dataItem).getTag().getResourcesData();
            items = new ArrayList<>(resourcesData.subList(0,8));
        }

    }

    public int getItemViewType(int position) {
        if (type == Constants.VIEW_TYPE_LINER) {
            return Constants.VIEW_TYPE_LINER;
        }
        return Constants.VIEW_TYPE_GRID;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsViewHolder itemsViewHolder;
        if (viewType == Constants.VIEW_TYPE_GRID) {
            itemsViewHolder = new ItemsViewHolderGrid(LayoutInflater.from(context).inflate(
                    R.layout.item_show_all, parent, false
            ));
        } else {
            itemsViewHolder =new ItemsViewHolderLinear(LayoutInflater.from(context).inflate(
                    R.layout.item_list_home, parent, false
            ));
        }
        return itemsViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {

        Object o = items.get(position);
        if(holder instanceof ItemsViewHolderGrid){
            ItemsViewHolderGrid subHolder = (ItemsViewHolderGrid) holder;

            if (o instanceof Meal) {
                Meal meal = ((Meal) o);
                subHolder.title.setText(meal.getStrMeal());
                Glide.with(context).load(meal.getStrMealThumb()).error(
                        R.drawable.ic_launcher_background
                ).into(subHolder.productImage);
                subHolder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position,view));

            } else if (o instanceof CategoryWithDetails) {
                CategoryWithDetails categoryWithDetails = ((CategoryWithDetails) o);
                subHolder.title.setText(categoryWithDetails.getStrCategory());
                Glide.with(context).load(categoryWithDetails.getStrCategoryThumb()).error(
                        R.drawable.ic_launcher_background
                ).into(subHolder.productImage);
                subHolder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position,view));

            } else if (o instanceof Country) {
                Country country = ((Country) o);
                subHolder.title.setText(country.getStrArea());
                Glide.with(context).load(R.drawable.food_image_eleven).error(
                        R.drawable.ic_launcher_background
                ).into(subHolder.productImage);
                subHolder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position,view));

            }
        }
        else {
            ItemsViewHolderLinear subHolder = (ItemsViewHolderLinear) holder;
            if (o instanceof Meal) {
                Meal meal = ((Meal) o);
                subHolder.title.setText(meal.getStrMeal());
                Glide.with(context).load(meal.getStrMealThumb()).error(
                        R.drawable.ic_launcher_background
                ).into(subHolder.productImage);
                subHolder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position,view));

            } else if (o instanceof CategoryWithDetails) {
                CategoryWithDetails categoryWithDetails = ((CategoryWithDetails) o);
                subHolder.title.setText(categoryWithDetails.getStrCategory());
                Glide.with(context).load(categoryWithDetails.getStrCategoryThumb()).error(
                        R.drawable.ic_launcher_background
                ).into(subHolder.productImage);
                subHolder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position,view));

            } else if (o instanceof Country) {
                Country country = ((Country) o);
                subHolder.title.setText(country.getStrArea());
                Glide.with(context).load(R.drawable.food_image_eleven).error(
                        R.drawable.ic_launcher_background
                ).into(subHolder.productImage);
                subHolder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position,view));

            }
        }


    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    class ItemsViewHolderLinear extends ItemsViewHolder{
        public ItemsViewHolderLinear(@NonNull View itemView) {
            super(itemView);
        }
        TextView title = itemView.findViewById(R.id.text_item_list_home);
        ImageView productImage = itemView.findViewById(R.id.image_item_list_home);
    }
    class ItemsViewHolderGrid extends ItemsViewHolder {
        public ItemsViewHolderGrid(@NonNull View itemView) {
            super(itemView);
        }
        TextView title = itemView.findViewById(R.id.title_item_show_all);
        ImageView productImage = itemView.findViewById(R.id.image_item_show_all);
    }

}
