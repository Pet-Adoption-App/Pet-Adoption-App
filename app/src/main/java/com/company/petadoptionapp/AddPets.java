package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPets extends AppCompatActivity {

    private ImageView ivAddPet;
    private EditText etAddPetName, etAddPetAge, etAddPetBreed,etAddPetAbout;
    private RadioGroup rgAddPetGender, rgAddPetType;
    private RadioButton rbAddPetMale, rbAddPetFemale, rbAddPetCat, rbAddPetDog;
    private Button btnAddPetSetLocation, btnAddPet;
    String petGen,petType;

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
        btnAddPet = findViewById(R.id.btnAddPet);
        btnAddPetSetLocation = findViewById(R.id.btnAddPetSetLocation);
        etAddPetAbout = findViewById(R.id.etAddPetAbout);

        btnAddPetSetLocation.setOnClickListener(view -> {
            startActivity(new Intent(this, SetPetLocation.class));
        });

        btnAddPet.setOnClickListener(new View.OnClickListener() {
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
                insertData(petGen,petType);
            }
        });
    }


    private void insertData(String setPetGen,String setPetType){
        Map<String,Object>map=new HashMap<>();
        map.put("etAddPetName",etAddPetName.getText().toString());
        map.put("etAddPetAge",etAddPetAge.getText().toString());
        map.put("etAddPetBreed",etAddPetBreed.getText().toString());
        map.put("etAddPetAbout",etAddPetAbout.getText().toString());
        map.put("rgAddPetGender",setPetGen);
        map.put("rgAddPetType",setPetType);


        FirebaseDatabase.getInstance().getReference().child("Approval_req").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddPets.this, "Pet Add for Approval", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPets.this, "Can't Add Pet", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}