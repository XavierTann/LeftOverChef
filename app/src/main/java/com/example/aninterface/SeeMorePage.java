package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class SeeMorePage extends AppCompatActivity {

    private static String recipeDescription;
    private static String recipeImage;
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

        Intent intent = getIntent();
        SeeMorePage.recipeDescription = intent.getStringExtra("recipeDescription");
        SeeMorePage.recipeImage = intent.getStringExtra("recipeImage");

        textRecipeDescription.setText(recipeDescription);
        Picasso.get().load(recipeImage).into(imageRecipeImage);

        backButton.setOnClickListener(v -> {
            finish();

        });


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
