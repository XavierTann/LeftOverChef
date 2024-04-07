package com.example.aninterface.Fragments.History;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.R;
import com.example.aninterface.SeeMorePage2;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class RecipeAdapterHistory extends RecyclerView.Adapter<RecipeAdapterHistory.RecipeViewHolder> {
    private Context context;
    private List<HistoryRecipeItem> historyRecipeItemList;

    public RecipeAdapterHistory(Context context, List<HistoryRecipeItem> historyRecipeItemList) {
        this.context = context;
        this.historyRecipeItemList = historyRecipeItemList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        HistoryRecipeItem historyRecipeItem = historyRecipeItemList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(historyRecipeItem.getRecipeThumbnail())
                .apply(new RequestOptions().placeholder(R.drawable.leftoverchef)) // Placeholder image while loading
                .into(holder.recipeThumbnail);

        holder.recipeName.setText(historyRecipeItem.getRecipeName());
        holder.recipeDescription.setText(historyRecipeItem.getRecipeDescription());

        // Set OnClickListener for the recipeName TextView
        holder.recipeName.setOnClickListener(view -> {
            // Get the position of the clicked item
            int position1 = holder.getAdapterPosition();
            if (position1 != RecyclerView.NO_POSITION) {
                // Handle the click event
                HistoryRecipeItem clickedItem = historyRecipeItemList.get(position1);

                // Start the SeeMorePage2 activity and pass necessary data
                String phoneNumber = SharedPreferencesUtil.getPhoneNumber(context);
                Intent intent = new Intent(context, SeeMorePage2.class);
                intent.putExtra("recipeName", clickedItem.getRecipeName());
                intent.putExtra("path", "users/" + phoneNumber +"/recipe");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyRecipeItemList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView recipeThumbnail;
        TextView recipeName;
        TextView recipeDescription;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeThumbnail = itemView.findViewById(R.id.history_recipe_thumbnail);
            recipeName = itemView.findViewById(R.id.history_recipe_name);
            recipeDescription = itemView.findViewById(R.id.history_recipe_description);
        }
    }

}
