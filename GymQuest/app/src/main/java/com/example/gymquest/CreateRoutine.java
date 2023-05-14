package com.example.gymquest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateRoutine extends AppCompatActivity {
    private EditText title, duration;
    private ImageView save;
    private ListView allExercises,userRoutineListView;
    private DatabaseReference mDatabase,exDatabase;
    private ArrayAdapter<Exercise> userRoutineAdapter;
    private ArrayList<Exercise> userRoutineList;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        title=(EditText) findViewById(R.id.createRoutineTitle);
        duration=(EditText)findViewById(R.id.createRoutineDuration);
        save = (ImageView)findViewById(R.id.createRoutineSave);
        allExercises=(ListView)findViewById(R.id.createRoutineExercisesList);
        userRoutineListView=(ListView)findViewById(R.id.createRoutineMyList);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        exDatabase= FirebaseDatabase.getInstance().getReference();
        exDatabase.child("exercises");
        exDatabase.child("exercises").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> exerciseNames = new ArrayList<>();
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {
                    String exerciseName = exerciseSnapshot.child("name").getValue(String.class);
                    exerciseNames.add(exerciseName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateRoutine.this, R.layout.list_item_exercise, exerciseNames);
                allExercises.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Error obtaining exercises list", databaseError.toException());
            }
        });


        // Adapter for the ListView of the routine's user
        userRoutineList = new ArrayList<>();
        userRoutineAdapter = new ArrayAdapter<>(this, R.layout.list_item_exercise, userRoutineList);
        userRoutineListView.setAdapter(userRoutineAdapter);
        //Set up the OnItemClickListener for the ListView of all exercises
        allExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view).getText().toString();
                Exercise exercise = new Exercise(name);
                userRoutineList.add(exercise);
                userRoutineAdapter.notifyDataSetChanged();
            }
        });
        //save all changes
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routineTitle = title.getText().toString().trim();
                String routineDuration = duration.getText().toString().trim();
                String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                String userRoutineString = "";
                for (Exercise exercise : userRoutineList) {
                    userRoutineString += exercise.getName() + ",";
                }
                // erase last coma and any space
                if (!userRoutineList.isEmpty()) {
                    userRoutineString = userRoutineString.substring(0, userRoutineString.length() - 2);
                }


                if (routineTitle.isEmpty() || routineDuration.isEmpty()) {
                    Toast.makeText(CreateRoutine.this, "Please insert a title and duration", Toast.LENGTH_SHORT).show();
                } else {
                    UserRoutine routine = new UserRoutine( routineTitle, routineDuration,userRoutineString,userEmail);
                    mDatabase.child("user-routine").push().setValue(routine)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CreateRoutine.this, "Routine successfully created", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CreateRoutine.this, "Error creating the routine", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
    public class Exercise {
        private String name;

        public Exercise(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public String toString() {
            return name;
        }
    }
}
