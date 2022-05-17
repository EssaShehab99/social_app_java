package com.example.it342_project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.it342_project.models.User;
import com.example.it342_project.pdo.UserPDO;
import com.example.it342_project.shared.Shared;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);

        Button buttonL2 = (Button) findViewById(R.id.buttonL2);
        buttonL2.setOnClickListener(view -> {
            if (TextUtils.isEmpty(email.getText()))
                email.setError("Enter Value");
            else if (TextUtils.isEmpty(password.getText()))
                password.setError("Enter Value");
            else {
                try {
                    db.collection("users").document(email.getText().toString()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (String.valueOf(Objects.requireNonNull(document.getData()).get("password")).equals(password.getText().toString())) {
                                    UserPDO.user = new User().fromMap(document.getData(), document.getId());
                                    openProfile();
                                    finish();
                                } else {
                                    Shared.showSnackBar(findViewById(android.R.id.content), "User name or password not correct");
                                }
                            } else {
                                Shared.showSnackBar(findViewById(android.R.id.content), "User name or password not correct");
                            }
                        } else {
                            Shared.showSnackBar(findViewById(android.R.id.content), "Connect Error");
                        }
                    });
                } catch (Exception e) {
                }
            }
        });
    }

    public void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}