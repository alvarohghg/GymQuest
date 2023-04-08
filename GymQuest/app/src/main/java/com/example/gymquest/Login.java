package com.example.gymquest;

import android.content.Intent;
import android.net.Uri;
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

import com.example.gymquest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
                        "mailto","support@gymquest.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "GymQuest Support");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "GymQuest Support");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });


        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        //Firebase Auth instance
        mAuth=FirebaseAuth.getInstance();

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
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                progressBar.setVisibility(View.VISIBLE);
                                startActivity(new Intent(Login.this, MainActivity.class));
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(Login.this, "Check your email to verify your account \n " +
                                        "Make sure to check spam folder!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
        /*
        db.collection("users")
                   .get()
                   .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           if(task.isSuccessful()){
                               for(QueryDocumentSnapshot doc : task.getResult()){
                                   String a=doc.getString("email");
                                   String b=doc.getString("password");
                                   String a1=Email;
                                   String b1=Password;
                                   if(a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                       Intent home = new Intent(Login.this, MainActivity.class);
                                       startActivity(home);
                                       Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                       break;
                                   }
                               }

                           }
                       }
                   });
            */

    }

}
