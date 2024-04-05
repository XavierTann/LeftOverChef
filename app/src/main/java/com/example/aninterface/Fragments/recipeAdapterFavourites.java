package com.example.aninterface.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aninterface.HelperClass.FirebaseFunctions;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recipeAdapterFavourites extends RecyclerView.Adapter<recipeAdapterFavourites.RecipeViewHolder> {
    private Context context;
    private List<favouritesRecipeItem> favouritesRecipeItemList;

    public recipeAdapterFavourites(Context context, List<favouritesRecipeItem> featuredRecipeItemList) {
        this.context = context;
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
        String firebaseRecipeName = FirebaseFunctions.recipeNameToFirebaseKey(favouritesRecipeItem.getRecipeName());
        holder.likeButton.setImageResource(R.drawable.baseline_favorite_24);
        // similar to picasso
        Glide.with(holder.itemView.getContext())
                .load(favouritesRecipeItem.getRecipeThumbnail())
                .apply(new RequestOptions().placeholder(R.drawable.leftoverchef)) // Placeholder image while loading
                .into(holder.recipeThumbnail);
//        holder.recipeThumbnail.setImageResource(featuredRecipeItem.getRecipeThumbnail());
        holder.recipeName.setText(favouritesRecipeItem.getRecipeName());
        holder.recipeDescription.setText(favouritesRecipeItem.getRecipeDescription());
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    favouritesRecipeItem item = favouritesRecipeItemList.get(adapterPosition);
                    boolean isLiked = item.isLiked();
                    // Toggle the liked state
                    item.setLiked(isLiked);
                    // Update the button image based on the liked state
                    holder.likeButton.setImageResource(item.isLiked() ? R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);

                    // Update Firebase
                    String phoneNumber = SharedPreferencesUtil.getPhoneNumber(context);
                    DatabaseReference itemFavouritesRef = FirebaseDatabase.getInstance().getReference("users")
                            .child(phoneNumber).child("favourites").child(firebaseRecipeName);

                    if (item.isLiked()) {
                    }
                    else {
                        // Remove from the local list to update UI immediately
                        favouritesRecipeItemList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);

                        // Remove from Firebase
                        itemFavouritesRef.removeValue();
                    }
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return favouritesRecipeItemList.size();
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
            likeButton = itemView.findViewById(R.id.favourites_LikeButton);
        }
    }
}