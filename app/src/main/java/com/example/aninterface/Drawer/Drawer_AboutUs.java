package com.example.aninterface.Drawer;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Drawer_AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_aboutus);

        ImageButton BackButton = findViewById(R.id.back_aboutus_home);
        BackButton.setOnClickListener(v -> {
            finish();
        });
    }

}