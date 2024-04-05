package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.Drawer.Drawer_AboutUs;
import com.example.aninterface.Drawer.Drawer_DietarySettings;
import com.example.aninterface.Drawer.Drawer_UserSettings;
import com.example.aninterface.Fragments.FavouritesFragment;
import com.example.aninterface.Fragments.FeaturedFragment;
import com.example.aninterface.Fragments.HomeFragment;
import com.example.aninterface.Fragments.PantryFragment;
import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.example.aninterface.R;

import androidx.core.view.GravityCompat;
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
    ImageButton buttonProfileMenu;
    NavigationView navigationView;
    FloatingActionButton home_cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        String phoneNumber = SharedPreferencesUtil.getPhoneNumber(this);
        String userName = SharedPreferencesUtil.getUserName(this);


        navigationView = findViewById(R.id.NavigationMenu_Drawer);
        View headerView = navigationView.getHeaderView(0);


        //Setting PhoneNumber and Name in Drawer
        TextView textViewPhoneNumber_Drawer = headerView.findViewById(R.id.profile_phonenumber);
        TextView textViewUserName_Drawer = headerView.findViewById(R.id.profile_username);
        if (textViewPhoneNumber_Drawer != null) {
            textViewPhoneNumber_Drawer.setText(phoneNumber);}
        if (textViewUserName_Drawer != null) {
            textViewUserName_Drawer.setText(userName);}
        TextView textViewUserName_Home = findViewById(R.id.textView_home_username);
        if (textViewUserName_Home != null) {
            textViewUserName_Home.setText(userName);}
        drawerLayout = findViewById(R.id.drawer_profile_menu);
        buttonProfileMenu = findViewById(R.id.button_profile_menu);

        //Drawer Open/Close
        buttonProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        //In Drawer, Open User Settings Page through a Button
        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId()== R.id.profile_settings){
                // Open the User Settings Activity
                Intent intent = new Intent(Home.this, Drawer_UserSettings.class);
                startActivity(intent);
                // Close the drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            else if(item.getItemId() == R.id.profile_dietary){
                Intent intent = new Intent(Home.this, Drawer_DietarySettings.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            else if(item.getItemId() == R.id.profile_aboutus){
                Intent intent = new Intent(Home.this, Drawer_AboutUs.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            else if(item.getItemId() == R.id.profile_logout){
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });

        home_cameraButton = findViewById(R.id.fab);
        home_cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, CameraRecognition.class);
                startActivity(intent);
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