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
import com.example.foodplanner.utils.Constants;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MainFavouriteViewHolder> {
    ArrayList<Meal> meals;
    Context context;
    OnClickFavoriteOrPlanned onClickFavoriteOrPlanned;
    int type;
    public FavouriteAdapter(ArrayList<Meal> meals, Context context,
                            OnClickFavoriteOrPlanned onClickFavoriteOrPlanned, int  type) {
        this.meals = meals;
        this.context = context;
        this.onClickFavoriteOrPlanned = onClickFavoriteOrPlanned;
        this.type = type;
    }

    @NonNull
    @Override
    public MainFavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainFavouriteViewHolder mainFavouriteViewHolder;
        if(viewType==Constants.Favorite){
            mainFavouriteViewHolder= new FavouriteViewHolder(LayoutInflater.from(context).inflate(
                    R.layout.item_favourite,parent,false
            ));
        }else{
            mainFavouriteViewHolder=new PlannedViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.item_planed_meal,parent,false
                    )
            );
        }

        return mainFavouriteViewHolder;
    }
    public int getItemViewType(int position) {
        if (type == Constants.Favorite) {
            return Constants.Favorite;
        }
        return Constants.Planed;
    }
    @Override
    public void onBindViewHolder(@NonNull MainFavouriteViewHolder holder, int position) {
        Meal meal = meals.get(position);
        if(holder instanceof  PlannedViewHolder){
            PlannedViewHolder plannedViewHolder = (PlannedViewHolder) holder;
            Glide.with(context).load(meal.getStrMealThumb()).error(
                    R.drawable.ic_launcher_background
            ).into(plannedViewHolder.imgOfMeal);
            plannedViewHolder.categoryOfMeal.setText(meal.getStrCategory());
            plannedViewHolder.nameOfMeal.setText(meal.getStrMeal());
            plannedViewHolder.itemView.setOnClickListener(view -> onClickFavoriteOrPlanned.onClick(meal,view));
            plannedViewHolder.delete.setOnClickListener(view -> onClickFavoriteOrPlanned.deleteMeal(meal));

        }
        if(holder instanceof  FavouriteViewHolder){
            FavouriteViewHolder favouriteViewHolder = (FavouriteViewHolder) holder;
            Glide.with(context).load(meal.getStrMealThumb()).error(
                    R.drawable.ic_launcher_background
            ).into(favouriteViewHolder.imgOfMeal);
            favouriteViewHolder.categoryOfMeal.setText(meal.getStrCategory());
            favouriteViewHolder.nameOfMeal.setText(meal.getStrMeal());
            favouriteViewHolder.itemView.setOnClickListener(view -> onClickFavoriteOrPlanned.onClick(meal,view));
            favouriteViewHolder.unFavorite.setOnClickListener(view -> onClickFavoriteOrPlanned.deleteMeal(meal));

        }

    }



    public class MainFavouriteViewHolder extends RecyclerView.ViewHolder {
        public MainFavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
    public class FavouriteViewHolder extends MainFavouriteViewHolder {

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView nameOfMeal=itemView.findViewById(R.id.name_f);
        TextView categoryOfMeal=itemView.findViewById(R.id.category_f);
        ImageView imgOfMeal=itemView.findViewById(R.id.image_f);
        ImageView unFavorite =itemView.findViewById(R.id.make_unFavorite);
    }
    public class PlannedViewHolder extends MainFavouriteViewHolder {
        public PlannedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView nameOfMeal=itemView.findViewById(R.id.name_p);
        TextView categoryOfMeal=itemView.findViewById(R.id.category_p);
        ImageView imgOfMeal=itemView.findViewById(R.id.image_p);
        ImageView delete =itemView.findViewById(R.id.delete_planed_meal);

    }


    @Override
    public int getItemCount() {
        return meals==null?0:meals.size();
    }
    public void updateData(ArrayList<Meal> newData) {
        meals=newData;
        notifyDataSetChanged();
    }



}
