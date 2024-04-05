package com.example.aninterface.Drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aninterface.Drawer.DietaryButtonRecyclerView.ButtonAdapter;
import com.example.aninterface.Drawer.DietaryButtonRecyclerView.ButtonItem;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.Home;
import com.example.aninterface.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
public class Drawer_DietarySettings extends AppCompatActivity {
    ImageButton back_DrawerProfile_Home;
    RecyclerView dietarySettingsRecyclerView;
    EditText specialRequirements;
    TextView specialRequirementsTV;
    Button applychanges;
    public Drawer_DietarySettings() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_dietarysettings);
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);

        back_DrawerProfile_Home = findViewById(R.id.Back_ProfileSettings_Home);
        back_DrawerProfile_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Drawer_DietarySettings.this, Home.class);
                startActivity(intent);}
        });

        // Initialize RecyclerView
        dietarySettingsRecyclerView = findViewById(R.id.dietary_recyclerview);
        // Example data using ButtonItem instead of String
        List<ButtonItem> dietaryOptions = Arrays.asList(
                new ButtonItem("Vegetarian"),
                new ButtonItem("Vegan"),
                new ButtonItem("Pescatarian"),
                new ButtonItem("Flexitarian/Semi-Vegetarian"),
                new ButtonItem("Gluten-Free"),
                new ButtonItem("Lactose-Free"),
                new ButtonItem("Low-Carb"),
                new ButtonItem("Keto"),
                new ButtonItem("Paleo"),
                new ButtonItem("Whole30"),
                new ButtonItem("Low FODMAP"),
                new ButtonItem("Halal"),
                new ButtonItem("Kosher"),
                new ButtonItem("Nut-Free"),
                new ButtonItem("Egg-Free"),
                new ButtonItem("Dairy-Free"),
                new ButtonItem("Soy-Free"),
                new ButtonItem("Shellfish-Free"),
                new ButtonItem("Raw Vegan"),
                new ButtonItem("Low Sodium")
        );
        // Use FlexboxLayoutManager for a wrapping behavior
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        dietarySettingsRecyclerView.setLayoutManager(layoutManager);
        // Set the adapter
        ButtonAdapter adapter = new ButtonAdapter(dietaryOptions, this); // Adjust this to use your actual adapter
        dietarySettingsRecyclerView.setAdapter(adapter);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber) // Ensure this is the correct user ID
                .child("dietaryRestrictions");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (ButtonItem item : dietaryOptions) {
                    // Determine if the item is selected based on its presence in Firebase
                    if (dataSnapshot.hasChild(item.getButtonText())) {
                        item.setSelected(true);
                    } else {
                        item.setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter to refresh the UI
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        specialRequirements = findViewById(R.id.special_editText);
        applychanges = findViewById(R.id.applyChangesButton_Dietary);
        specialRequirementsTV = findViewById(R.id.current_SpecialRequirements);
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber) // Ensure this is the correct user ID
                .child("otherSpecialRestrictions");
        applychanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String specialRequirementsString = specialRequirements.getText().toString();
                if (specialRequirementsString.isEmpty()) {
                    return;}
                Ref.setValue(specialRequirementsString).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update the TextView to show the new special requirements
                        specialRequirementsTV.setText("Current Special Requirements: " + specialRequirementsString);
                    } else {
                        // Handle errors, e.g., show a toast or log
                        Log.e("Firebase Update", "Failed to update special requirements", task.getException());
                    }
                });
            }
        });

    }
    private void fetchAndUpdateSpecialRequirements() {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber) // Ensure this is the correct user ID
                .child("otherSpecialRestrictions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the value and update the TextView
                    String specialRequirements = snapshot.getValue(String.class);
                    specialRequirementsTV.setText("Current Special Requirements: " + specialRequirements);
                } else {
                    // Handle case where there is no special requirement set
                    specialRequirementsTV.setText("Current Special Requirements: None");
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                Log.e("Firebase Read", "Failed to read special requirements", error.toException());
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchAndUpdateSpecialRequirements(); // Fetch and display the current special requirements
    }
    @Override
    protected void onStart() {
        super.onStart();
        fetchAndUpdateSpecialRequirements(); // Fetch and display the current special requirements
    }
}