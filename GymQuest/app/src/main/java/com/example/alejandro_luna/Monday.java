package com.example.alejandro_luna;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Monday extends AppCompatActivity {

    private ListView mondayAddRoutines;
    private TextView mondayList;
    private ImageView mondayRemoveRoutine;
    private static final String TAG = "Mondayyyy";

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);



        mondayList = findViewById(R.id.mondayRoutines);
        mondayAddRoutines=findViewById(R.id.mondayAddRoutines);
        mondayRemoveRoutine=findViewById(R.id.mondayRemoveRoutine);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        
        mondayRemoveRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la referencia a la base de datos
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userRef = database.getReference("user-day");

                // Buscar el usuario actual en la base de datos
                Query query = userRef.orderByChild("email").equalTo(currentUserEmail);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Obtener la lista actual de rutinas en monday
                            String currentRoutines = (String) userSnapshot.child("monday").getValue();
                            if (currentRoutines == null) {
                                // Si la lista está vacía, mostrar un mensaje de error y salir
                                Toast.makeText(getApplicationContext(), "Monday routines list is empty", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Separar la lista por comas en un array de strings
                            String[] routinesArray = currentRoutines.split(",");

                            if (routinesArray.length == 0) {
                                // Si la lista está vacía, mostrar un mensaje de error y salir
                                Toast.makeText(getApplicationContext(), "Monday routines list is empty", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Eliminar el último elemento del array
                            String[] updatedRoutinesArray = Arrays.copyOf(routinesArray, routinesArray.length - 1);

                            // Concatenar el array actualizado en una lista separada por comas
                            String updatedRoutines = TextUtils.join(",", updatedRoutinesArray);

                            // Actualizar el valor en la base de datos
                            userSnapshot.getRef().child("monday").setValue(updatedRoutines);

                            // Mostrar mensaje de éxito
                            Toast.makeText(getApplicationContext(), "Last routine removed from Monday", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        });


        //show all the routines available
        DatabaseReference routinesRef = FirebaseDatabase.getInstance().getReference().child("routines");

        routinesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> routineTitles = new ArrayList<>(); // Create a list to store routine titles

                for (DataSnapshot routineSnapshot : snapshot.getChildren()) { // Iterate over each routine snapshot in the data snapshot
                    String title = routineSnapshot.getKey(); // Get the value of the "title" field
                    routineTitles.add(title); // Add the title to the list of routine titles
                }

                // Create a ListView adapter with the list of routine titles
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Monday.this, android.R.layout.simple_list_item_1, routineTitles);
                mondayAddRoutines.setAdapter(adapter); // Set the adapter to your ListView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error retrieving routines: " + error.getMessage());
            }
        });

        //here the user can add routines
        mondayAddRoutines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedRoutine = (String) parent.getItemAtPosition(position);

                // Obtener la referencia a la base de datos
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userRef = database.getReference("user-day");

                // Buscar el usuario actual en la base de datos
                Query query = userRef.orderByChild("email").equalTo(currentUserEmail);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Obtener el valor actual de la lista de rutinas en monday
                            String currentRoutines = (String) userSnapshot.child("monday").getValue();
                            if (currentRoutines == null) {
                                currentRoutines = "";
                            }

                            // Concatenar el valor seleccionado con la lista actual
                            String updatedRoutines = selectedRoutine + "," + currentRoutines;

                            // Actualizar el valor en la base de datos
                            userSnapshot.getRef().child("monday").setValue(updatedRoutines);

                            // Mostrar mensaje de éxito
                            Toast.makeText(getApplicationContext(), "Routine added to Monday", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        });




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user-day");


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> userData = (Map<String, Object>) userSnapshot.getValue();
                    if (userData != null && userData.get("email") != null && userData.get("email").equals(currentUserEmail)) {
                        String mondayValue = (String) userData.get("monday");
                        if (mondayValue != null) {
                            if (mondayValue != null) {
                                String[] values = mondayValue.split(",");
                                StringBuilder sb = new StringBuilder();
                                for (String value : values) {
                                    sb.append(value.trim()).append("\n");
                                }
                                mondayList.setText(sb.toString());
                            }

                            //mondayList.setText(mondayValue);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });






    }
}