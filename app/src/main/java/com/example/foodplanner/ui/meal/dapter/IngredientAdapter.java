package com.example.foodplanner.ui.meal.dapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.IngredientMeasurePair;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
   Context context;
   ArrayList<IngredientMeasurePair> ingredientMeasurePairs;

    public IngredientAdapter(Context context, ArrayList<IngredientMeasurePair> ingredientMeasurePairs) {
        this.context = context;
        this.ingredientMeasurePairs = ingredientMeasurePairs;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_ingredient,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientMeasurePair ingredientMeasurePair = ingredientMeasurePairs.get(position);

          String formattedText = context.getString(R.string.ingredient_measure,
                  ingredientMeasurePair.getMeasure(),
                  ingredientMeasurePair.getIngredient());
          holder.textView.setText(formattedText);


    }

    @Override
    public int getItemCount() {
        return ingredientMeasurePairs==null ? 0 : ingredientMeasurePairs.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView textView=itemView.findViewById(R.id.text_ingredient_item);

    }
}
