package com.example.aninterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aninterface.Fragments.pantryIngredientItem;
import com.example.aninterface.HelperClass.FirebaseFunctions;

import java.util.List;

public class IngredientPage_Adapter extends RecyclerView.Adapter<IngredientPage_Adapter.RecipeViewHolder> {
    private Context context;
    private List<pantryIngredientItem> ingredientPageItemList;
    public IngredientPage_Adapter(Context context, List<pantryIngredientItem> ingredientPageItemList) {
        this.context = context;
        this.ingredientPageItemList = ingredientPageItemList;}

    @NonNull
    @Override
    public IngredientPage_Adapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry_ingredients, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientPage_Adapter.RecipeViewHolder holder, int position) {
        pantryIngredientItem pantryIngredientItem = ingredientPageItemList.get(position);
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
                    pantryIngredientItem item = ingredientPageItemList.get(adapterPosition);
                    // Remove from the local list to update UI immediately
                    ingredientPageItemList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientPageItemList.size();
    }
    public void addItem(pantryIngredientItem item) {
        ingredientPageItemList.add(item); // Add the item to your data list
        notifyItemInserted(ingredientPageItemList.size() - 1); // Notify the adapter of the item insertion
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
                        ingredientPageItemList.get(getAdapterPosition()).setSelected(true);
                    } else{
                        ingredientPageItemList.get(getAdapterPosition()).setSelected(false);
                    }
                    notifyDataSetChanged();
                }
            });
        }

    }
}
