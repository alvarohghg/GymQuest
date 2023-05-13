package com.example.gymquest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;

import android.widget.AdapterView;

public class Planification extends AppCompatActivity {

    private Button Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday;
    private TextView planificationRoutine,planificationDuration;
    private ListView planificationExercises;
    private ImageView planificationEdit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planification);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationListener navigationListener = new BottomNavigationListener(this, bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navigationListener);

        // Call customizeMenuColors initially to set the default colors
        navigationListener.customizeMenuColors(R.id.menu_schedule);


        Monday=(Button) findViewById(R.id.planificationMonday);
        Tuesday=(Button)findViewById(R.id.planificationTuesday);
        Wednesday=(Button)findViewById(R.id.planificationWednesday);
        Thursday=(Button)findViewById(R.id.planificationThursday);
        Friday=(Button)findViewById(R.id.planificationFriday);
        Saturday=(Button)findViewById(R.id.planificationSaturday);
        Sunday=(Button)findViewById(R.id.planificationSunday);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        planificationRoutine=findViewById(R.id.planificationRoutine);
        planificationDuration=findViewById(R.id.planificationDuration);
        planificationExercises=findViewById(R.id.editRoutineList);

        planificationEdit=(ImageView)findViewById(R.id.planificationEdit);
        planificationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Planification.this, editRoutine.class));
            }
        });

        Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("monday");
            }
        });
        Tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("tuesday");
            }
        });
        Wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("wednesday");
            }
        });
        Thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("thursday");
            }
        });
        Friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("friday");
            }
        });
        Saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("saturday");
            }
        });
        Sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRoutineForDay("sunday");
            }
        });

        // Add listener to ListView "planificationExercises"
        planificationExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedExerciseName = (String) adapterView.getItemAtPosition(i);

                // Intent to start activity "exerciseDisplay" and send the name of the selected exercise
                Intent intent = new Intent(Planification.this, ExerciseDisplay.class);
                Bundle bundle = new Bundle();
                bundle.putString("selectedExerciseName", selectedExerciseName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void displayRoutineForDay(String day) {
        // Show the user's routines
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user-day");
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Query query = userRef.orderByChild("email").equalTo(currentUserEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String currentRoutine = (String) userSnapshot.child(day.toLowerCase()).getValue();
                    planificationRoutine.setText(currentRoutine);
                    if (currentRoutine != null) {
                        // Find the routine in the "routines" collection
                        DatabaseReference routinesRef = FirebaseDatabase.getInstance().getReference().child("routines");
                        Query routineQuery = routinesRef.orderByChild("title").equalTo(currentRoutine);

                        routineQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean found = false;
                                for (DataSnapshot routineSnapshot : dataSnapshot.getChildren()) {
                                    found = true;
                                    String duration = routineSnapshot.child("duration").getValue(String.class);
                                    String exercisesString = routineSnapshot.child("exercises").getValue(String.class);
                                    List<String> exercises = Arrays.asList(exercisesString.split(","));
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Planification.this, R.layout.list_item_exercise, exercises);
                                    planificationExercises.setAdapter(adapter);
                                    planificationDuration.setText(duration);
                                }

                                if (!found) {
                                    // The routine was not found in the "routines" collection, try to find it in "user-routine"
                                    DatabaseReference userRoutinesRef = FirebaseDatabase.getInstance().getReference().child("user-routine");
                                    Query userRoutineQuery = userRoutinesRef.orderByChild("email").equalTo(currentUserEmail);
                                    userRoutineQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot userRoutineSnapshot : dataSnapshot.getChildren()) {
                                                String routineName = userRoutineSnapshot.child("title").getValue(String.class);
                                                if (routineName.equals(currentRoutine)) {
                                                    String duration = userRoutineSnapshot.child("duration").getValue(String.class);
                                                    String exercisesString = userRoutineSnapshot.child("exercises").getValue(String.class);
                                                    List<String> exercises = Arrays.asList(exercisesString.split(","));
                                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Planification.this, R.layout.list_item_exercise, exercises);
                                                    planificationExercises.setAdapter(adapter);
                                                    planificationDuration.setText(duration);
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.e(TAG, "Error retrieving user-routines: " + databaseError.getMessage());
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e(TAG, "Error retrieving routines: " + databaseError.getMessage());
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving user-day: " + databaseError.getMessage());
            }
        });
    }


}
