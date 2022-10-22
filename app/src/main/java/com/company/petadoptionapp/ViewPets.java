package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private ImageView ivViewPets;
    private TextView tvAboutViewPets, tvPetNameViewPets, tvPetAgeViewPets, tvPetBreedViewPets, tvPetGenderViewPets;
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}