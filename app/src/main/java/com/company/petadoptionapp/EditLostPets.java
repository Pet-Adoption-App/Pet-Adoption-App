package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditLostPets extends AppCompatActivity {

    ImageView imageView;
    EditText etAge,etBreed,etAdress,etAbout;
    RadioGroup rgGender,rgType;
    RadioButton rbMale,rbFemale,rbDog,rbCat;
    Button btnUpadte,btnDelete;
    String petID;

    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lost_pets);

        imageView = findViewById(R.id.ivEditPetNGO);
        etAge = findViewById(R.id.etEditPetAgeNGO);
        etBreed = findViewById(R.id.etEditPetBreedNGO);
        etAdress = findViewById(R.id.etEditPetAddressNGO);
        etAbout = findViewById(R.id.etEditPetAboutNGO);
        rgGender = findViewById(R.id.rgEditPetGenderNGO);
        rgType = findViewById(R.id.rgEditPetTypeNGO);
        rbMale = findViewById(R.id.rbEditPetMaleNGO);
        rbFemale = findViewById(R.id.rbEditPetFemaleNGO);
        rbDog = findViewById(R.id.rbEditPetDogNGO);
        rbCat = findViewById(R.id.rbEditPetCatNGO);
        btnUpadte = findViewById(R.id.btnUpdatePetNGO);
        btnDelete = findViewById(R.id.btnDeletePetNGO);

        database = FirebaseDatabase.getInstance();
       // ref = database.getReference();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        Intent i = getIntent();
        petID = i.getStringExtra("petID");

        setValues();

        btnUpadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                send_Data_Approvel(reference.child("Lost_Approved_req").child(petID),
                        reference.child("Lost_Approval_req").child(petID));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("NGO").child(firebaseUser.getUid())
                        .child("NgoPets").child(petID).removeValue();
                reference.child("Lost_Approved_req").child(petID).removeValue();
            }
        });

    }

    public void send_Data_Approvel(DatabaseReference fromPath, final DatabaseReference toPath)
    {
        fromPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                toPath.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "Approval Failed", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Approved", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                reference.child("Lost_Approved_req").child(petID).removeValue();
                reference.child("NGO").child(firebaseUser.getUid()).child("NgoPets").child(petID).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateData()
    {
        String age = etAge.getText().toString();
        reference.child("Lost_Approved_req").child(petID).child("PetAge").setValue(age);
        String breed = etBreed.getText().toString();
        reference.child("Lost_Approved_req").child(petID).child("PetBreed").setValue(breed);
        String address = etAdress.getText().toString();
        reference.child("Lost_Approved_req").child(petID).child("PetAddress").setValue(address);
        String about = etAbout.getText().toString();
        reference.child("Lost_Approved_req").child(petID).child("PetAbout").setValue(about);
        String gender;
        if(rbMale.isChecked())
        {
            gender  = "Male";
        }
        else
        {
            gender = "Female";
        }
        reference.child("Lost_Approved_req").child(petID).child("PetGender").setValue(gender);
        String type;
        if(rbDog.isChecked())
        {
            type = "Dog";
        }
        else
        {
            type = "Cat";
        }
        reference.child("Lost_Approved_req").child(petID).child("PetType").setValue(type);
    }


    public void setValues()
    {
        reference.child("Lost_Approved_req").child(petID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String age = snapshot.child("PetAge").getValue().toString();
                etAge.setText(age);
                String imageUri = snapshot.child("ImageUrl").getValue().toString();
                Picasso.get().load(imageUri).into(imageView);
                String breed = snapshot.child("PetBreed").getValue().toString();
                etBreed.setText(breed);
                String address = snapshot.child("PetAddress").getValue().toString();
                etAdress.setText(address);

                String gender = snapshot.child("PetGender").getValue().toString();
                if(gender.equals("Male"))
                {
                    rbMale.setChecked(true);
                }
                else
                {
                    rbFemale.setChecked(true);
                }
                String type = snapshot.child("PetType").getValue().toString();
                if(type.equals("Dog"))
                {
                    rbDog.setChecked(true);
                }
                else
                {
                    rbCat.setChecked(true);
                }
                String about = snapshot.child("PetAbout").getValue().toString();
                etAbout.setText(about);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}