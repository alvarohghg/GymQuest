package com.example.alejandro_luna;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText email,password;
    private Button signIn;
    private ImageView needHelp;
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*private SignInButton googleButton;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.registerButton);
        register.setOnClickListener(this);

        signIn=(Button) findViewById(R.id.loginButton);
        signIn.setOnClickListener(this);

        email=(EditText) findViewById(R.id.email);
        email.setOnClickListener(this);

        password=(EditText) findViewById(R.id.password);
        password.setOnClickListener(this);

        needHelp=(ImageView)findViewById(R.id.support);
        needHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","support@smarfit.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SmartFit Support");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "SmartFit Support");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });


        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        //Firebase Auth instance
        //mAuth=FirebaseAuth.getInstance();

        forgotPassword=(TextView) findViewById(R.id.forgot);
        forgotPassword.setOnClickListener(this);

        //googleButton=(SignInButton) findViewById(R.id.mainGoogle);



        //Configure google Sign-in
        /*gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this,gso);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });*/


    }

    /*private void SignIn() {
        Intent intent =gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }*/
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                GoogleSignActivity();

            } catch(ApiException e){
                Toast.makeText(this,"Error signing in with Google", Toast.LENGTH_LONG).show();
            }

        }
    }*/

    /*
    private void GoogleSignActivity() {
        finish();
        Intent intent=new Intent(getApplicationContext(),GoogleSignActivity.class);
        startActivity(intent);
    }*/



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.loginButton:
                userLogin();
                break;
            case R.id.forgot:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

        }
    }

    private void userLogin() {

        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
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
            password.setError("Failed to login! Please check your credentials");
            password.requestFocus();
            return;
        }

        db.collection("users")
                .whereEqualTo("email", Email)
                .whereEqualTo("password", Password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot.isEmpty()) {
                            Toast.makeText(Login.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                        } else {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> user = document.getData();
                                boolean emailVerified = (boolean) user.get("emailVerified");
                                if (emailVerified) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    //Toast.makeText(Login.this, "All good", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                } else {
                                    Toast.makeText(Login.this, "Check your email to verify your account \n " +
                                            "Make sure to check spam folder!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(Login.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });


    }

}
