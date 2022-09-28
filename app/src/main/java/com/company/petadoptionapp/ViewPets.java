package com.company.petadoptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPets extends AppCompatActivity {

    private ImageView ivViewPets;
    private TextView tvAboutViewPets, tvPetNameViewPets, tvPetAgeViewPets, tvPetBreedViewPets, tvPetGenderViewPets;
    private Button btnCallViewPets, btnChatViewPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pets);

        ivViewPets = findViewById(R.id.ivViewPets);
        tvAboutViewPets = findViewById(R.id.tvAboutViewPets);
        tvPetNameViewPets = findViewById(R.id.tvPetNameViewPets);
        tvPetAgeViewPets = findViewById(R.id.tvPetAgeViewPets);
        tvPetBreedViewPets = findViewById(R.id.tvPetBreedViewPets);
        tvPetGenderViewPets = findViewById(R.id.tvPetGenderViewPets);
        btnCallViewPets = findViewById(R.id.btnCallViewPets);
        btnChatViewPets = findViewById(R.id.btnChatViewPets);

    }
}