package com.example.alejandro_luna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 3000;

    ImageView appTitle;
    TextView developedByLine;
    TextView poweredByLine;
    ImageView androidLogo;
    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //backgroundImage = findViewById(R.id.backgroundImage);
        appTitle = findViewById(R.id.appTitle);
        developedByLine = findViewById(R.id.developedByLine);
        androidLogo = findViewById(R.id.androidLogo);
        poweredByLine = findViewById(R.id.poweredBy);

        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        appTitle.setAnimation(bottomAnim);
        developedByLine.setAnimation(sideAnim);
        poweredByLine.setAnimation(sideAnim);
        androidLogo.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run(){
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}