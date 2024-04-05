package com.example.aninterface.Fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aninterface.HelperClass.HelperClass;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.HelperClass.FirebaseFunctions;
import com.example.aninterface.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class recipeAdapterFeatured extends RecyclerView.Adapter<recipeAdapterFeatured.RecipeViewHolder> {
    private Context context;
    private List<featuredRecipeItem> featuredRecipeItemList;
    private Set<String> userFavourites = new HashSet<>();

    public void setUserFavourites(Set<String> favourites) {
        this.userFavourites = favourites;
        notifyDataSetChanged();
    }


    public recipeAdapterFeatured(Context context, List<featuredRecipeItem> featuredRecipeItemList) {
        this.context = context;
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

        String firebaseRecipeName = FirebaseFunctions.recipeNameToFirebaseKey(featuredRecipeItem.getRecipeName());
        // Set like button state based on whether the item is favourited
        boolean isFavourited = userFavourites.contains(firebaseRecipeName);
        featuredRecipeItem.setLiked(isFavourited);
        holder.likeButton.setImageResource(isFavourited ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);

        Glide.with(holder.itemView.getContext())
                .load(featuredRecipeItem.getRecipeThumbnail())
                .apply(new RequestOptions().placeholder(R.drawable.leftoverchef)) // Placeholder image while loading
                .into(holder.recipeThumbnail);

        holder.recipeName.setText(featuredRecipeItem.getRecipeName());
        holder.recipeDescription.setText(featuredRecipeItem.getRecipeDescription());

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    featuredRecipeItem item = featuredRecipeItemList.get(adapterPosition);
                    boolean isLiked = item.isLiked(); // Assume you have a boolean field 'isLiked' in your item class
                    // Toggle the liked state
                    item.setLiked(!isLiked);

                // Update the button image based on the liked state
                holder.likeButton.setImageResource(item.isLiked() ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);

                // Update Firebase
                String phoneNumber = SharedPreferencesUtil.getPhoneNumber(context);
                DatabaseReference itemAllRecipeRef = FirebaseDatabase.getInstance().getReference("all_recipes").child(firebaseRecipeName);
                DatabaseReference itemFavouritesRef = FirebaseDatabase.getInstance().getReference("users").child(phoneNumber).child("favourites").child(firebaseRecipeName);

                if (item.isLiked()) {
                    itemAllRecipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            itemFavouritesRef.setValue(snapshot.getValue());}
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
                else {
                    // If the recipe is now unliked, remove it from the user's favorites
                    itemFavouritesRef.removeValue();}

            }}
        });
    }
    @Override
    public int getItemCount() {
        return featuredRecipeItemList.size();
    }


    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView recipeThumbnail;
        TextView recipeName;
        TextView recipeDescription;
        ImageView likeButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeThumbnail = itemView.findViewById(R.id.recipeThumbnail);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeDescription = itemView.findViewById(R.id.recipeDescription);
            likeButton = itemView.findViewById(R.id.featured_Like_Button);

        }
    }

    

    // Method to update the adapter with new recipe data
    public void updateRecipes(List<featuredRecipeItem> newRecipes) {
        this.featuredRecipeItemList = newRecipes;
        notifyDataSetChanged(); // Notify adapter of dataset change
    }
}
