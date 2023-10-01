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

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    Context context;
    ArrayList items;
    DataItem dataItem;
    OnClickItem onClickItem;

    public ItemsAdapter(Context context, DataItem dataItem, OnClickItem onClickItem) {
        this.context = context;
        this.dataItem = dataItem;
      this.onClickItem = onClickItem;
      if(dataItem instanceof MealsItem){
          List<Meal> resourcesData = ((MealsItem) dataItem).getTag().getResourcesData();
          items=  new  ArrayList<>(resourcesData);
      }
      else if(dataItem instanceof CategoriesItem){
          List<CategoryWithDetails> resourcesData = ((CategoriesItem) dataItem).getTag().getResourcesData();
          items=  new  ArrayList<>(resourcesData);
      }
      else if(dataItem instanceof CountriesItem){
          List<Country> resourcesData = ((CountriesItem) dataItem).getTag().getResourcesData();
          items=  new  ArrayList<>(resourcesData);
      }

    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_list_home, parent, false
        ));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Object o = items.get(position);
        if( o instanceof Meal){
            Log.i("yyyyyyyy", "onBindViewHolder: "+((Meal) o).getStrArea());

            Meal meal = ((Meal) o);
            holder.title.setText(meal.getStrMeal());
            Glide.with(context).load(meal.getStrMealThumb()).error(
                    R.drawable.ic_launcher_background
            ).into(holder.productImage);
            holder.itemView.setOnClickListener(view -> onClickItem.click(dataItem,position));

        }
      else   if( o instanceof CategoryWithDetails){
            Log.i("ssssss", "onBindViewHolder: "+((CategoryWithDetails) o).getStrCategory());
            CategoryWithDetails categoryWithDetails = ((CategoryWithDetails) o);
            holder.title.setText(categoryWithDetails.getStrCategory());
            Glide.with(context).load(categoryWithDetails.getStrCategoryThumb()).error(
                    R.drawable.ic_launcher_background
            ).into(holder.productImage);
            holder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position));

        }
        else if( o instanceof Country){
            Country country = ((Country) o);
            holder.title.setText(country.getStrArea());
            Glide.with(context).load(R.drawable.food_image_eleven).error(
                    R.drawable.ic_launcher_background
            ).into(holder.productImage);
            holder.itemView.setOnClickListener(view -> onClickItem.click(dataItem, position));

        }

    }

    @Override
    public int getItemCount() {
        return items==null ? 0 :items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView title = itemView.findViewById(R.id.text_item_list_home);
        ImageView productImage = itemView.findViewById(R.id.image_item_list_home);

    }
}
