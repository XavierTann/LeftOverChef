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

        // Redirect to Recipes page //
        generateRecipes.setOnClickListener(v -> {
                    String ingredients = searchIngredient.getText().toString(); // Get the current text
                    String selectedDifficulty = spinnerDifficulty.getSelectedItem().toString();
                    Intent intent = new Intent(IngredientPage.this, RecipePage.class);
                    intent.putExtra("ingredients", ingredients);
                    intent.putExtra("difficulty", selectedDifficulty);
                    startActivity(intent);
                }
        );

    }
}

