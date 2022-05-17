package com.example.it342_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.it342_project.models.Post;
import com.example.it342_project.pdo.PostPDO;
import com.example.it342_project.pdo.UserPDO;
import com.example.it342_project.shared.Shared;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Post post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        TextView textPost = findViewById(R.id.text_post);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.blog);
        Button buttonP = findViewById(R.id.btnPost);
        Button btnDelete = findViewById(R.id.btnDelete);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null&&(!bundle.isEmpty())) {
            btnDelete.setVisibility(View.VISIBLE);
            buttonP.setText("Edit");
            post= PostPDO.projectList.get(bundle.getInt("position"));
            textPost.setText(post.text);
        }
        btnDelete.setOnClickListener(view -> {
            db.collection("posts")
                    .document(post.id)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Shared.showSnackBar(findViewById(android.R.id.content), "Delete Success");
                        finish();
                    })
                    .addOnFailureListener(e -> Shared.showSnackBar(findViewById(android.R.id.content), "Delete Error"));

        });
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
               /* case R.id.home:
                    startActivity(new Intent(getApplicationContext(), home.class));
                    overridePendingTransition(0, 0);
                    return true;*/
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), BottomNavigationView.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.blog:

                    return true;
            }
            return false;
        });

        buttonP.setOnClickListener(view -> {
            if (TextUtils.isEmpty(textPost.getText()))
                textPost.setError("Enter Value");
            else {
                Post postData = new Post("", textPost.getText().toString(),UserPDO.user.email);
                if(post==null)
                db.collection("posts")
                        .add(postData.toMap())
                        .addOnSuccessListener(aVoid -> {
                            Shared.showSnackBar(findViewById(android.R.id.content), "Inserted Success");
                            finish();
                        })
                        .addOnFailureListener(e -> Shared.showSnackBar(findViewById(android.R.id.content), "Inserted Error"));
                else
                    db.collection("posts")
                    .document(post.id)
                            .update(postData.toMap())
                            .addOnSuccessListener(aVoid -> {
                                Shared.showSnackBar(findViewById(android.R.id.content), "Inserted Success");
                                finish();
                            })
                            .addOnFailureListener(e -> Shared.showSnackBar(findViewById(android.R.id.content), "Inserted Error"));

            }
        });
    }

    public void openPost() {
        Intent intent = new Intent(this, Post2.class);
        startActivity(intent);
    }
}