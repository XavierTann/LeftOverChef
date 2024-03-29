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

public class recipeAdapterFavourites extends RecyclerView.Adapter<recipeAdapterFavourites.RecipeViewHolder> {

    private List<favouritesRecipeItem> favouritesRecipeItemList;
    public recipeAdapterFavourites(List<favouritesRecipeItem> featuredRecipeItemList) {
        this.favouritesRecipeItemList = featuredRecipeItemList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourites_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        favouritesRecipeItem favouritesRecipeItem = favouritesRecipeItemList.get(position);
        holder.recipeThumbnail.setImageResource(favouritesRecipeItem.getRecipeThumbnail());
        holder.recipeName.setText(favouritesRecipeItem.getRecipeName());
        holder.recipeDescription.setText(favouritesRecipeItem.getRecipeDescription());
    }
    @Override
    public int getItemCount() {
        return favouritesRecipeItemList.size();
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