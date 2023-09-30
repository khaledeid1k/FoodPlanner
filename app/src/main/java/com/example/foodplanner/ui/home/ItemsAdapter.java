package com.example.foodplanner.ui.home;

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
import com.example.foodplanner.data.models.meal.Meals;

import java.util.ArrayList;

interface OnClick {
    void click(int position);
}

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    Context context;
    ArrayList<Meal> items;
    OnClick onClick;

    public ItemsAdapter(Context context, ArrayList<Meal> items) {
        this.context = context;
        this.items = items;
       // this.onClick = onClick;

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
        Meal item = items.get(position);
        holder.title.setText(item.getStrMeal());
        Glide.with(context).load(item.getStrMealThumb()).error(
                R.drawable.ic_launcher_background
        ).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView title = itemView.findViewById(R.id.text_item_list_home);
        ImageView productImage = itemView.findViewById(R.id.image_item_list_home);

    }
}
