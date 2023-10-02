package com.example.foodplanner.ui.favourite;

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
import com.example.foodplanner.data.models.meal.Meal;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    ArrayList<Meal> meals;
    Context context;
    OnClickFavorite onClickFavorite;

    public FavouriteAdapter(ArrayList<Meal> meals, Context context, OnClickFavorite onClickFavorite) {
        this.meals = meals;
        this.context = context;
        this.onClickFavorite = onClickFavorite;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_favourite,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Meal meal = meals.get(position);
        Glide.with(context).load(meal.getStrMealThumb()).error(
                R.drawable.ic_launcher_background
        ).into(holder.imgOfMeal);
        holder.categoryOfMeal.setText(meal.getStrCategory());
        holder.nameOfMeal.setText(meal.getStrMeal());
        holder.itemView.setOnClickListener(view -> onClickFavorite.onClick(meal,view));
        holder.unFavorite.setOnClickListener(view -> onClickFavorite.deleteMeal(meal));

    }

    @Override
    public int getItemCount() {
        return meals==null?0:meals.size();
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView nameOfMeal=itemView.findViewById(R.id.name_f);
        TextView categoryOfMeal=itemView.findViewById(R.id.category_f);
        ImageView imgOfMeal=itemView.findViewById(R.id.image_f);
        ImageView unFavorite =itemView.findViewById(R.id.make_unFavorite);
    }

    public void updateData(ArrayList<Meal> newData) {
        meals=newData;
        notifyDataSetChanged();
    }
}
