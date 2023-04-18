package com.example.gymquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button logoutButton, planificationButton;
    private ImageView userProfile;

    // new comment
    // alex comment

    String currentUserEmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Create user-day collection and add document with user's email
        //only for the first time

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user-day");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean userExists = false;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> userData = (Map<String, Object>) userSnapshot.getValue();
                    if (userData != null && userData.get("email") != null && userData.get("email").equals(currentUserEmail)) {
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference userDayRef = rootRef.child("user-day").push();
                    userDayRef.child("email").setValue(currentUserEmail);
                    userDayRef.child("monday").setValue("");
                    userDayRef.child("tuesday").setValue("");
                    userDayRef.child("wednesday").setValue("");
                    userDayRef.child("thursday").setValue("");
                    userDayRef.child("friday").setValue("");
                    userDayRef.child("saturday").setValue("");
                    userDayRef.child("sunday").setValue("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer los datos", databaseError.toException());
            }
        });



        planificationButton=(Button)findViewById(R.id.mainPlanification);
        planificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, planification.class));
            }
        });

        logoutButton=(Button) findViewById(R.id.signOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        userProfile=(ImageView)findViewById(R.id.userProfile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
