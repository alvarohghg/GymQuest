package com.example.gymquest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;

public class exerciseDisplay extends AppCompatActivity {

    private TextView exerciseName,exerciseDescription,exerciseGroup,
    exerciseSets,exerciseReps;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_display);
        Intent intent = getIntent();
        String selectedExerciseName = intent.getStringExtra("selectedExerciseName");
        exerciseName=(TextView)findViewById(R.id.exerciseName);
        exerciseName.setText(selectedExerciseName);

        exerciseDescription=findViewById(R.id.exerciseDescription);
        exerciseGroup=findViewById(R.id.exerciseGroup);

        exerciseSets=findViewById(R.id.exerciseSets);
        exerciseReps=findViewById(R.id.exerciseReps);
        displayExerciseInfo(selectedExerciseName);

    }
    private void displayExerciseInfo(String exerciseKey) {
        DatabaseReference exercisesRef = FirebaseDatabase.getInstance().getReference().child("exercises");
        Query exercisesQuery = exercisesRef.orderByChild("name").equalTo(exerciseKey);

        exercisesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot routineSnapshot : dataSnapshot.getChildren()) {
                    String description = routineSnapshot.child("description").getValue(String.class);
                    String group = routineSnapshot.child("group").getValue(String.class);
                    String reps = routineSnapshot.child("reps").getValue(String.class);
                    String sets = routineSnapshot.child("sets").getValue(String.class);

                    exerciseDescription.setText(description);
                    exerciseGroup.setText(group);
                    exerciseSets.setText(sets);
                    exerciseReps.setText(reps);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving routines: " + databaseError.getMessage());
            }
        });

    }



}