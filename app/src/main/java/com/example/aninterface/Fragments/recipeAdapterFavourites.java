package com.example.aninterface.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aninterface.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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
        //original code is the code commented out below, this glide is supp to process the images
        // similar to picasso
        Glide.with(holder.itemView.getContext())
                .load(favouritesRecipeItem.getRecipeThumbnail())
                .apply(new RequestOptions().placeholder(R.drawable.leftoverchef)) // Placeholder image while loading
                .into(holder.recipeThumbnail);
//        holder.recipeThumbnail.setImageResource(featuredRecipeItem.getRecipeThumbnail());
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
        ImageView favouriteButton;
//        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeThumbnail = itemView.findViewById(R.id.recipeThumbnail);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeDescription = itemView.findViewById(R.id.recipeDescription);
//            recipeImage = itemView.findViewById(R.id.recipeImage);
//            favouriteButton = itemView.findViewById(R.id.Favourites_FavouritesButton);
//
//            favouriteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        favouritesRecipeItem item = favouritesRecipeItemList.get(position);
//                        toggleFavouriteState(item, position);
//                    }
//                }
//                private void toggleFavouriteState(favouritesRecipeItem item, int position) {
//                    // Example check to determine the current state
//                    boolean isFavourite = item.isFavourite();
//
//                    if (isFavourite) {
//                        // If currently a favorite, remove from Firebase and update icon
//                        removeFromFavourites(item.getId(), position);
//                    } else {
//                        // If not a favorite, add to Firebase and update icon
//                        addToFavourites(item.getId(), position);
//                    }
//                }
//                private void addToFavourites(String itemId, int position) {
//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("favourites").child(itemId);
//                    ref.setValue(true).addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            // Update the item's favorite state and icon
//                            favouritesRecipeItemsList.get(position).setFavourite(true);
//                            notifyItemChanged(position);
//                        }
//                    });
//                }
//
//                private void removeFromFavourites(String itemId, int position) {
//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("favourites").child(itemId);
//                    ref.removeValue().addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            // Update the item's favorite state and icon
//                            favouritesRecipeItemsList.get(position).setFavourite(false);
//                            notifyItemChanged(position);
//                        }
//                    });
//                }


//            });


        }
    }
}