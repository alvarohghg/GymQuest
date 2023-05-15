package com.example.gymquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView registerUser;
    private EditText name, height,kg,kcal, email, password, confirmPassword;
    private ProgressBar progressBar;
    private ImageView backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(this);

        name = (EditText) findViewById(R.id.registerFirstName);
        email = (EditText) findViewById(R.id.registerEmail);
        password = (EditText) findViewById(R.id.registerPassword);
        confirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);

        height = (EditText) findViewById(R.id.registerHeight);
        kg = (EditText) findViewById(R.id.registerKg);
        kcal = (EditText) findViewById(R.id.registerKcal);

        progressBar = (ProgressBar) findViewById(R.id.registerProgressBar);

        backButton=(ImageView)findViewById(R.id.btnBackRegisterUser);
        backButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                registerUser();
                break;

            case R.id.btnBackRegisterUser:
                startActivity(new Intent(RegisterUser.this, Login.class));
                break;
        }

    }

    private void registerUser() {

        String Name = name.getText().toString().trim();
        String Height = height.getText().toString().trim();
        String Kg = kg.getText().toString().trim();
        String Kcal = kcal.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String ConfirmPassword = confirmPassword.getText().toString().trim();

        Map<String, Object> user = new HashMap<>();
        user.put("name", Name);
        user.put("email", Email);
        user.put("password", Password);
        user.put("height", Height);
        user.put("kcal", Kcal);
        user.put("kg", Kg);

        if (Name.isEmpty() || Name.matches("Name")) {
            name.setError("Name is required!");
            name.requestFocus();
            return;
        }

        if (Height.isEmpty()) {
            height.setError("Height is required!");
            height.requestFocus();
            return;
        }
        if (Kcal.isEmpty()) {
            kcal.setError("Kcal is required!");
            kcal.requestFocus();
            return;
        }
        if (Kg.isEmpty()) {
            kg.setError("Kg is required!");
            kg.requestFocus();
            return;
        }
        if (Email.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if (Password.length() < 6) {
            password.setError("Password must have at least 6 characters!");
            password.requestFocus();
            return;
        }
        if (ConfirmPassword.isEmpty()) {
            confirmPassword.setError("You need to confirm the password!");
            confirmPassword.requestFocus();
            return;
        }
        if (!ConfirmPassword.matches(Password)) {
            confirmPassword.setError("Passwords must match!");
            confirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // public User(String name, String height, String kg, String kcal, String email, String password)
                            User user = new User(Name, Height, Kg, Kcal,Email, Password);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterUser.this, "User Registered", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(RegisterUser.this, Login.class));
                                            } else {
                                                Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
