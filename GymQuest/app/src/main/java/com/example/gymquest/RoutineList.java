package com.example.gymquest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineList extends AppCompatActivity {
    private TextView routineName,routineDuration;
    private ListView routineExercises;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_list);
        //This string comes from a day activity
        String selectedRoutine = getIntent().getStringExtra("SELECTED_ROUTINE");
        routineName=findViewById(R.id.routineName);
        routineDuration=findViewById(R.id.routineDuration);
        routineName.setText(selectedRoutine);
        ArrayList<String> routineExercises = new ArrayList<>();



        DatabaseReference routinesRef = FirebaseDatabase.getInstance().getReference().child("routines");

        routinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if (childSnapshot.getKey().equals(selectedRoutine)) {
                        String duration = childSnapshot.child("duration").getValue(String.class);
                        routineDuration.setText(duration);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error retrieving routines: " + error.getMessage());
            }
        });
        //to print the exercises











    }
}