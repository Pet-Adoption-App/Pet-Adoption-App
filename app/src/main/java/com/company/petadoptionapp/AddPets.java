package com.company.petadoptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddPets extends AppCompatActivity {

    private ImageView ivAddPet;
    private EditText etAddPetName, etAddPetAge, etAddPetBreed;
    private RadioGroup rgAddPetGender, rgAddPetType;
    private RadioButton rbAddPetMale, rbAddPetFemale, rbAddPetCat, rbAddPetDog;
    private Button btnAddPetSetLocation, btnAddPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);

        ivAddPet = findViewById(R.id.ivAddPet);
        etAddPetName = findViewById(R.id.etAddPetName);
        etAddPetAge = findViewById(R.id.etAddPetAge);
        etAddPetBreed = findViewById(R.id.etAddPetBreed);
        rgAddPetGender =findViewById(R.id.rgAddPetGender);
        rgAddPetType = findViewById(R.id.rgAddPetType);
        rbAddPetMale = findViewById(R.id.rbAddPetMale);
        rbAddPetFemale = findViewById(R.id.rbAddPetFemale);
        rbAddPetDog = findViewById(R.id.rbAddPetDog);
        rbAddPetCat = findViewById(R.id.rbAddPetCat);
        btnAddPet = findViewById(R.id.btnAddPet);
        btnAddPetSetLocation = findViewById(R.id.btnAddPetSetLocation);

        btnAddPetSetLocation.setOnClickListener(view -> {
            startActivity(new Intent(this,SetPetLocation.class));
        });
    }
}