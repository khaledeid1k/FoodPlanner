package com.example.foodplanner.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.ui.home.HomeAdapter;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ParentViewHolder>{
    public class ParentViewHolder extends RecyclerView.ViewHolder {
        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
