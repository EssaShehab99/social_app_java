package com.example.it342_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.it342_project.adapter.CustomAdapter;
import com.example.it342_project.models.Post;
import com.example.it342_project.pdo.PostPDO;
import com.example.it342_project.pdo.UserPDO;
import com.example.it342_project.shared.Shared;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;

public class ProfileActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    TextView emailTV,profileTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.recycler_view);
        profileTV = findViewById(R.id.profile_tv);
        emailTV = findViewById(R.id.email_tv);
        profileTV.setText(UserPDO.user.email);
        emailTV.setText(UserPDO.user.email);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        fetchData();
        customAdapter = new CustomAdapter(this, PostPDO.projectList);
        customAdapter.setOnItemClickListener((position, v) -> {
            if(PostPDO.projectList.get(position).email.equals(UserPDO.user.email)){
                Intent intent=new Intent(this, PostActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                /* case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home.class));
                        overridePendingTransition(0, 0);
                        return true;*/
                //                    case R.id.profile:
                //                        return true;
                if (menuItem.getItemId() == R.id.blog) {
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
//                        overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

    }
    void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Shared.showSnackBar(findViewById(android.R.id.content),"Error Occur");
                        return;
                    }

                    assert value != null;
                    PostPDO.projectList.clear();
                    for (QueryDocumentSnapshot doc : value) {
                            try {
                                PostPDO.projectList.add(Post.fromMap(doc.getData(),doc.getId()));
                            } catch (Exception err) {
                                Shared.showSnackBar(findViewById(android.R.id.content),"Error Occur");
                            }

                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        customAdapter = new CustomAdapter(ProfileActivity.this, PostPDO.projectList);
                        recyclerView.setAdapter(customAdapter);



                });
    }
}