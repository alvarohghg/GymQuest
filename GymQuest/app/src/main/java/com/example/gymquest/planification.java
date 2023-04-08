package com.example.alejandro_luna;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class planification extends AppCompatActivity {
    private Button Monday;
    private TextView planificationRoutine,planificationDuration;
    private ListView planificationExercises;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planification);
        Monday=(Button) findViewById(R.id.planificationMonday);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        planificationRoutine=findViewById(R.id.planificationRoutine);
        planificationDuration=findViewById(R.id.planificationDuration);
        planificationExercises=findViewById(R.id.planificationExercises);

        Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userRef = database.getReference("user-day");
                Query query = userRef.orderByChild("email").equalTo(currentUserEmail);


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            //change the routine text
                            String currentRoutine = (String) userSnapshot.child("monday").getValue();
                            planificationRoutine.setText(currentRoutine);
                            DatabaseReference routinesRef = FirebaseDatabase.getInstance().getReference().child("routines");

                            routinesRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                        if (childSnapshot.getKey().equals(currentRoutine)) {
                                            //change the duration text
                                            String duration = childSnapshot.child("duration").getValue(String.class);
                                            planificationDuration.setText(duration);
                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e(TAG, "Error retrieving routines: " + error.getMessage());
                                }
                            });

                            routinesRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Obtener la rutina correspondiente a la currentRoutine
                                    for (DataSnapshot routineSnapshot : dataSnapshot.getChildren()) {
                                        String routineName = routineSnapshot.child("title").getValue(String.class);
                                        if (routineName.equals(currentRoutine)) {
                                            // Obtener la lista de ejercicios
                                            String exercisesString = routineSnapshot.child("exercises").getValue(String.class);
                                            List<String> exercisesList = Arrays.asList(exercisesString.split(","));
                                            // Mostrar la lista de ejercicios en la ListView
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(planification.this, android.R.layout.simple_list_item_1, exercisesList);
                                            planificationExercises.setAdapter(adapter);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                });



            }
        });

    }
}