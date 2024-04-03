package com.example.aninterface.Fragments;

import android.content.Context;
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

import com.example.aninterface.HelperClass.FirebaseFunctions;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class recipeAdapterPantry extends RecyclerView.Adapter<recipeAdapterPantry.RecipeViewHolder> {
    private Context context;
    private List<pantryIngredientItem> pantryIngredientItemList;
    public recipeAdapterPantry(Context context, List<pantryIngredientItem> pantryIngredientItemList) {
        this.context = context;
        this.pantryIngredientItemList = pantryIngredientItemList;}

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

        String ingredientNameFB = FirebaseFunctions.recipeNameToFirebaseKey(pantryIngredientItem.getIngredientName());
        holder.ingredient_deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    pantryIngredientItem item = pantryIngredientItemList.get(adapterPosition);
                    // Update Firebase
                    String phoneNumber = SharedPreferencesUtil.getPhoneNumber(context);
                    DatabaseReference itemPantryIngredientRef = FirebaseDatabase.getInstance().getReference("users")
                            .child(phoneNumber).child("pantry").child(ingredientNameFB);
                    // Remove from the local list to update UI immediately
                    pantryIngredientItemList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    // Remove from Firebase
                    itemPantryIngredientRef.removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryIngredientItemList.size();
    }
    public void addItem(pantryIngredientItem item) {
        pantryIngredientItemList.add(item); // Add the item to your data list
        notifyItemInserted(pantryIngredientItemList.size() - 1); // Notify the adapter of the item insertion
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
