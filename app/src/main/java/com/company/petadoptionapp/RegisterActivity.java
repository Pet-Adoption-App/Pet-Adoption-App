package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText etAadhaarNo;
    private Button btnVerify;
    private TextView tvLoginHere;

    FirebaseDatabase database;
    DatabaseReference apiRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterActivity.this,R.color.black));

        etAadhaarNo = findViewById(R.id.etAadhaarNo);
        btnVerify = findViewById(R.id.btnVerify);
        tvLoginHere = findViewById(R.id.tvLoginHere);

        database = FirebaseDatabase.getInstance();
        apiRef = database.getReference().child("AadhaarAPI");

        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification();
            }
        });

    }

    public void verification(){
        String aadhaarNo = etAadhaarNo.getText().toString();

        if(aadhaarNo.isEmpty()){
            etAadhaarNo.setError("This field can't be empty");
            etAadhaarNo.requestFocus();
            return;
        }
        if(aadhaarNo.length() != 12){
            etAadhaarNo.setError("12 Digits Required");
            etAadhaarNo.requestFocus();
            return;
        }

        //Aadhaar Number Verification
        apiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(aadhaarNo)){
                    // Checks whether the Aadhaar is already verified or not
                    FirebaseDatabase.getInstance().getReference("VerifiedAadhaar").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(aadhaarNo)){
                                Toast.makeText(RegisterActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this,UserSignUpActivity.class);
                                intent.putExtra("aadhaarNo",aadhaarNo);
                                startActivity(intent);
                            }else {
                                Toast.makeText(RegisterActivity.this, "Already Verified", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Invalid Aadhaar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}