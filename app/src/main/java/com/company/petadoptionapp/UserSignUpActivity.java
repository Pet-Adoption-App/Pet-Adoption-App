package com.company.petadoptionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class UserSignUpActivity extends AppCompatActivity {

    private ImageView ivProfile;
    private TextView tvName, tvDob, tvMobile, tvGender;
    private EditText etMail, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private ProgressBar progressBarSU;

    private boolean imageControl = false;
    private String name,dob,gender,mobile;
    private String aadhaarNo;

    Uri imageUri;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference AadhaarRef;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        getWindow().setStatusBarColor(ContextCompat.getColor(UserSignUpActivity.this,R.color.black));

        ivProfile = findViewById(R.id.ivUploadPhoto);
        tvName = findViewById(R.id.tvSUpName);
        tvDob = findViewById(R.id.tvSUpDob);
        tvMobile = findViewById(R.id.tvSUpMobile);
        tvGender = findViewById(R.id.tvSUpGender);
        etConfirmPassword = findViewById(R.id.etSignUpConfirmPassword);
        etPassword = findViewById(R.id.etSignUpPassword);
        etMail = findViewById(R.id.etSignUpEmail);
        btnSignUp = findViewById(R.id.btnLogin);
        progressBarSU = findViewById(R.id.progressBarSU);

        //getting Aadhaar Number from previous Activity
        Intent intent = getIntent();
        aadhaarNo = intent.getStringExtra("aadhaarNo");

        database = FirebaseDatabase.getInstance();
        AadhaarRef = database.getReference().child("AadhaarAPI").child(aadhaarNo);
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        progressBarSU.setVisibility(View.GONE);

        AadhaarRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AadhaarModel model = snapshot.getValue(AadhaarModel.class);

                if(model != null){
                    name = model.name;
                    dob = model.dob;
                    gender = model.gender;
                    mobile = model.mobile;

                    tvName.setText(name);
                    tvDob.setText(dob);
                    tvMobile.setText(mobile);
                    tvGender.setText(gender);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }


    //user registration using firebase
    public void registerUser(){
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if(mail.isEmpty()){
            etMail.setError("Email can't be empty");
            etMail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            etMail.setError("Please provide valid Email");
            etMail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Password can't be empty");
            etPassword.requestFocus();
            return;
        }

        if(password.length() < 8){
            etPassword.setError("At least 8 character required");
            etPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            etConfirmPassword.setError("Confirm Password can't be empty");
            etConfirmPassword.requestFocus();
            return;
        }

        if(!password.equals(confirmPassword)){
            etConfirmPassword.setError("Confirm Password is not matching with Password");
            etConfirmPassword.requestFocus();
            return;
        }

        progressBarSU.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(imageControl){
                        UUID randomID = UUID.randomUUID();
                        String imageName = "images/"+randomID+".jpg";
                        storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                StorageReference myStorageRef = firebaseStorage.getReference(imageName);
                                myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        UserModel user = new UserModel(name,dob,mobile,gender,mail,uri.toString());
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(UserSignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    progressBarSU.setVisibility(View.GONE);
                                                    FirebaseDatabase.getInstance().getReference("VerifiedAadhaar").child(aadhaarNo).setValue(true);
                                                    Intent intent = new Intent(UserSignUpActivity.this,MainActivity.class);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(UserSignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                                    progressBarSU.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }else{
                        UserModel user = new UserModel(name,dob,mobile,gender,mail,"null");
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(UserSignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    progressBarSU.setVisibility(View.GONE);
                                    FirebaseDatabase.getInstance().getReference("VerifiedAadhaar").child(aadhaarNo).setValue(true);
                                    Intent intent = new Intent(UserSignUpActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(UserSignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    progressBarSU.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    //When user click on upload photo this method will run
    public void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    //Checks whether user selected any image or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode ==RESULT_OK && data != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(ivProfile);
            imageControl = true;
        }else {
            imageControl = false;
        }
    }
}