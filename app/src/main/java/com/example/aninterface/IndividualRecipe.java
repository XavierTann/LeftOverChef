package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class IndividualRecipe extends AppCompatActivity {

    private static String recipeDescription;
    private static String recipeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.individual_recipe);

        ImageView imageRecipeImage = findViewById(R.id.image_individualRecipe_recipeImage);
        TextView textRecipeDescription = findViewById(R.id.text_individualRecipe_recipeDescription);

        Intent intent = getIntent();
        IndividualRecipe.recipeDescription = intent.getStringExtra("recipeDescription");
        IndividualRecipe.recipeImage = intent.getStringExtra("recipeImage");

        textRecipeDescription.setText(recipeDescription);
        Picasso.get().load(recipeImage).into(imageRecipeImage);
    }

    ;
}

//        // Get Started Button redirects to Login Page
//        Button button = findViewById(R.id.btn_activity_main_getStarted);
//        button.setOnClickListener(v -> {
//                    Intent intent = new Intent(MainActivity.this, Login.class);
//                    startActivity(intent);
//                }
//        );
