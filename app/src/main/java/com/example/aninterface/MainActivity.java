package com.example.aninterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get Started Button redirects to Login Page
        Button button = findViewById(R.id.btn_activity_main_getStarted);
            button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
        });
    }}
