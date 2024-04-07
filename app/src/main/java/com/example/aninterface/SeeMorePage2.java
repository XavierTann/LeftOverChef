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

    private static String recipeDescription;
    private static String recipeImage;
    private static String recipeName;
    private static String userPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.see_more2);

        ImageView imageRecipeImage = findViewById(R.id.image_individualRecipe_recipeImage2);
        TextView textRecipeDescription = findViewById(R.id.text_individualRecipe_recipeDescription2);
        ImageButton backButton = findViewById(R.id.back_individualingredientPage_recipepage2);
//        userPhoneNumber = SharedPreferencesUtil.getPhoneNumber(getApplicationContext());
        userPhoneNumber = "85000421";
//        Intent intent = getIntent();
//        SeeMorePage2.recipeDescription = intent.getStringExtra("recipeDescription");
//        SeeMorePage2.recipeImage = intent.getStringExtra("recipeImage");

        recipeName = "Potato_Gnocchi";

        textRecipeDescription.setText(recipeDescription);
        Picasso.get().load(recipeImage).into(imageRecipeImage);

        backButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, HomePage.class);
            startActivity(intent2);

        });

        fetchRecipeDetails(recipeName);

    }



    public void fetchRecipeDetails(String recipeName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("users").child(userPhoneNumber).child("recipes").orderByChild("recipeName").equalTo(recipeName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Assuming there's only one recipe with the given name
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    recipeDescription = recipeSnapshot.child("recipeDescription").getValue(String.class);
                    recipeImage = recipeSnapshot.child("imageUrl").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur
            }
        });
    }


}

