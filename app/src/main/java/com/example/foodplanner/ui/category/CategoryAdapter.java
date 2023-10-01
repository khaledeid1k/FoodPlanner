package com.example.foodplanner.ui.category;

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
import com.example.foodplanner.data.models.IngredientMeasurePair;
import com.example.foodplanner.data.models.category.CategoriesWithDetails;
import com.example.foodplanner.data.models.category.CategoryWithDetails;
import com.example.foodplanner.data.models.filter.FilteredItem;
import com.example.foodplanner.ui.meal.dapter.IngredientAdapter;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<CategoryWithDetails> categoryWithDetailsArrayList;

    public CategoryAdapter(Context context, ArrayList<CategoryWithDetails> categoryWithDetailsArrayList) {
        this.context = context;
        this.categoryWithDetailsArrayList = categoryWithDetailsArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_category,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryWithDetails categoryWithDetails = categoryWithDetailsArrayList.get(position);
        Glide.with(context).load(categoryWithDetails.getStrCategoryThumb()).error(
                R.drawable.ic_launcher_background
        ).into(holder.imageViewOfCategory);
        holder.textViewOfCategory.setText(categoryWithDetails.getStrCategory());
    }

    @Override
    public int getItemCount() {
        return categoryWithDetailsArrayList==null ? 0 : categoryWithDetailsArrayList.size();

    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView textViewOfCategory=itemView.findViewById(R.id.text_category_item);
        ImageView imageViewOfCategory=itemView.findViewById(R.id.image_category_item);

    }
    public void updateData(ArrayList<CategoryWithDetails>newData) {
        categoryWithDetailsArrayList=newData;
        notifyDataSetChanged();
    }
}
