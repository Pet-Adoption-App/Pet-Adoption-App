package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.company.petadoptionapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    LostPetFragment lostPetFragment = new LostPetFragment();
    MessageFragment messageFragment = new MessageFragment();
    Profile_Fragment profileFragment = new Profile_Fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().findItem(R.id.placeholder).setEnabled(false);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeID:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.lostPet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,lostPetFragment).commit();
                        return true;
                    case R.id.message:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,messageFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}