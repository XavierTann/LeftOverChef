package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.HelperClass.SharedPreferencesUtil;

import org.w3c.dom.Text;

public class IngredientPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_page);

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
        Spinner spinnerDietaryRequirements = findViewById(R.id.spinner_ingredientPage_dietaryRequirements);
        EditText editSpecialRequirements =  findViewById(R.id.edit_ingredientPage_specialRequirements);


        generateRecipes.setOnClickListener(v -> {
                    // Getting ingredients and filters
                    String ingredients = searchIngredient.getText().toString();
                    String selectedDifficulty = spinnerDifficulty.getSelectedItem().toString();
                    String cookingTime = spinnerCookingTime.getSelectedItem().toString();
                    String cuisine = spinnerCuisine.getSelectedItem().toString();
                    String dietaryRequirements = spinnerDietaryRequirements.getSelectedItem().toString();
                    String specialRequirements = editSpecialRequirements.getText().toString();

            String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
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


                }
        );

    }
}

