package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class IngredientPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_page);

        EditText searchIngredient = findViewById(R.id.edit_ingredientPage_searchIngredient);
        Button generateRecipes = findViewById(R.id.btn_ingredientPage_generateRecipes);
        Spinner spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        Spinner spinnerCookingTime = findViewById(R.id.spinner_duration);
        Spinner spinnerCuisine = findViewById(R.id.spinner_ingredientPage_cuisine);
        Spinner spinnerDietaryRequirements = findViewById(R.id.spinner_ingredientPage_dietaryRequirements);

        // Redirect to Recipes page //
        generateRecipes.setOnClickListener(v -> {
                    String ingredients = searchIngredient.getText().toString(); // Get the current text
                    String selectedDifficulty = spinnerDifficulty.getSelectedItem().toString();
                    String cookingTime = spinnerCookingTime.getSelectedItem().toString();
                    String cuisine = spinnerCuisine.getSelectedItem().toString();
                    String dietaryRequirements = spinnerDietaryRequirements.getSelectedItem().toString();

                    Intent intent = getIntent();
                    String phoneNumber = intent.getStringExtra("phoneNumber");
                    intent = new Intent(IngredientPage.this, RecipePage.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("ingredients", ingredients);
                    intent.putExtra("difficulty", selectedDifficulty);
                    intent.putExtra("cookingTime", cookingTime);
                    intent.putExtra("cuisine", cuisine);
                    intent.putExtra("dietaryRequirements", dietaryRequirements);
                    startActivity(intent);
                }
        );

    }
}

