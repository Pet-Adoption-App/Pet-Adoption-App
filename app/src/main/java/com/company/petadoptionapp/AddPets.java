package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPets extends AppCompatActivity {

    private ImageView ivAddPet;
    private EditText etAddPetName, etAddPetAge, etAddPetBreed,etAddPetAbout;
    private RadioGroup rgAddPetGender, rgAddPetType;
    private RadioButton rbAddPetMale, rbAddPetFemale, rbAddPetCat, rbAddPetDog;
    private Button btnAddPetSetLocation;
    private String petGen,petType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);

        ivAddPet = findViewById(R.id.ivAddPet);
        etAddPetName = findViewById(R.id.etAddPetName);
        etAddPetAge = findViewById(R.id.etAddPetAge);
        etAddPetBreed = findViewById(R.id.etAddPetBreed);
        rgAddPetGender = findViewById(R.id.rgAddPetGender);
        rgAddPetType = findViewById(R.id.rgAddPetType);
        rbAddPetMale = findViewById(R.id.rbAddPetMale);
        rbAddPetFemale = findViewById(R.id.rbAddPetFemale);
        rbAddPetDog = findViewById(R.id.rbAddPetDog);
        rbAddPetCat = findViewById(R.id.rbAddPetCat);
        btnAddPetSetLocation = findViewById(R.id.btnAddPetSetLocation);
        etAddPetAbout = findViewById(R.id.etAddPetAbout);

        btnAddPetSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbAddPetMale.isChecked()) {
                    petGen = rbAddPetMale.getText().toString();
                } else if (rbAddPetFemale.isChecked()) {
                    petGen = rbAddPetFemale.getText().toString();
                }

                if (rbAddPetCat.isChecked()) {
                    petType = rbAddPetCat.getText().toString();
                } else if (rbAddPetDog.isChecked()) {
                    petType = rbAddPetDog.getText().toString();
                }
                if(petGen != "" && petType != ""){
                    insertData(petGen,petType);
                }else{
                    Toast.makeText(AddPets.this, "Check radio button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void insertData(String setPetGen,String setPetType){

        String name = etAddPetName.getText().toString();
        String age = etAddPetAge.getText().toString();
        String breed = etAddPetBreed.getText().toString();
        String about = etAddPetAbout.getText().toString();

        if(name.isEmpty()){
            etAddPetName.setError("This field cannot be empty");
            etAddPetName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            etAddPetAge.setError("This field cannot be empty");
            etAddPetAge.requestFocus();
            return;
        }

        if(breed.isEmpty()){
            etAddPetBreed.setError("This field cannot be empty");
            etAddPetBreed.requestFocus();
            return;
        }

        if(about.isEmpty()){
            etAddPetAbout.setError("This field cannot be empty");
            etAddPetAbout.requestFocus();
            return;
        }

        Intent i = new Intent(AddPets.this,SetPetLocation.class);
        i.putExtra("PetName",name);
        i.putExtra("PetAge",age);
        i.putExtra("PetBreed",breed);
        i.putExtra("PetAbout",about);
        i.putExtra("PetGender",setPetGen);
        i.putExtra("PetType",setPetType);

        startActivity(i);
    }
}