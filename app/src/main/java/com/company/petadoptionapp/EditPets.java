package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditPets extends AppCompatActivity {

    private ImageView ivEditPet;
    private EditText etEditPetName, etEditPetAge, etEditPetBreed, etEditPetAbout;
    private RadioGroup rgEditPetGender, rgEditPetType;
    private RadioButton rbEditPetMale, rbEditPetFemale, rbEditPetDog, rbEditPetCat;
    private Button btnUpdatePet, btnDeletePet;
    private ConstraintLayout clEditPets;

    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pets);
        ivEditPet = findViewById(R.id.ivEditPet);
        etEditPetName = findViewById(R.id.etEditPetName);
        etEditPetBreed = findViewById(R.id.etEditPetBreed);
        etEditPetAbout = findViewById(R.id.etEditPetAbout);
        etEditPetAge = findViewById(R.id.etEditPetAge);
        rgEditPetGender = findViewById(R.id.rgEditPetGender);
        rgEditPetType = findViewById(R.id.rgEditPetType);
        rbEditPetMale = findViewById(R.id.rbEditPetMale);
        rbEditPetFemale = findViewById(R.id.rbEditPetFemale);
        rbEditPetDog = findViewById(R.id.rbEditPetDog);
        rbEditPetCat = findViewById(R.id.rbEditPetCat);
        btnDeletePet = findViewById(R.id.btnDeletePet);
        btnUpdatePet = findViewById(R.id.btnUpdatePet);
        clEditPets = findViewById(R.id.clEditPets);

        clEditPets.setVisibility(View.INVISIBLE);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Approved_req");

        Intent i = getIntent();
        String petID = i.getStringExtra("petID");

        setValues(petID);

        btnUpdatePet.setOnClickListener(view -> {
            // Write code here charchil


            alert(petID);
        });
    }

    private void alert(String petID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPets.this);
        builder.setMessage("Do you want to Update Pet Location?");
        builder.setTitle("Update Location");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(EditPets.this,UpdatePetLocation.class);
                intent.putExtra("petID",petID);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(EditPets.this,MainActivity.class));
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setValues(String petID){
        ref.child(petID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pet_Model pet = snapshot.getValue(Pet_Model.class);
                Picasso.get().load(pet.getImageUrl()).into(ivEditPet);
                etEditPetName.setText(pet.getPetName());
                etEditPetAge.setText(pet.getPetAge());
                etEditPetAbout.setText(pet.getPetAbout());
                etEditPetBreed.setText(pet.getPetBreed());

                if(pet.getPetGender().equals("Male")){
                    rbEditPetMale.setChecked(true);
                }else{
                    rbEditPetFemale.setChecked(true);
                }

                if(pet.getPetType().equals("Dog")){
                    rbEditPetDog.setChecked(true);
                }else{
                    rbEditPetCat.setChecked(true);
                }

                clEditPets.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}