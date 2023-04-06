package com.example.alejandro_luna;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Monday extends AppCompatActivity {

    private ListView mondayList,mondayAddRoutines;
    private static final String TAG = "Mondayyyy";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);



        mondayList = findViewById(R.id.mondayRoutines);
        mondayAddRoutines=findViewById(R.id.mondayAddRoutines);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        mondayList = findViewById(R.id.mondayRoutines);

        //only works with users
        CollectionReference routinesRef = db.collection("exercises");

        routinesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> routines = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String title = document.getId();
                    routines.add(title);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Monday.this, android.R.layout.simple_list_item_1, routines);
                mondayAddRoutines.setAdapter(adapter);
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