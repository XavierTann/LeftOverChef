package com.example.aninterface.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aninterface.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class recipeAdapterFeatured extends RecyclerView.Adapter<recipeAdapterFeatured.RecipeViewHolder> {

    private List<featuredRecipeItem> featuredRecipeItemList;
    public recipeAdapterFeatured(List<featuredRecipeItem> featuredRecipeItemList) {
        this.featuredRecipeItemList = featuredRecipeItemList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_recipe1, parent, false);
        return new RecipeViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        featuredRecipeItem featuredRecipeItem = featuredRecipeItemList.get(position);
        holder.recipeThumbnail.setImageResource(featuredRecipeItem.getRecipeThumbnail());
        holder.recipeName.setText(featuredRecipeItem.getRecipeName());
        holder.recipeDescription.setText(featuredRecipeItem.getRecipeDescription());
//        holder.recipeImage.setImageResource(featuredRecipeItem.getRecipeImage());
    }
    @Override
    public int getItemCount() {
        return featuredRecipeItemList.size();
    }


    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView recipeThumbnail;
        TextView recipeName;
        TextView recipeDescription;
//        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeThumbnail = itemView.findViewById(R.id.recipeThumbnail);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeDescription = itemView.findViewById(R.id.recipeDescription);
//            recipeImage = itemView.findViewById(R.id.recipeImage);

        }
    }
}
