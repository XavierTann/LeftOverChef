package com.example.aninterface;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aninterface.Fragments.FavouritesFragment;
import com.example.aninterface.Fragments.FeaturedFragment;
import com.example.aninterface.Fragments.HomeFragment;
import com.example.aninterface.Fragments.PantryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.aninterface.R;


public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    PantryFragment pantryFragment = new PantryFragment();
    FavouritesFragment favouritesFragment = new FavouritesFragment();
    FeaturedFragment featuredFragment = new FeaturedFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pantryFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                        return true;}
                else if (item.getItemId() == R.id.menu_pantry) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pantryFragment).commit();
                        return true;}
                else if (item.getItemId() == R.id.menu_favourite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,favouritesFragment).commit();
                    return true;}
                else if (item.getItemId() == R.id.menu_featured) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,featuredFragment).commit();
                    return true;}
                return false;
            }
        });
    }
}