package com.example.alejandro_luna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button editButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        final TextView settingsName=(TextView) findViewById(R.id.settingsName);
        final TextView settingsSurname=(TextView) findViewById(R.id.settingsSurname);
        final TextView settingsEmail=(TextView) findViewById(R.id.settingsEmail);
        final TextView settingsBirth=(TextView) findViewById(R.id.settingsBirth);

        editButton=(Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, EditProfile.class));

            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile!=null){
                    String name = userProfile.name;
                    String surname = userProfile.surname;
                    String email = userProfile.email;
                    String birth = userProfile.birth;
                    settingsName.setText(name);
                    settingsSurname.setText(surname);
                    settingsEmail.setText(email);
                    settingsBirth.setText(birth);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this,"Something wrong happened!",Toast.LENGTH_LONG ).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editBackButton1:

            case R.id.homeButton:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.editButton:
                startActivity(new Intent(this, EditProfile.class));
                break;
        }
    }
}
