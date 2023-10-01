package com.example.foodplanner.ui.meal.dapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.data.models.meal.Instructions;

import java.util.ArrayList;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder> {

    Context context;
    ArrayList<Instructions> instructionsArrayList;

    public InstructionsAdapter(Context context,
                               ArrayList<Instructions> instructionsArrayList
                            ) {
        this.context = context;
        this.instructionsArrayList = instructionsArrayList;
    }



    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_ingredient,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        Instructions instructions = instructionsArrayList.get(position);
        String formattedText = context.getString(R.string.ingredient_measure,
                instructions.getNumber(),
                instructions.getInstruction());
        holder.textView.setText(formattedText);
    }

    @Override
    public int getItemCount() {
        return instructionsArrayList==null?0:instructionsArrayList.size();
    }

    public class InstructionsViewHolder extends RecyclerView.ViewHolder{
        public InstructionsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView textView=itemView.findViewById(R.id.text_ingredient_item);
    }
}
