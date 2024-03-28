package com.example.aninterface;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.Fragments.FavouritesFragment;
import com.example.aninterface.Fragments.FeaturedFragment;
import com.example.aninterface.Fragments.HomeFragment;
import com.example.aninterface.Fragments.PantryFragment;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.aninterface.R;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    PantryFragment pantryFragment = new PantryFragment();
    FavouritesFragment favouritesFragment = new FavouritesFragment();
    FeaturedFragment featuredFragment = new FeaturedFragment();
    DrawerLayout drawerLayout;
    Button buttonProfileMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        NavigationView navigationView = findViewById(R.id.NavigationMenu_Drawer);
        View headerView = navigationView.getHeaderView(0);
        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        TextView textViewPhoneNumber = headerView.findViewById(R.id.profile_phonenumber);
        TextView textViewUsername = headerView.findViewById(R.id.profile_username);

        if (textViewPhoneNumber != null) {
            textViewPhoneNumber.setText(phoneNumber);
        } else {
            ;
        }

        drawerLayout = findViewById(R.id.drawer_profile_menu);
        buttonProfileMenu = findViewById(R.id.button_profile_menu);
        buttonProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

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