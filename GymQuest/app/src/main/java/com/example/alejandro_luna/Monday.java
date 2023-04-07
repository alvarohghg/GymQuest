package com.example.alejandro_luna;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Monday extends AppCompatActivity {

    private ListView mondayList,mondayAddRoutines;
    private ImageView addRoutineImage;
    private static final String TAG = "Mondayyyy";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);



        mondayList = findViewById(R.id.mondayRoutines);
        mondayAddRoutines=findViewById(R.id.mondayAddRoutines);
        addRoutineImage=findViewById(R.id.addImage);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();



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

        // Establece un Listener de elemento de lista en tu ListView
                mondayAddRoutines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Obtiene el elemento de la lista que ha sido seleccionado
                        String selectedRoutine = (String) parent.getItemAtPosition(position);

                        // Realiza la acción deseada con el elemento seleccionado
                        // Por ejemplo, podrías abrir una nueva actividad y pasar el elemento seleccionado como parámetro

                    }
                });



                /*db.collection("user-day")
                .document(currentUserEmail)
                .collection("monday")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Procesar los resultados de la consulta
                        List<String> routineNames = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Routine routine = document.toObject(Routine.class);
                            routineNames.add(routine.getTitle()); // Añadir el nombre de la rutina a la lista
                        }

                        // Configurar un adaptador para el ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Monday.this, android.R.layout.simple_list_item_1, routineNames);
                        mondayList.setAdapter(adapter);
                    }
                });
*/




    }
}