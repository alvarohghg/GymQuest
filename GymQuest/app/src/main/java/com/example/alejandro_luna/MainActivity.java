package com.example.alejandro_luna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button logoutButton;

    private ImageView userProfile;
    private TextView exerciseTV, weightTV, repsTV;
    ArrayList<String> exercises = new ArrayList<>();

    RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseTV = findViewById(R.id.exerciseET);
        weightTV = findViewById(R.id.weightET);
        repsTV = findViewById(R.id.repsET);



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
        switch (view.getId()){
            case R.id.bt_save:
                exercises.add(exerciseTV.getText().toString() + " with " + weightTV.getText().toString() + " kg for " + repsTV.getText().toString() + " reps");

                RecyclerView recyclerView = findViewById(R.id.rvExercises);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewAdapter = new RecyclerViewAdapter(this, exercises);
                recyclerView.setAdapter(recyclerViewAdapter);
                break;

            case R.id.bt_clear:
                exerciseTV.setText("");
                weightTV.setText("");
                repsTV.setText("");

                break;
        }
    }
}
