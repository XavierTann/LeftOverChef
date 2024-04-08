package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.HelperClass.Recipe;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SeeMorePage extends AppCompatActivity {

    private static String recipeDescription;
    private static String recipeImage;
    private static String recipeName;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Use the same system UI visibility code as in onCreate
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.see_more);

        ImageView imageRecipeImage = findViewById(R.id.image_individualRecipe_recipeImage);
        TextView textRecipeDescription = findViewById(R.id.text_individualRecipe_recipeDescription);
        ImageButton backButton = findViewById(R.id.back_individualingredientPage_recipepage);
        ImageView favouriteButton = findViewById(R.id.image_seeMore_favourite);

        Intent intent = getIntent();
        SeeMorePage.recipeDescription = intent.getStringExtra("recipeDescription");
        SeeMorePage.recipeImage = intent.getStringExtra("recipeImage");

        textRecipeDescription.setText(recipeDescription);
        Picasso.get().load(recipeImage).into(imageRecipeImage);

        backButton.setOnClickListener(v -> {
            finish();
        });

        favouriteButton.setOnClickListener(v -> {
            addToFavorites(favouriteButton, recipeDescription, recipeImage);
        });


    }

    private void addToFavorites(ImageView favouriteButton, final String generatedString, String imageUrl) {
        favouriteButton.setOnClickListener(v -> {
            FirebaseDatabase database;
            DatabaseReference reference;
            String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users").child(phoneNumber);

            // Create a reference to the "favourites" node under the phone number node
            DatabaseReference recipeRef = reference.child("favourites");

            // Create a reference to the new recipe node using the generated key
            String sanitizedFoodName = RecipePage.foodName.replaceAll("[^a-zA-Z0-9]", "_");
            DatabaseReference newRecipeRef = recipeRef.child(sanitizedFoodName);

            // Now you can set the value of the new recipe node
            // For example, you can set recipe details including name, instructions, ingredients, cooking time, and difficulty
            Recipe recipe = new Recipe(sanitizedFoodName, generatedString, RecipePage.ingredients, RecipePage.cookingTime, RecipePage.difficulty, imageUrl);
            newRecipeRef.setValue(recipe).addOnSuccessListener(aVoid -> {
                // Show a toast message indicating that the recipe has been favorited successfully
                Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                // Show a toast message if there's an error
                Toast.makeText(getApplicationContext(), "Failed to add to Favorites", Toast.LENGTH_SHORT).show();
            });
        });
    }


}


