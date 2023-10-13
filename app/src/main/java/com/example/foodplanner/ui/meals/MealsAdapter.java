package com.example.foodplanner.ui.meals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.filter.FilteredItem;

import java.util.ArrayList;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    ArrayList<FilteredItem> meals;
    Context context;
    OnClickListener onClickItem;
    public MealsAdapter(ArrayList<FilteredItem> meals, Context context,OnClickListener onClickItem) {
        this.meals = meals;
        this.context = context;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_search,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        FilteredItem filteredItem = meals.get(position);
        holder.nameOfMeal.setText(filteredItem.getStrMeal());
        Glide.with(context).load(filteredItem.getStrMealThumb()).error(
                R.drawable.ic_launcher_background
        ).into(holder.ImageOfMeal);

       holder.itemView.setOnClickListener(view -> onClickItem.onclickMeal(filteredItem.getStrMeal()));

    }

    @Override
    public int getItemCount() {
        return meals==null?0:meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView nameOfMeal =itemView.findViewById(R.id.name_meal_of_search);
        ImageView ImageOfMeal =itemView.findViewById(R.id.image_meal_of_search);
    }
    public void updateData(ArrayList<FilteredItem> newData) {
     //   meals.clear();
        meals=newData;
        notifyDataSetChanged();
    }
}
