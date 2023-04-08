package com.example.gymquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class planification extends AppCompatActivity {
    private Button Monday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planification);
        Monday=(Button) findViewById(R.id.planificationMonday);
        Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(planification.this, Monday.class));
            }
        });
    }
}