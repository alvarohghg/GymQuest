package com.example.gymquest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllExercises extends AppCompatActivity {
    private GridView gridView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exercises);

        gridView=findViewById(R.id.exercisesGridView);

        DatabaseReference exercisesRef = FirebaseDatabase.getInstance().getReference().child("exercises");
        Query exQuery = exercisesRef.orderByChild("name");

        exQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> exerciseNames = new ArrayList<>(); // Create a list outside of the loop
                for (DataSnapshot routineSnapshot : dataSnapshot.getChildren()) {
                    String exercise = routineSnapshot.child("name").getValue(String.class);
                    exerciseNames.add(exercise); // Add every name to the list
                }
                ExerciseAdapter adapter = new ExerciseAdapter(AllExercises.this, exerciseNames);
                gridView.setAdapter(adapter); // assign the grid to the adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving routines: " + databaseError.getMessage());
            }
        });




    }
    class ExerciseAdapter extends BaseAdapter {
        private Context context;
        private List<String> exercises;

        public ExerciseAdapter(Context context, List<String> exercises) {
            this.context = context;
            this.exercises = exercises;
        }

        @Override
        public int getCount() {
            return exercises.size();
        }

        @Override
        public String getItem(int position) {
            return exercises.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            }
            TextView exerciseNameView = convertView.findViewById(R.id.exercise_name);
            exerciseNameView.setText(getItem(position));
            return convertView;
        }
    }
}
