package com.example.aninterface.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aninterface.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class recipeAdapterPantry extends RecyclerView.Adapter<recipeAdapterPantry.RecipeViewHolder> {
    private static List<pantryIngredientItem> pantryIngredientItemList;

    public recipeAdapterPantry(List<pantryIngredientItem> pantryIngredientItemList) {
        this.pantryIngredientItemList = pantryIngredientItemList;
    }


    @NonNull
    @Override
    public recipeAdapterPantry.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry_ingredients, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recipeAdapterPantry.RecipeViewHolder holder, int position) {
        pantryIngredientItem pantryIngredientItem = pantryIngredientItemList.get(position);
        holder.ingredientName.setText(pantryIngredientItem.getIngredientName());
        holder.ingredientAmount.setText(pantryIngredientItem.getIngredientAmount());
        holder.ingredientUnit.setText(pantryIngredientItem.getIngredientUnit());
        holder.checkBox.setChecked(pantryIngredientItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return pantryIngredientItemList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        TextView ingredientAmount;
        TextView ingredientUnit;
        CheckBox checkBox;
        ImageButton ingredient_deleteButton;
        LinearLayout linearLayout;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientAmount = itemView.findViewById(R.id.ingredientAmount);
            ingredientUnit = itemView.findViewById(R.id.ingredientUnit);
            checkBox = itemView.findViewById(R.id.myCheckBox);
            linearLayout =itemView.findViewById(R.id.linearLayoutIngredientItem);
            ingredient_deleteButton = itemView.findViewById(R.id.ImageButton_DeleteIngredient);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked =((CheckBox)v).isChecked();

                    if (isChecked) {
                        pantryIngredientItemList.get(getAdapterPosition()).setSelected(true);
                    } else{
                        pantryIngredientItemList.get(getAdapterPosition()).setSelected(false);
                    }
                    notifyDataSetChanged();
                }
            });


        }

    }
}
