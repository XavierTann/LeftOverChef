package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class pantry_ingredient_page extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_ingredient_page);

        TextView textView1 = findViewById(R.id.textView24);
        TextView textView2 = findViewById(R.id.textView25);

        Intent intent = getIntent();
        String ingredientList = intent.getStringExtra("ingredientList");

        textView1.setText(ingredientList);

    }
}
