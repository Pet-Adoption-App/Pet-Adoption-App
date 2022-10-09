package com.company.petadoptionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginNGO extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ngo);

        btn = findViewById(R.id.button);

        btn.setOnClickListener(view -> {
            startActivity(new Intent(this,SignUpNGO.class));
        });
    }
}