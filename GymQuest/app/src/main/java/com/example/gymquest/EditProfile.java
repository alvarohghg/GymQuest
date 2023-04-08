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
    private ImageView backButton;
    private Button submit;
    private EditText newName, newSurname, newBirth;
    private static final String TAG = "MyActivity";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backButton=(ImageView)findViewById(R.id.editBackButton);
        backButton.setOnClickListener(this);
        submit=(Button)findViewById(R.id.editSubmitChanges);
        submit.setOnClickListener(this);

        newName = (EditText) findViewById(R.id.editFirstName);
        newSurname = (EditText) findViewById(R.id.editSurname);
        newBirth = (EditText) findViewById(R.id.editBirth);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editBackButton:
                startActivity(new Intent(EditProfile.this, UserProfile.class));
                break;

            case R.id.editSubmitChanges:
                changeUser();
                break;
        }

    }

    private void changeUser() {
        String Name = newName.getText().toString().trim();
        String Surname = newSurname.getText().toString().trim();
        String Birth = newBirth.getText().toString().trim();

        if( Name.isEmpty() || Name.matches("First Name")){
            newName.setError("Name is required!");
            newName.requestFocus();
            return;
        }

        if( Surname.isEmpty() || Surname.matches("Surname")) {
            newSurname.setError("Surname is required!");
            newSurname.requestFocus();
            return;
        }

        if( Birth.isEmpty()) {
            newBirth.setError("Birth is required!");
            newBirth.requestFocus();
            return;
        }

        //manage edit data
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference refName = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("name");
        DatabaseReference refSurname = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("surname");
        DatabaseReference refBirth = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("birth");

        refName.setValue(Name);
        refSurname.setValue(Surname);
        refBirth.setValue(Birth);

        Toast.makeText(EditProfile.this,"Profile updated!",Toast.LENGTH_LONG ).show();

    }
}
