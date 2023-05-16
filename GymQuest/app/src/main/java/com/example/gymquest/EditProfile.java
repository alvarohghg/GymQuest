package com.example.gymquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity implements View.OnClickListener{

    private Button submit;
    private EditText newName, newHeight, newWeight,newTarget;
    private static final String TAG = "MyActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        submit=(Button)findViewById(R.id.editSubmitChanges);
        submit.setOnClickListener(this);

        newName = (EditText) findViewById(R.id.editFirstName);
        newHeight = (EditText) findViewById(R.id.editHeight);
        newWeight = (EditText) findViewById(R.id.editWeight);
        newTarget= (EditText) findViewById(R.id.editTarget);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.editSubmitChanges:
                changeUser();
                break;
        }

    }

    private void changeUser() {
        String Name = newName.getText().toString().trim();
        String Height = newHeight.getText().toString().trim();
        String Weight = newWeight.getText().toString().trim();
        String Target = newTarget.getText().toString().trim();

        if( Name.isEmpty() || Name.matches("Name")){
            newName.setError("Name is required!");
            newName.requestFocus();
            return;
        }

        if( Height.isEmpty() || Height.matches("Height")) {
            newHeight.setError("Height is required!");
            newHeight.requestFocus();
            return;
        }
        if( Weight.isEmpty() || Weight.matches("Weight")) {
            newWeight.setError("Weight is required!");
            newWeight.requestFocus();
            return;
        }
        if( Target.isEmpty() || Target.matches("Target")) {
            newTarget.setError("Target is required!");
            newTarget.requestFocus();
            return;
        }


        //manage edit data
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference refName = FirebaseDatabase.getInstance().getReference("users").child(uid).child("name");
        DatabaseReference refHeight = FirebaseDatabase.getInstance().getReference("users").child(uid).child("height");
        DatabaseReference refTarget = FirebaseDatabase.getInstance().getReference("users").child(uid).child("kcal");
        DatabaseReference refWeight = FirebaseDatabase.getInstance().getReference("users").child(uid).child("kg");

        refName.setValue(Name);
        refHeight.setValue(Height);
        refTarget.setValue(Target);
        refWeight.setValue(Weight);


        Toast.makeText(EditProfile.this,"Profile updated!",Toast.LENGTH_LONG ).show();

    }
}
