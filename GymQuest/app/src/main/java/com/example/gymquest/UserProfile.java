package com.example.gymquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ImageView editButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Bottom main menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationListener navigationListener = new BottomNavigationListener(this, bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navigationListener);

        // Call customizeMenuColors initially to set the default colors
        navigationListener.customizeMenuColors(R.id.menu4_profile);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userID=user.getUid();
        final TextView settingsName=(TextView) findViewById(R.id.settingsName);
        final TextView settingsHeight=(TextView) findViewById(R.id.settingsHeight);
        final TextView settingsEmail=(TextView) findViewById(R.id.settingsEmail);
        final TextView settingsKg=(TextView) findViewById(R.id.settingsWeight);
        final TextView settingsKcal=(TextView) findViewById(R.id.settingsKcal);

        editButton=(ImageView) findViewById(R.id.editButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, EditProfile.class));

            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String name = userProfile.name;
                    String height = userProfile.height;
                    String email = userProfile.email;
                    String kg = userProfile.kg;
                    String kcal = userProfile.kcal;
                    settingsName.setText(name);
                    settingsHeight.setText(height + " cm");
                    settingsEmail.setText(email);
                    settingsKg.setText(kg + " kg");
                    settingsKcal.setText(kcal + " kcal");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this,"Something wrong happened!",Toast.LENGTH_LONG ).show();
            }
        });
        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User userProfile = documentSnapshot.toObject(User.class);
                    if (userProfile != null) {
                        String name = userProfile.name;
                        String height = userProfile.height;
                        String email = userProfile.email;
                        String kg = userProfile.kg;
                        String kcal = userProfile.kcal;
                        settingsName.setText(name);
                        settingsHeight.setText(height);
                        settingsEmail.setText(email);
                        settingsKg.setText(kg);
                        settingsKcal.setText(kcal);
                    }
                } else {
                    Toast.makeText(UserProfile.this, "User not found", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });*/


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editBackButton1:

            case R.id.homeButton:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.editButton:
                startActivity(new Intent(this, EditProfile.class));
                break;
        }
    }
}
