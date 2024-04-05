package com.example.aninterface.Drawer.DietaryButtonRecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    private int selectedIndex = -1;
    private Context context;
    private List<ButtonItem> buttonItemList; // Instead of buttonLabels


    public ButtonAdapter(List<ButtonItem> buttonItemList, Context context) {
        this.buttonItemList = buttonItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_dietary_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ButtonItem item = buttonItemList.get(position);
        holder.dietaryname.setText(item.getButtonText());

        // Update the background based on selection
        updateBackground(holder, item.isSelected());

        holder.relativeLayout.setOnClickListener(v -> {
            // Toggle the current item's selected state
            item.setSelected(!item.isSelected());
            String phoneNumber = SharedPreferencesUtil.getPhoneNumber(context);
            // Reference to the user's dietary restrictions in Firebase
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users")
                    .child(phoneNumber) // Replace with the user's actual ID
                    .child("dietaryRestrictions");
            if (item.isSelected()) {
                // Add the dietary restriction to Firebase
                userRef.child(item.getButtonText()).setValue(item.getButtonText()); // Using an empty string as a placeholder
            } else {
                // Remove the dietary restriction from Firebase
                userRef.child(item.getButtonText()).removeValue();
            }
            notifyItemChanged(position); // Refresh the current item to reflect the change
        });
    }

    private void updateBackground(@NonNull ViewHolder holder, boolean isSelected) {
        Drawable background = isSelected
                ? ContextCompat.getDrawable(context, R.drawable.border_orange_morerounded) // Drawable for selected state
                : ContextCompat.getDrawable(context, R.drawable.border_black_morerounded); // Drawable for normal state
        holder.relativeLayout.setBackground(background);
    }
    @Override
    public int getItemCount() {
        return buttonItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dietaryname;
        ImageView imageView;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            dietaryname = itemView.findViewById(R.id.DietaryText);
            imageView = itemView.findViewById(R.id.addIcon);
            relativeLayout = (RelativeLayout) itemView; // Assuming the root is the RelativeLayout
        }
    }
}

