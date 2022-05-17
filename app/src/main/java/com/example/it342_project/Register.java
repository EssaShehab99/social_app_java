package com.example.it342_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.it342_project.models.User;
import com.example.it342_project.pdo.UserPDO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText email, password,confPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        confPassword = findViewById(R.id.conf_password_et);
        Button buttonR2 = (Button) findViewById(R.id.buttonR2);
        buttonR2.setOnClickListener(view ->{
            if (TextUtils.isEmpty(email.getText()))
                email.setError("Enter Value");
            else if (TextUtils.isEmpty(password.getText()))
                password.setError("Enter Value");
            else if (!confPassword.getText().toString().equals(password.getText().toString()))
                confPassword.setError("Not match");
            else {
                UserPDO.user = new User("", email.getText().toString(), password.getText().toString());
                signUp();
            }
        });
    }
    public void signUp() {
        db.collection("users").document(email.getText().toString())
                .set(UserPDO.user.toMap())
                .addOnSuccessListener(aVoid -> {
                    openProfile2();
                    finish();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    public void openProfile2(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }}