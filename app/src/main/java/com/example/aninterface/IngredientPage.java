package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.HelperClass.FirebaseFunctions;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientPage extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_page);
        fetchAndUpdateSpecialRequirements();
        fetchAndUpdateDietaryRequirements();

        String ingredientsFromCamera = getIntent().getStringExtra("ingredientString");
        String ingredientsFromPantry = getIntent().getStringExtra("ingredientsFromPantry");
        TextView txt_predictedIngredients = findViewById(R.id.txt_ingredientPage_predictedIngredients);
        if (ingredientsFromCamera != null) {
            txt_predictedIngredients.setText("The camera has scanned: " + ingredientsFromCamera + "Enter the other ingredients it is missing, as well as select your preferences below.");
        } else {
            txt_predictedIngredients.setText("No ingredients were scanned from the camera. Take a picture of your ingredients to get started, or just type in the search bar below");
        }
        if (ingredientsFromPantry != null) {
            txt_predictedIngredients.setText("The pantry has scanned: " + ingredientsFromPantry + "Enter the other ingredients it is missing, as well as select your preferences below.");
        } else {
            txt_predictedIngredients.setText("No ingredients were scanned from the pantry. Take a picture of your ingredients to get started, or just type in the search bar below");
        }

        EditText searchIngredient = findViewById(R.id.edit_ingredientPage_searchIngredient);
        Button generateRecipes = findViewById(R.id.btn_ingredientPage_generateRecipes);
        Spinner spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        Spinner spinnerCookingTime = findViewById(R.id.spinner_duration);
        Spinner spinnerCuisine = findViewById(R.id.spinner_ingredientPage_cuisine);
        EditText editDietaryRequirements = findViewById(R.id.edit_ingredientPage_dietaryRequirements);
        EditText editSpecialRequirements =  findViewById(R.id.edit_ingredientPage_specialRequirements);

        generateRecipes.setOnClickListener(v -> {
            // Getting ingredients and filters
            String ingredients = searchIngredient.getText().toString();
            String selectedDifficulty = spinnerDifficulty.getSelectedItem().toString();
            String cookingTime = spinnerCookingTime.getSelectedItem().toString();
            String cuisine = spinnerCuisine.getSelectedItem().toString();
            String dietaryRequirements = editDietaryRequirements.getText().toString();
            String specialRequirements = editSpecialRequirements.getText().toString();
            String phoneNumber = SharedPreferencesUtil.getPhoneNumber(IngredientPage.this);
            Intent intent = new Intent(IngredientPage.this, RecipePage.class);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("difficulty", selectedDifficulty);
            intent.putExtra("cookingTime", cookingTime);
            intent.putExtra("cuisine", cuisine);
            intent.putExtra("dietaryRequirements", dietaryRequirements);
            intent.putExtra("specialRequirements", specialRequirements);
            intent.putExtra("ingredientsFromCamera", ingredientsFromCamera);
            intent.putExtra("ingredientsFromPantry", ingredientsFromPantry);
            startActivity(intent);
        });

        ImageButton back_ingredientPage_Camera = findViewById(R.id.back_ingredientPage_Camera);
        back_ingredientPage_Camera.setOnClickListener(v -> {
                Intent intent2 = new Intent(IngredientPage.this, CameraRecognition.class);
                startActivity(intent2);
        });
    }

    private void fetchAndUpdateSpecialRequirements() {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        EditText editSpecialRequirementsSetText = findViewById(R.id.edit_ingredientPage_specialRequirements);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber)
                .child("otherSpecialRestrictions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String specialRequirements = snapshot.getValue(String.class);
                    editSpecialRequirementsSetText.setText(specialRequirements);
                } else {;}
            }
            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                Log.e("Firebase Read", "Failed to read special requirements", error.toException());
            }
        });
    }
    private void fetchAndUpdateDietaryRequirements() {
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        EditText editDietaryRequirementsSetText = findViewById(R.id.edit_ingredientPage_dietaryRequirements);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users")
                .child(phoneNumber)
                .child("dietaryRestrictions");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dietaryRestictions = "| ";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dietaryRequirement = snapshot.getValue(String.class);
                    dietaryRestictions = dietaryRestictions + dietaryRequirement + " | ";
                }
                editDietaryRequirementsSetText.setText(dietaryRestictions);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

