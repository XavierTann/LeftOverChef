package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SeeMorePage2 extends AppCompatActivity {

    private String recipeDescription;
    private String recipeImage;
    private String recipeName;
    private String userPhoneNumber;

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
        setContentView(R.layout.see_more2);


//    userPhoneNumber = SharedPreferencesUtil.getPhoneNumber(getApplicationContext());
//    Intent intent = getIntent();
//    SeeMorePage2.recipeDescription = intent.getStringExtra("recipeDescription");
//    SeeMorePage2.recipeImage = intent.getStringExtra("recipeImage");

        userPhoneNumber = SharedPreferencesUtil.getPhoneNumber(getApplicationContext());
        recipeName = getIntent().getStringExtra("recipeName");
        fetchRecipeDetails(recipeName);

        ImageButton backButton = findViewById(R.id.back_individualingredientPage_recipepage2);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    public void fetchRecipeDetails(String recipeName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("users").child(userPhoneNumber).child("recipe");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Assuming there's only one recipe with the given name
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    if (recipeSnapshot.child("recipeName").getValue(String.class).equals(recipeName)) {
                        recipeDescription = recipeSnapshot.child("cookingInstructions").getValue(String.class);
                        recipeImage = recipeSnapshot.child("imageUrl").getValue(String.class);

                        //Initialize the views
                        ImageView imageRecipeImage = findViewById(R.id.image_individualRecipe_recipeImage2);
                        TextView textRecipeDescription = findViewById(R.id.text_individualRecipe_recipeDescription2);

                        // Set the text and image after fetching the recipe details
                        textRecipeDescription.setText(recipeDescription);
                        Picasso.get().load(recipeImage).into(imageRecipeImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
    }



}

