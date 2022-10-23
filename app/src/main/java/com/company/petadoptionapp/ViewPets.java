package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewPets extends AppCompatActivity {
    String receivePetID;
    DatabaseReference petReference;
    private ImageView ivViewPets, ivViewPetsLocation;
    private TextView tvAboutViewPets, tvPetNameViewPets, tvPetAgeViewPets, tvPetBreedViewPets, tvPetGenderViewPets, tvViewPetsAddress;
    private Button btnCallViewPets, btnChatViewPets;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewPets.this, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pets);

        ivViewPets = findViewById(R.id.ivViewPet);
        tvAboutViewPets = findViewById(R.id.tvAboutViewPets);
        tvPetNameViewPets = findViewById(R.id.tvPetNameViewPets);
        tvPetAgeViewPets = findViewById(R.id.tvPetAgeViewPets);
        tvPetBreedViewPets = findViewById(R.id.tvPetBreedViewPets);
        tvPetGenderViewPets = findViewById(R.id.tvPetGenderViewPets);
        btnCallViewPets = findViewById(R.id.btnCallViewPets);
        btnChatViewPets = findViewById(R.id.btnChatViewPets);
        ivViewPetsLocation = findViewById(R.id.ivViewPetsLocation);
        tvViewPetsAddress = findViewById(R.id.tvPetAddressViewPets);

        petReference = FirebaseDatabase.getInstance().getReference().child("Approved_req");
        receivePetID = getIntent().getStringExtra("view_pet_id");
        petReference.child(receivePetID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pet_Model pet = snapshot.getValue(Pet_Model.class);

                tvPetNameViewPets.setText(pet.getPetName());
                tvAboutViewPets.setText(pet.getPetAbout());
                tvPetAgeViewPets.setText(pet.getPetAge());
                tvPetBreedViewPets.setText(pet.getPetBreed());
                Picasso.get().load(pet.getImageUrl()).into(ivViewPets);
                tvPetGenderViewPets.setText(pet.getPetGender());
                String address = pet.getCity()+", "+pet.getState()+", "+pet.getCountry();
                tvViewPetsAddress.setText(address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivViewPetsLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewPets.this,ViewPetsLocation.class);
                i.putExtra("view_pet_id",receivePetID);
                startActivity(i);
            }
        });

    }
}